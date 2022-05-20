package com.hgsoft.modules.bankbillcheck.service.impl;

import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheckDetail;
import com.hgsoft.modules.bankbillcheck.entity.TbCenterBillCheckDetail;
import com.hgsoft.modules.bankbillcheck.mapper.TbCenterBillCheckDetailMapper;
import com.hgsoft.modules.bankbillcheck.service.TbCenterBillCheckDetailService;

import com.hgsoft.ecip.framework.core.service.impl.CrudServiceImpl;

import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.ecip.framework.shiro.ShiroUser;

import com.hgsoft.ecip.web.util.SystemUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.common.response.Page;

import com.hgsoft.modules.merchantcommon.MerchantManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

import com.hgsoft.ecip.framework.util.IdWorkerInit;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.ExcelImportUtil;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.excel.entity.ImportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import org.apache.commons.lang3.StringUtils;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * 联网中心对账核对明细表ServiceImpl
 *
 * @author 何志豪
 * @version 2021-04-21 02:34:34
 */

@Service("com.hgsoft.modules.bankbillcheck.service.TbCenterBillCheckDetailService")
public class TbCenterBillCheckDetailServiceImpl extends CrudServiceImpl<TbCenterBillCheckDetailMapper, TbCenterBillCheckDetail> implements TbCenterBillCheckDetailService {

    @Autowired
    private MerchantManageService merchantManageService;

    private final String[] tipString = {
            "transDate(【主键transDate】): 可以不填写主键transDate；",
            "merchantGroupId(【主键merchantGroupId】): 可以不填写主键merchantGroupId；",
            "merchantId(【主键merchantId】): 可以不填写主键merchantId；",
            "siteId(【主键siteId】): 可以不填写主键siteId；",
            "equipmentId(【主键equipmentId】): 可以不填写主键equipmentId；",
            "transDate(【银行对账交易日期】): 必填",
            "merchantGroupId(【拓展方】): 必填",
            "merchantId(【运营方】): 必填",
            "siteId(【场景】): 必填",
            "equipmentId(【设备编号】): 必填",
            "confirmTotalCount(【用户确认流水数量】): 必填",
            "confirmTotalAmount(【用户确认流水金额(分)】): 必填",
            "remarks(【备注信息】): 非必填"
    };

    /**
     * 分页查询
     *
     * @param page
     * @param tbCenterBillCheckDetail
     * @return
     */
    @Override
    public IPage<TbCenterBillCheckDetail> tbCenterBillCheckDetailPage(Page<TbCenterBillCheckDetail> page, TbCenterBillCheckDetail tbCenterBillCheckDetail) {
        tbCenterBillCheckDetail.setDataScope(SystemUtils.newInstance().findDataScope("tbCenterBillCheckDetail:page"));
        page.initOrder();
        IPage<TbCenterBillCheckDetail> page1 = this.findPage(page, tbCenterBillCheckDetail);
        decorate(page1.getRecords().iterator());
        addSubTotal(page1.getRecords());
        return page1;
    }

    @Override
    public TbCenterBillCheckDetail getByPrimaryKey(String transDate, String merchantGroupId, String merchantId, String siteId, String equipmentId) {
        TbCenterBillCheckDetail one = this.getOne(new LambdaQueryWrapper<TbCenterBillCheckDetail>()
                .eq(TbCenterBillCheckDetail::getTransDate, transDate)
                .eq(TbCenterBillCheckDetail::getMerchantGroupId, merchantGroupId)
                .eq(TbCenterBillCheckDetail::getMerchantId, merchantId)
                .eq(TbCenterBillCheckDetail::getSiteId, siteId)
                .eq(TbCenterBillCheckDetail::getEquipmentId, equipmentId));
        Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(), ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        decorate(one,merchantInfo);
        return one;
    }

    /**
     * 导出数据
     */
    @Override
    public ModelAndView exportData(HttpServletRequest request, TbCenterBillCheckDetail tbCenterBillCheckDetail) {
        List<TbCenterBillCheckDetail> list = this.findList(tbCenterBillCheckDetail);
        decorate(list.iterator());
        addSubTotal(list);
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "联网中心对账核对明细表-" + System.currentTimeMillis());
        mv.addObject(NormalExcelConstants.CLASS, TbCenterBillCheckDetail.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("联网中心对账核对明细表", "联网中心对账核对明细"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    /**
     * 导出模板
     */
    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "联网中心对账核对明细模板");
        mv.addObject(NormalExcelConstants.CLASS, TbCenterBillCheckDetail.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("联网中心对账核对明细表", "联网中心对账核对明细"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    private void decorate(Iterator<TbCenterBillCheckDetail> collection) {
        Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(), ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        while (collection.hasNext()) {
            decorate(collection.next(), merchantInfo);
        }
    }

    private void decorate(TbCenterBillCheckDetail billCheck, Map<String, String> merchantInfo) {
        String merchantGroupId = billCheck.getMerchantGroupId();
        String merchantId = billCheck.getMerchantId();
        String siteId = billCheck.getSiteId();
        String merchantGroupIdName = merchantInfo.get(merchantGroupId);
        String merchantIdName = merchantInfo.get(merchantId);
        String siteIdName = merchantInfo.get(siteId);
        billCheck.setMerchantGroupIdName(merchantGroupIdName == null ? "未知一级商户" : merchantGroupIdName);
        billCheck.setMerchantIdName(merchantIdName == null ? "未知二级商户" : merchantIdName);
        billCheck.setSiteIdName(siteIdName == null ? "未知三级商户" : siteIdName);



        Long confirmTotalAmount = billCheck.getConfirmTotalAmount() == null ? 0 : billCheck.getConfirmTotalAmount();
        Long realTotalAmount = billCheck.getRealTotalAmount() == null ? 0 : billCheck.getRealTotalAmount();
        Long fillTotalAmount = realTotalAmount - confirmTotalAmount;

        Long realTotalCount = billCheck.getRealTotalCount();
        realTotalCount = realTotalCount==null?0L:realTotalCount;
        Long confirmTotalCount = billCheck.getConfirmTotalCount();
        confirmTotalCount = confirmTotalCount==null?0L:confirmTotalCount;
        billCheck.setConfirmTotalCount(confirmTotalCount);
        billCheck.setFillTotalCount(realTotalCount-confirmTotalCount);
        String confirmTotalAmountStr = BigDecimal.valueOf(confirmTotalAmount).movePointLeft(2).toString();
        billCheck.setConfirmTotalAmountStr(confirmTotalAmountStr);

        String realTotalAmountStr = BigDecimal.valueOf(realTotalAmount).movePointLeft(2).toString();
        billCheck.setRealTotalAmountStr(realTotalAmountStr);

        String fillTotalAmountStr = BigDecimal.valueOf(fillTotalAmount).movePointLeft(2).toString();
        billCheck.setFillTotalAmountStr(fillTotalAmountStr);

    }

    private void addSubTotal(List<TbCenterBillCheckDetail> list) {
        if (list == null || list.isEmpty())
            return;

        Long confirmCount = 0L;
        Long confirmAmount = 0L;
        Long realCount = 0L;
        Long realAmount = 0L;
        Long fillCount = 0L;
        Long fillAmount = 0L;
        for (TbCenterBillCheckDetail detail : list) {
            Long realTotalAmount = detail.getRealTotalAmount();
            Long realTotalCount = detail.getRealTotalCount();
            Long confirmTotalAmount = detail.getConfirmTotalAmount();
            Long confirmTotalCount = detail.getConfirmTotalCount();
            realTotalAmount = realTotalAmount == null ? 0L : realTotalAmount;
            realTotalCount = realTotalCount == null ? 0L : realTotalCount;
            confirmTotalAmount = confirmTotalAmount == null ? 0L : confirmTotalAmount;
            confirmTotalCount = confirmTotalCount == null ? 0L : confirmTotalCount;

            confirmCount+=confirmTotalCount;
            confirmAmount+=confirmTotalAmount;

            realCount+=realTotalCount;
            realAmount+=realTotalAmount;
        }


        TbCenterBillCheckDetail subTotal = new TbCenterBillCheckDetail();
        subTotal.setMerchantGroupIdName("小计");
        subTotal.setConfirmTotalCount(confirmCount);
        subTotal.setConfirmTotalAmount(confirmAmount);
        subTotal.setRealTotalCount(realCount);
        subTotal.setRealTotalAmount(realAmount);
        subTotal.setFillTotalAmount(realAmount-confirmAmount);
        subTotal.setFillTotalCount(realCount-confirmCount);

        String confirmTotalAmountStr = BigDecimal.valueOf(confirmAmount).movePointLeft(2).toString();
        subTotal.setConfirmTotalAmountStr(confirmTotalAmountStr);

        String realTotalAmountStr = BigDecimal.valueOf(realAmount).movePointLeft(2).toString();
        subTotal.setRealTotalAmountStr(realTotalAmountStr);

        String fillTotalAmountStr = BigDecimal.valueOf(realAmount-confirmAmount).movePointLeft(2).toString();
        subTotal.setFillTotalAmountStr(fillTotalAmountStr);

        list.add(subTotal);
    }
}