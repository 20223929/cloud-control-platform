package com.hgsoft.modules.settlement.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.auto.poi.def.MapExcelConstants;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.excel.entity.params.ExcelExportEntity;
import com.hgsoft.ecip.auto.poi.util.ExcelUtil;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.auto.poi.view.EcipMapExcelView;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.ecip.framework.shiro.ShiroUser;
import com.hgsoft.ecip.web.util.SystemUtils;
import com.hgsoft.modules.bankbillcheck.entity.TbEtcTransactionEexitExportExeclVo;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.settlement.entity.SettlementSearchVo;
import com.hgsoft.modules.settlement.entity.TbCenterSettlement;
import com.hgsoft.modules.settlement.entity.TbCenterSettlementDetail;
import com.hgsoft.modules.settlement.mapper.TbCenterSettlementMapper;
import com.hgsoft.modules.settlement.service.TbCenterSettlementService;
import com.hgsoft.modules.utils.CallBackEcipEntityExcelView;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 联网中心结算ServiceImpl
 *
 * @author 何志豪
 * @version 2021-05-08 04:55:35
 */

@Service("com.hgsoft.modules.settlement.service.TbCenterSettlementService")
public class TbCenterSettlementServiceImpl implements TbCenterSettlementService {

    @Autowired
    private TbCenterSettlementMapper mapper;

    @Autowired
    private MerchantManageService merchantManageService;

    private final String[] tipString = {
            "settlementDay(【主键settlementDay】): 可以不填写主键settlementDay；",
            "merchantGroupId(【主键merchantGroupId】): 可以不填写主键merchantGroupId；",
            "merchantId(【主键merchantId】): 可以不填写主键merchantId；",
            "siteId(【主键siteId】): 可以不填写主键siteId；",
            "settlementDay(【对账日】): 必填",
            "serviceType(【服务类型】): 必填",
            "merchantGroupId(【拓展方】): 必填",
            "merchantId(【运营方】): 必填",
            "siteId(【场景】): 必填",
            "realTotalAmount(【应付商户金额(单位元)】): 必填",
            "clearServiceTotalFee(【清分服务费(单位元）】): 必填",
            "confirmState(【确认状态】): 必填",
            "confirmMan(【确认人】): 必填",
            "confirmTime(【确认时间】): 必填",
            "remarks(【备注信息】): 非必填"
    };

    /**
     * 分页查询
     *
     * @param page
     * @param vo
     * @return
     */
    @Override
    public IPage<TbCenterSettlement> tbCenterSettlementPage(Page<TbCenterSettlement> page, SettlementSearchVo vo) {

        vo.setDataScope(SystemUtils.newInstance().findDataScope("tbCenterSettlement:page"));
        page.initOrder();
        IPage<TbCenterSettlement> result = mapper.findPage(page, vo);
        List<TbCenterSettlement> records = result.getRecords();
        decorate(records);
        TbCenterSettlement subTotal = TbCenterSettlement.createSubTotal(records);
        if(subTotal!=null){
            records.add(subTotal);
        }
        addSumTotal(records,vo);
        return result;
    }


    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, SettlementSearchVo tbCenterSettlement) {
        List<TbCenterSettlement> list = mapper.findAllList(tbCenterSettlement);
        decorate(list);
        addSumTotal(list,tbCenterSettlement);
        int row = list.size()+1;
        ModelAndView mv = new ModelAndView(new CallBackEcipEntityExcelView(workbook->{
            Sheet sheet = workbook.getSheetAt(0);
            ExcelUtil.mergeRegion(sheet,row , row, 0, 4);
            sheet.getRow(row).getCell(0).setCellValue("合计");
        }));
        Date now = new Date();
        String yyyyMMddHHmmss = DateUtil.format(now, "yyyyMMddHHmmss");
        mv.addObject(NormalExcelConstants.FILE_NAME, "联网中心结算-" + yyyyMMddHHmmss);
        mv.addObject(NormalExcelConstants.CLASS, TbCenterSettlement.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("联网中心结算", "联网中心结算"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }



    @Override
    public void confirm(TbCenterSettlement e) {
        mapper.updateSettlementConfirm(e);
    }

    private void decorate(List<TbCenterSettlement> collection) {
        ShiroUser principal = ShiroSecurityUtil.getPrincipal();
        String userId = principal.getId();
        Boolean isSuperUser = principal.getIsSuperUser();
        Map<String, String> merchantByUserId = merchantManageService.getAllMerchantByUserId(userId, isSuperUser);
        collection.forEach(c->c.triggerShowState(merchantByUserId));
    }


    private void addSumTotal(List<TbCenterSettlement> list, SettlementSearchVo billCheck) {
        TbCenterSettlement sum = this.mapper.getSum(billCheck);
        if (sum != null) {
            sum.setRealTotalAmountStr(sum.movePointToLeft2(sum.getRealTotalAmount()));
            sum.setClearServiceTotalFeeStr(sum.roundHalfUpScale2(sum.getClearServiceTotalFee()));
            BigDecimal merchantTotalAmount = (new BigDecimal(sum.getRealTotalAmount())).subtract(sum.getClearServiceTotalFee());
            sum.setMerchantTotalAmountStr(sum.roundHalfUpScale2(merchantTotalAmount));
            sum.setPayTotalAmountStr(sum.movePointToLeft2(sum.getPayTotalAmount()));
            sum.setMerchantGroupIdName("合计");
            list.add(sum);
        }
    }

    /**
     * 分页查询
     * @param page
     * @param vo
     * @return
     */
    @Override
    public IPage<TbEtcTransactionEexitExportExeclVo> findEexitPage(Page<TbEtcTransactionEexitExportExeclVo> page, SettlementSearchVo vo) {
        vo.setDataScope(SystemUtils.newInstance().findDataScope("tbEtcTransactionEexit:page"));
        page.initOrder();
        IPage<TbEtcTransactionEexitExportExeclVo> eexitPage = this.mapper.findEexitPage(page, vo);
        ShiroUser principal = ShiroSecurityUtil.getPrincipal();
        String userId = principal.getId();
        Boolean isSuperUser = principal.getIsSuperUser();
        Map<String, String> merchantByUserId = merchantManageService.getAllMerchantByUserId(userId, isSuperUser);
        eexitPage.getRecords().forEach(c->c.innerTransform(merchantByUserId));
        return eexitPage;
    }

    /**
     * 导出明细数据
     */
    @Override
    public ModelAndView exportDataForEexit(HttpServletRequest request, SettlementSearchVo vo) {
        List<TbEtcTransactionEexitExportExeclVo> list = this.mapper.findEexitAll(vo);
        ShiroUser principal = ShiroSecurityUtil.getPrincipal();
        String userId = principal.getId();
        Boolean isSuperUser = principal.getIsSuperUser();
        Map<String, String> merchantByUserId = merchantManageService.getAllMerchantByUserId(userId, isSuperUser);
        list.forEach(c->c.innerTransform(merchantByUserId));
        ModelAndView mv = new ModelAndView(new EcipMapExcelView());
        Date now = new Date();

        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
        entity.add(new ExcelExportEntity("服务类型", "serviceTypeStr"));
        entity.add(new ExcelExportEntity("拓展方", "merchantGroupIdStr",30));
        entity.add(new ExcelExportEntity("运营方", "merchantIdStr",30));
        entity.add(new ExcelExportEntity("场景", "siteIdStr",30));
        entity.add(new ExcelExportEntity("流水号", "transactionId",30));
        entity.add(new ExcelExportEntity("ETC卡号", "etcCardId",30));
        entity.add(new ExcelExportEntity("车牌号码", "vehiclePlate"));
        entity.add(new ExcelExportEntity("车牌颜色", "vehicleColorStr"));
        ExcelExportEntity transTimeEntity = new ExcelExportEntity("交易时间", "transTime",30);
        transTimeEntity.setFormat("yyyy-MM-dd HH:mm:ss");
        entity.add(transTimeEntity);
        entity.add(new ExcelExportEntity("实收金额", "merchantRealFeeStr"));
        entity.add(new ExcelExportEntity("应收金额", "merchantPayFeeStr"));
        entity.add(new ExcelExportEntity("清分日", "clearDate"));

        String yyyyMMddHHmmss = DateUtil.format(now, "yyyyMMddHHmmss");
        mv.addObject(MapExcelConstants.FILE_NAME, "联网中心结算明细-" + yyyyMMddHHmmss);
        //mv.addObject(NormalExcelConstants.CLASS, TbEtcTransactionEexitExportExeclVo.class);
        mv.addObject(MapExcelConstants.PARAMS, new ExportParams("联网中心结算明细", "联网中心结算明细"));
        mv.addObject(MapExcelConstants.ENTITY_LIST, entity);
        List<Map<String,Object>> objects = new ArrayList<>(list.size());
        list.forEach(c->{
            Map<String, Object> stringObjectMap = BeanUtil.beanToMap(c);
            objects.add(stringObjectMap);
        });
        mv.addObject(MapExcelConstants.MAP_LIST, objects); //数据集合
        return mv;
    }

    @Override
    public Long countEexit(SettlementSearchVo vo) {
        return mapper.countEexit(vo);
    }

    /**
     * 导出模板
     */
    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "联网中心结算模板");
        mv.addObject(NormalExcelConstants.CLASS, TbCenterSettlement.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("联网中心结算", "联网中心结算"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    /**
     * 导出模板
     */
    @Override
    public ModelAndView exportTemplateForEexit(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "联网中心结算明细模板");
        mv.addObject(NormalExcelConstants.CLASS, TbCenterSettlementDetail.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("联网中心结算明细", "联网中心结算明细"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }
}