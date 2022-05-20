package com.hgsoft.modules.bankbillcheck.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.util.ExcelUtil;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.bankbillcheck.entity.*;
import com.hgsoft.modules.bankbillcheck.enums.BankCheckEnums;
import com.hgsoft.modules.bankbillcheck.mapper.BankCheckMapper;
import com.hgsoft.modules.bankbillcheck.service.BankCheckService;
import com.hgsoft.modules.merchant.enums.ServiceTypeEnum;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.querymanage.enums.VehicleColor;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.systemmanage.enums.PaySignManageEnum;
import com.hgsoft.modules.utils.CallBackEcipEntityExcelView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 新银行对账服务实现类
 * Created by 吴鉴武 on 2021/7/16 16:51
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BankCheckServiceImpl implements BankCheckService {

    private final BankCheckMapper mapper;
    private final MerchantManageService merchantManageService;

    @Override
    public PageVo<BankCheck> findBankCheckPage(PageVo<BankCheck> pageVo,BankCheck bankCheck) {
        pageVo.initOrder();
        PageVo<BankCheck> page = mapper.findBankCheckPage(pageVo, bankCheck);
        if(CollUtil.isEmpty(page.getRecords())) return page;
        Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(bankCheck.getUserMerchantParam() == null ? null : bankCheck.getUserMerchantParam().getSearchIds(), ShiroSecurityUtil.isSuperUser());
        page.getRecords().replaceAll(vo->{
            vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
            vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
            vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
            vo.setCheckModeName(BankCheckEnums.getDescByCode(BankCheckEnums.CheckMode.values(),vo.getCheckMode()));
            vo.setCheckStatusName(BankCheckEnums.getDescByCode(BankCheckEnums.CheckStatus.values(),vo.getCheckStatus()));
            return vo;
        });
        BankCheck sum = mapper.getSum(bankCheck);
        page.getDataMap().put("sum",sum);
        return page;
    }

    @Override
    public PageVo<BankCheckException> findBankCheckExceptionPage(PageVo<BankCheckException> pageVo, BankCheckException bankCheckException) {
        pageVo.initOrder();
        PageVo<BankCheckException> page = mapper.findBankCheckExceptionPage(pageVo, bankCheckException);
        if(CollUtil.isEmpty(page.getRecords())) return page;
        Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(Arrays.asList(bankCheckException.getSiteId()),ShiroSecurityUtil.isSuperUser());
        page.getRecords().replaceAll(vo->{
            vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
            vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
            vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
            vo.setVehicleColorName(VehicleColor.getColorDescByColorNumber(vo.getVehicleColor()));
            long diffMinutes = DateUtil.between(DateUtil.parse(vo.getExTime(), DatePattern.NORM_DATETIME_PATTERN), DateUtil.parse(vo.getEnTime(), DatePattern.NORM_DATETIME_PATTERN), DateUnit.MINUTE);
            vo.setParkTime(StrUtil.format("{}小时{}分钟",diffMinutes / 60,diffMinutes % 60));
            return vo;
        });
        return page;
    }

    @Override
    public List<BankCheckDetail> findDetailList(BankCheckDetail bankCheckDetail) {
        List<BankCheckDetail> detailList = mapper.findDetailList(bankCheckDetail);
        if(CollUtil.isEmpty(detailList)) return detailList;
        Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(Arrays.asList(bankCheckDetail.getSiteId()),ShiroSecurityUtil.isSuperUser());
        detailList.replaceAll(vo->{
            vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
            vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
            vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
            return vo;
        });
        return detailList;
    }

    @Override
    @Transactional
    public ResponseEntity saveConfirmData(List<BankCheckConfirm> list) {
        Date now = new Date();
        String userName = ShiroSecurityUtil.realName();
        list.replaceAll(vo->{
            vo.setConfirmTime(now);
            vo.setConfirmMan(userName);
            vo.setTotalConfirmAmount(vo.getTotalConfirmAmount() == null ? null : vo.getTotalConfirmAmount().multiply(BigDecimal.valueOf(100)).setScale(0));
            return vo;
        });
        List<BankCheckConfirm> existsList = mapper.findConfirmList(list);
        try {
            if (CollUtil.isNotEmpty(existsList)) {
                Map<String, BankCheckConfirm> existsMap = existsList.stream().collect(Collectors.toMap(k -> k.getSiteId() + "|" + k.getTradeDay(),v->v));
                List<BankCheckConfirm> updateList = list.stream().filter(vo -> existsMap.containsKey(vo.getSiteId() + "|" + vo.getTradeDay())).collect(Collectors.toList());
                Iterator<BankCheckConfirm> iterator = updateList.iterator();
                while (iterator.hasNext()){
                    mapper.updateConfirmData(iterator.next());
                }
                list.removeAll(updateList);
            }
            if(CollUtil.isNotEmpty(list)) {
                mapper.insertConfirmData(list);
            }
            return ResponseEntity.builder().code(200).msg("确认成功").build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.builder().code(500).msg(ExceptionUtil.getMessage(e)).build();
        }
    }

    @Override
    public ModelAndView exportExcel(BankCheck bankCheck) {
        List<BankCheck> list = this.mapper.findList(bankCheck);
        ModelAndView mv;
        if(CollUtil.isNotEmpty(list)){
            Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(bankCheck.getUserMerchantParam() == null ? null : bankCheck.getUserMerchantParam().getSearchIds(), ShiroSecurityUtil.isSuperUser());
            list.replaceAll(vo->{
                vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
                vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
                vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
                vo.setCheckModeName(BankCheckEnums.getDescByCode(BankCheckEnums.CheckMode.values(),vo.getCheckMode()));
                vo.setCheckStatusName(BankCheckEnums.getDescByCode(BankCheckEnums.CheckStatus.values(),vo.getCheckStatus()));
                return vo;
            });
            BankCheck sum = mapper.getSum(bankCheck);
            list.add(sum);
            BigDecimal hundred = BigDecimal.valueOf(100);
            list.replaceAll(vo->{
                if(vo.getDifferAmount() != null) vo.setDifferAmount(vo.getDifferAmount().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                if(vo.getTotalAmount() != null) vo.setTotalAmount(vo.getTotalAmount().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                if(vo.getTotalCheckAmount() != null) vo.setTotalCheckAmount(vo.getTotalCheckAmount().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                if(vo.getTotalConfirmAmount() != null) vo.setTotalConfirmAmount(vo.getTotalConfirmAmount().divide(hundred).setScale(2, BigDecimal.ROUND_HALF_UP));
                return vo;
            });
            int row = list.size()+1;
            mv = new ModelAndView(new CallBackEcipEntityExcelView(book->{
                Sheet sheet = book.getSheetAt(0);
                ExcelUtil.mergeRegion(sheet,row, row, 0, 3);
                sheet.getRow(row).getCell(0).setCellValue("合计");
            }));
        }else{
            mv = new ModelAndView(new EcipEntityExcelView());
        }
        mv.addObject(NormalExcelConstants.FILE_NAME, "银行对账管理-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        mv.addObject(NormalExcelConstants.CLASS, BankCheck.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("银行对账管理", "银行对账管理"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    @Override
    public ModelAndView exportExceptionExcel(BankCheckException bankCheckException) {
        List<BankCheckException> list = mapper.findExceptionList(bankCheckException);
        if(CollUtil.isNotEmpty(list)){
            Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(Arrays.asList(bankCheckException.getSiteId()),ShiroSecurityUtil.isSuperUser());
            list.replaceAll(vo->{
                vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
                vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
                vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
                vo.setVehicleColorName(VehicleColor.getColorDescByColorNumber(vo.getVehicleColor()));
                long diffMinutes = DateUtil.between(DateUtil.parse(vo.getExTime(), DatePattern.NORM_DATETIME_PATTERN), DateUtil.parse(vo.getEnTime(), DatePattern.NORM_DATETIME_PATTERN), DateUnit.MINUTE);
                vo.setParkTime(StrUtil.format("{}小时{}分钟",diffMinutes / 60,diffMinutes % 60));
                vo.setFee(vo.getFee().divide(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
                return vo;
            });
        }
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "异常明细-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        mv.addObject(NormalExcelConstants.CLASS, BankCheckException.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("异常明细", "异常明细"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    @Override
    public ModelAndView exportDetailExcel(BankCheck bankCheck) {
        List<BankCheckDetailExport> detailList = mapper.exportDetailList(bankCheck);
        if(CollUtil.isNotEmpty(detailList)){
            Map<String, String> allLevelMerchantInfo = merchantManageService.getAllMerchant(bankCheck.getUserMerchantParam() == null ? null : bankCheck.getUserMerchantParam().getSearchIds(), ShiroSecurityUtil.isSuperUser());
            detailList.replaceAll(vo->{
                vo.setMerchantGroupName(allLevelMerchantInfo.getOrDefault(vo.getMerchantGroupId(),"未知拓展方"));
                vo.setMerchantName(allLevelMerchantInfo.getOrDefault(vo.getMerchantId(),"未知运营方"));
                vo.setSiteName(allLevelMerchantInfo.getOrDefault(vo.getSiteId(),"未知场景"));
                vo.setVehicleColorName(VehicleColor.getColorDescByColorNumber(vo.getVehicleColor()));
                vo.setServiceTypeName(ServiceTypeEnum.getTitleByValue(vo.getServiceType()));
                vo.setVehicleTypeName(PaySignManageEnum.getDescByValue(PaySignManageEnum.VehicleType.values(),vo.getServiceType()));
                return vo;
            });
        }
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "流水明细-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        mv.addObject(NormalExcelConstants.CLASS, BankCheckDetailExport.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("流水明细", "流水明细"));
        mv.addObject(NormalExcelConstants.DATA_LIST, detailList);
        return mv;
    }
}
