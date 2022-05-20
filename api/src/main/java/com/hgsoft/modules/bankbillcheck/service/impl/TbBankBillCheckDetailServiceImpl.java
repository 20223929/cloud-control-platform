package com.hgsoft.modules.bankbillcheck.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.ecip.web.util.SystemUtils;
import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheckDetail;
import com.hgsoft.modules.bankbillcheck.mapper.TbBankBillCheckDetailMapper;
import com.hgsoft.modules.bankbillcheck.service.TbBankBillCheckDetailService;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 银行对账明细表ServiceImpl
 *
 * @author 何志豪
 * @version 2021-04-19 03:07:10
 */

@Service("com.hgsoft.modules.bankbillcheck.service.TbBankBillCheckDetailService")
public class TbBankBillCheckDetailServiceImpl extends CrudServiceImpl<TbBankBillCheckDetailMapper, TbBankBillCheckDetail> implements TbBankBillCheckDetailService {

    @Autowired
    private MerchantManageService merchantManageService;

    private final String[] tipString = {
            "transDate(【主键transDate】): 可以不填写主键transDate；",
            "merchantGroupId(【主键merchantGroupId】): 可以不填写主键merchantGroupId；",
            "merchantId(【主键merchantId】): 可以不填写主键merchantId；",
            "siteId(【主键siteId】): 可以不填写主键siteId；",
            "equipmentId(【主键equipmentId】): 可以不填写主键equipmentId；",
            "transDate(【对账交易日期】): 必填",
            "merchantGroupId(【一级运营商】): 必填",
            "merchantId(【二级运营商】): 必填",
            "siteId(【三级运营商】): 必填",
            "equipmentId(【四级设备编号】): 必填",
            "platformTotalCount(【本系统流水总数】): 必填",
            "platformTotalAmount(【本系统流水总金额(分）】): 必填",
            "bankTotalCount(【银行流水总数】): 必填",
            "bankTotalAmount(【银行流水总金额(分)】): 必填",
            "diffTotalCount(【流水总数差额】): 必填",
            "diffTotalAmount(【流水总金额差额(分)】): 必填",
            "createTime(【创建时间】): 必填",
            "remarks(【备注信息】): 非必填"
    };

    /**
     * 分页查询
     *
     * @param page
     * @param tbBankBillCheckDetail
     * @return
     */
    @Override
    public IPage<TbBankBillCheckDetail> tbBankBillCheckDetailPage(Page<TbBankBillCheckDetail> page, TbBankBillCheckDetail tbBankBillCheckDetail) {
        tbBankBillCheckDetail.setDataScope(SystemUtils.newInstance().findDataScope("tbBankBillCheckDetail:page"));
        page.initOrder();
        IPage<TbBankBillCheckDetail> page1 = this.findPage(page, tbBankBillCheckDetail);
        decorate(page1.getRecords().iterator());
        addSubTotal(page1.getRecords());
        return page1;
    }

    @Override
    public TbBankBillCheckDetail getByPrimaryKey(String transDate, String merchantGroupId, String merchantId, String siteId, String equipmentId) {
        TbBankBillCheckDetail one = this.getOne(new LambdaQueryWrapper<TbBankBillCheckDetail>()
                .eq(TbBankBillCheckDetail::getTransDate, transDate)
                .eq(TbBankBillCheckDetail::getMerchantGroupId, merchantGroupId)
                .eq(TbBankBillCheckDetail::getMerchantId, merchantId)
                .eq(TbBankBillCheckDetail::getSiteId, siteId)
                .eq(TbBankBillCheckDetail::getEquipmentId, equipmentId));
        Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(), ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        decorate(one, merchantInfo);
        return one;
    }


    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, TbBankBillCheckDetail tbBankBillCheckDetail) {
        List<TbBankBillCheckDetail> list = this.findList(tbBankBillCheckDetail);
        decorate(list.iterator());
        addSubTotal(list);
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "银行对账明细表-" + System.currentTimeMillis());
        mv.addObject(NormalExcelConstants.CLASS, TbBankBillCheckDetail.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("银行对账明细表", "银行对账明细表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    /**
     * 导出模板
     */
    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "银行对账明细表模板");
        mv.addObject(NormalExcelConstants.CLASS, TbBankBillCheckDetail.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("银行对账明细表", "银行对账明细表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    private void decorate(Iterator<TbBankBillCheckDetail> collection) {
        Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(), ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        while (collection.hasNext()) {
            decorate(collection.next(), merchantInfo);
        }
    }

    private void decorate(TbBankBillCheckDetail billCheck, Map<String, String> merchantInfo) {
        String merchantGroupId = billCheck.getMerchantGroupId();
        String merchantId = billCheck.getMerchantId();
        String siteId = billCheck.getSiteId();
        String merchantGroupIdName = merchantInfo.get(merchantGroupId);
        String merchantIdName = merchantInfo.get(merchantId);
        String siteIdName = merchantInfo.get(siteId);
        billCheck.setMerchantGroupIdName(merchantGroupIdName == null ? "未知一级商户" : merchantGroupIdName);
        billCheck.setMerchantIdName(merchantIdName == null ? "未知二级商户" : merchantIdName);
        billCheck.setSiteIdName(siteIdName == null ? "未知三级商户" : siteIdName);

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

    private void addSubTotal(List<TbBankBillCheckDetail> list) {
        if (list == null || list.isEmpty())
            return;
        int bankTotalCount = 0;
        int platformTotalCount = 0;
        int diffTotalCount = 0;
        BigDecimal platformTotalAmount = new BigDecimal(0);
        BigDecimal bankTotalAmount = new BigDecimal(0);
        BigDecimal diffTotalAmount = new BigDecimal(0);

        for (TbBankBillCheckDetail c : list) {
            bankTotalCount += Integer.valueOf(c.getBankTotalCount());
            platformTotalCount += Integer.valueOf(c.getPlatformTotalCount());
            diffTotalCount += Integer.valueOf(c.getDiffTotalCount());
            bankTotalAmount=bankTotalAmount.add(new BigDecimal(c.getBankTotalAmount()));
            platformTotalAmount = platformTotalAmount.add(new BigDecimal(c.getPlatformTotalAmount()));
            diffTotalAmount = diffTotalAmount.add(new BigDecimal(c.getDiffTotalAmount()));
        }
        TbBankBillCheckDetail subTotal = new TbBankBillCheckDetail();
        subTotal.setMerchantGroupIdName("小计");
        subTotal.setBankTotalCount(String.valueOf(bankTotalCount));
        subTotal.setPlatformTotalCount(String.valueOf(platformTotalCount));
        subTotal.setDiffTotalCount(String.valueOf(diffTotalCount));

        subTotal.setPlatformTotalAmount(platformTotalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        subTotal.setBankTotalAmount(bankTotalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        subTotal.setDiffTotalAmount(diffTotalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        list.add(subTotal);
    }

}