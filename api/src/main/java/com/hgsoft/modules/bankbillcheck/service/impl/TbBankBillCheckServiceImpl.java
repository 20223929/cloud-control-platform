package com.hgsoft.modules.bankbillcheck.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.ecip.web.util.SystemUtils;
import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheck;
import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheckSearchVo;
import com.hgsoft.modules.bankbillcheck.entity.TbOnlineEexit;
import com.hgsoft.modules.bankbillcheck.entity.TbOnlineEexitExportExeclVo;
import com.hgsoft.modules.bankbillcheck.mapper.TbBankBillCheckMapper;
import com.hgsoft.modules.bankbillcheck.service.TbBankBillCheckService;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.utils.ServiceTypeMapToNameUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 银行对账核对表ServiceImpl
 *
 * @author 何志豪
 * @version 2021-04-18 23:35:35
 */

@Service("com.hgsoft.modules.bankbillcheck.service.TbBankBillCheckService")
public class TbBankBillCheckServiceImpl extends CrudServiceImpl<TbBankBillCheckMapper, TbBankBillCheck>
        implements TbBankBillCheckService {

    @Autowired
    private MerchantManageService merchantManageService;

    private final String[] tipString = {"transDate(【主键transDate】): 可以不填写主键transDate；",
            "merchantGroupId(【主键merchantGroupId】): 可以不填写主键merchantGroupId；",
            "merchantId(【主键merchantId】): 可以不填写主键merchantId；", "siteId(【主键siteId】): 可以不填写主键siteId；",
            "transDate(【对账交易日期】): 必填", "merchantGroupId(【一级运营商】): 必填", "merchantId(【二级运营商】): 必填", "siteId(【三级运营商】): 必填",
            "serviceType(【服务类型】): 填写数据字典对应key，必填", "bankTotalCount(【银行流水总数】): 必填", "bankTotalAmount(【银行流水总金额(分)】): 必填",
            "platformTotalCount(【本系统流水总数】): 必填", "platformTotalAmount(【本系统流水总金额(分）】): 必填",
            "diffTotalCount(【流水总数差额】): 必填", "diffTotalAmount(【流水总金额差额(分)】): 必填",
            "confirmState(【确认状态:0-未确认 1-已确认】): 填写数据字典对应key，必填", "confirmMan(【确认人】): 必填", "confirmTime(【确认时间】): 必填",
            "remark(【备注】): 必填", "remarks(【备注信息】): 非必填"};


    @Override
    public TbBankBillCheck getByPrimaryKey(String transDate, String merchantGroupId, String merchantId, String siteId) {
        TbBankBillCheck one = this.getOne(new LambdaQueryWrapper<TbBankBillCheck>().eq(TbBankBillCheck::getTransDate, transDate)
                .eq(TbBankBillCheck::getMerchantGroupId, merchantGroupId).eq(TbBankBillCheck::getMerchantId, merchantId)
                .eq(TbBankBillCheck::getSiteId, siteId));
        if (one != null) {
            Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(), ShiroSecurityUtil.getPrincipal().getIsSuperUser());
            decorate(one, merchantInfo);
        }
        return one;
    }


    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, TbBankBillCheckSearchVo tbBankBillCheck) {
        List<TbBankBillCheck> list = this.mapper.findAllList(tbBankBillCheck);
        list.sort((c1,c2)->{
            return c2.getTransDate().compareTo(c1.getTransDate());
        });
        decorate(list.iterator());
        addSumTotal(list, tbBankBillCheck);
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        Date now = new Date();
        String yyyyMMddHHmmss = DateUtil.format(now, "yyyyMMddHHmmss");
        mv.addObject(NormalExcelConstants.FILE_NAME, "银行对账核对表-" + yyyyMMddHHmmss);
        mv.addObject(NormalExcelConstants.CLASS, TbBankBillCheck.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("银行对账核对表", "银行对账核对表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    /**
     * 导出模板
     */
    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "银行对账核对表模板");
        mv.addObject(NormalExcelConstants.CLASS, TbBankBillCheck.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("银行对账核对表", "银行对账核对表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    @Override
    public Long countEexit(TbBankBillCheckSearchVo checks) {
        return this.mapper.findAllEexitCount(checks);
    }

    @Override
    public ModelAndView exportEexitDetailData(HttpServletRequest request, TbBankBillCheckSearchVo checks) {
        List<TbOnlineEexit> list = this.mapper.findAllEexit(checks);

        List<TbOnlineEexitExportExeclVo> exports = new ArrayList<>(list.size());
        Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(), ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        list.forEach(c->{
            TbOnlineEexitExportExeclVo eexitExportExeclVo = TbOnlineEexitExportExeclVo.transform(c, merchantInfo);
            exports.add(eexitExportExeclVo);
        });
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        Date now = new Date();
        String yyyyMMddHHmmss = DateUtil.format(now, "yyyyMMddHHmmss");
        mv.addObject(NormalExcelConstants.FILE_NAME, "银行对账核流水明细-" + yyyyMMddHHmmss);
        mv.addObject(NormalExcelConstants.CLASS, TbOnlineEexitExportExeclVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("银行对账核对明细表", "银行对账核对明细表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exports);
        return mv;
    }

    @Override
    public ModelAndView exportEexitTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "银行对账流水明细模板");
        mv.addObject(NormalExcelConstants.CLASS, TbOnlineEexitExportExeclVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("银行对账流水明细", "银行对账流水明细"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }


    @Override
    public IPage<TbBankBillCheck> findSearchDataPage(Page<TbBankBillCheck> page, TbBankBillCheckSearchVo tbBankBillCheck) {
        tbBankBillCheck.setDataScope(SystemUtils.newInstance().findDataScope("tbBankBillCheck:page"));
        page.setOrder("desc");
        page.setField("transDate");
        page.initOrder();
        IPage<TbBankBillCheck> searchDataPage = this.mapper.findSearchDataPage(page, tbBankBillCheck);
        decorate(searchDataPage.getRecords().iterator());
        addSubTotal(searchDataPage.getRecords());
        addSumTotal(searchDataPage.getRecords(), tbBankBillCheck);
        return searchDataPage;
    }

    @Transactional
    @Override
    public void confirmCheckSumAndDetail(TbBankBillCheck tbBankBillCheck) {
        this.mapper.insertSumRecord(tbBankBillCheck);
        this.mapper.insertDetailRecord(tbBankBillCheck);
    }

    private void decorate(Iterator<TbBankBillCheck> collection) {
        Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(), ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        while (collection.hasNext()) {
            decorate(collection.next(), merchantInfo);
        }
    }

    private void decorate(TbBankBillCheck billCheck, Map<String, String> merchantInfo) {
        String merchantGroupId = billCheck.getMerchantGroupId();
        String merchantId = billCheck.getMerchantId();
        String siteId = billCheck.getSiteId();
        Integer serviceType = billCheck.getServiceType();
        String confirmState = billCheck.getConfirmState();
        String merchantGroupIdName = merchantInfo.get(merchantGroupId);
        String merchantIdName = merchantInfo.get(merchantId);
        String siteIdName = merchantInfo.get(siteId);
        billCheck.setMerchantGroupIdName(merchantGroupIdName == null ? "未知拓展方" : merchantGroupIdName);
        billCheck.setMerchantIdName(merchantIdName == null ? "未知运营方" : merchantIdName);
        billCheck.setSiteIdName(siteIdName == null ? "未知场景" : siteIdName);
        billCheck.setServiceTypeName(ServiceTypeMapToNameUtil.getServiceTypeName(serviceType));
        billCheck.setConfirmStateName("0".equals(confirmState) ? "待核对" : "已核对");

        String bankTotalAmount = StringUtils.isBlank(billCheck.getBankTotalAmount())?"0":billCheck.getBankTotalAmount();
        String platformTotalAmount = StringUtils.isBlank(billCheck.getPlatformTotalAmount())?"0":billCheck.getPlatformTotalAmount();
        String diffTotalAmount = StringUtils.isBlank(billCheck.getDiffTotalAmount())?"0":billCheck.getDiffTotalAmount();

        bankTotalAmount = (new BigDecimal(bankTotalAmount)).movePointLeft(2).toString();
        billCheck.setBankTotalAmount(bankTotalAmount);

        platformTotalAmount = (new BigDecimal(platformTotalAmount)).movePointLeft(2).toString();
        billCheck.setPlatformTotalAmount(platformTotalAmount);

        diffTotalAmount = (new BigDecimal(diffTotalAmount)).movePointLeft(2).toString();
        billCheck.setDiffTotalAmount(diffTotalAmount);

    }

    private void addSubTotal(List<TbBankBillCheck> list) {
        if (list == null || list.isEmpty())
            return;
        int bankTotalCount = 0;
        int platformTotalCount = 0;
        int diffTotalCount = 0;
        BigDecimal platformTotalAmount = new BigDecimal(0);
        BigDecimal bankTotalAmount = new BigDecimal(0);
        BigDecimal diffTotalAmount = new BigDecimal(0);

        for (TbBankBillCheck c : list) {
            String bankTotalCount1 = c.getBankTotalCount();
            bankTotalCount += Integer.valueOf(bankTotalCount1 == null ? "0" : bankTotalCount1);
            String platformTotalCount1 = c.getPlatformTotalCount();
            platformTotalCount += Integer.valueOf(platformTotalCount1 == null ? "0" : platformTotalCount1);

            String diffTotalCount1 = c.getDiffTotalCount();
            diffTotalCount += Integer.valueOf(diffTotalCount1 == null ? "0" : diffTotalCount1);
            bankTotalAmount=bankTotalAmount.add(new BigDecimal(StringUtils.isBlank(c.getBankTotalAmount()) ? "0" : c.getBankTotalAmount()));
            platformTotalAmount=platformTotalAmount.add(new BigDecimal(StringUtils.isBlank(c.getPlatformTotalAmount()) ? "0" : c.getPlatformTotalAmount()));
            diffTotalAmount=diffTotalAmount.add(new BigDecimal(StringUtils.isBlank(c.getDiffTotalAmount())? "0" : c.getDiffTotalAmount()));
        }
        TbBankBillCheck subTotal = new TbBankBillCheck();
        subTotal.setMerchantGroupIdName("小计");
        subTotal.setBankTotalCount(String.valueOf(bankTotalCount));
        subTotal.setPlatformTotalCount(String.valueOf(platformTotalCount));
        subTotal.setDiffTotalCount(String.valueOf(diffTotalCount));

        subTotal.setPlatformTotalAmount(platformTotalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        subTotal.setBankTotalAmount(bankTotalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        subTotal.setDiffTotalAmount(diffTotalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        list.add(subTotal);
    }

    private void addSumTotal(List<TbBankBillCheck> list, TbBankBillCheckSearchVo billCheck) {
        if(list==null||list.isEmpty())
            return;
        TbBankBillCheck sum = this.mapper.getSum(billCheck);
        sum.setBankTotalAmount((new BigDecimal(sum.getBankTotalAmount())).movePointLeft(2).toString());
        sum.setPlatformTotalAmount((new BigDecimal(sum.getPlatformTotalAmount())).movePointLeft(2).toString());
        sum.setDiffTotalAmount((new BigDecimal(sum.getDiffTotalAmount())).movePointLeft(2).toString());
        if (sum != null) {
            sum.setMerchantGroupIdName("合计");
            list.add(sum);
        }
    }


}