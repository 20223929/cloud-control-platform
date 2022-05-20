package com.hgsoft.modules.bankbillcheck.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.bankbillcheck.entity.TBMerchantBillCheckExpPlatformEtc;
import com.hgsoft.modules.bankbillcheck.entity.TBMerchantBillCheckExpPlatformEtcExportExcelVo;
import com.hgsoft.modules.bankbillcheck.entity.TBMerchantBillCheckExpPlatformEtcSearchVo;
import com.hgsoft.modules.bankbillcheck.mapper.TBMerchantBillCheckExpPlatformEtcMapper;
import com.hgsoft.modules.bankbillcheck.service.TBMerchantBillCheckExpPlatformEtcService;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("com.hgsoft.modules.bankbillcheck.service.TBMerchantBillCheckExpPlatformEtcService")
public class TBMerchantBillCheckExpPlatformEtcServiceImpl implements TBMerchantBillCheckExpPlatformEtcService {

    @Autowired
    private MerchantManageService merchantManageService;

    @Autowired
    private TBMerchantBillCheckExpPlatformEtcMapper mapper;

    @Override
    public ModelAndView exportData(HttpServletRequest request, TBMerchantBillCheckExpPlatformEtcSearchVo s) {
        List<TBMerchantBillCheckExpPlatformEtc> list = mapper.findSearchDataAll(s);

        Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(),
                ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        List<TBMerchantBillCheckExpPlatformEtcExportExcelVo> results = list.stream()
                .map(c -> TBMerchantBillCheckExpPlatformEtcExportExcelVo.getInstance(c, merchantInfo))
                .collect(Collectors.toList());
        if(results.size()>0){
            results.add(getSubTotal(list));
            results.add(getAllTotal(s));
        }
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        Date now = new Date();
        String yyyyMMddHHmmss = DateUtil.format(now, "yyyyMMddHHmmss");
        mv.addObject(NormalExcelConstants.FILE_NAME, "商户对账核对表-" + yyyyMMddHHmmss);
        mv.addObject(NormalExcelConstants.CLASS, TBMerchantBillCheckExpPlatformEtcExportExcelVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商户对账核对", "商户对账核对"));
        mv.addObject(NormalExcelConstants.DATA_LIST, results);
        return mv;
    }

    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "商户对账核对模板");
        mv.addObject(NormalExcelConstants.CLASS, TBMerchantBillCheckExpPlatformEtcExportExcelVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商户对账核对表", "商户对账核对"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    @Override
    public IPage<TBMerchantBillCheckExpPlatformEtcExportExcelVo> findSearchDataPage(Page<TBMerchantBillCheckExpPlatformEtc> page, TBMerchantBillCheckExpPlatformEtcSearchVo entity) {
        IPage<TBMerchantBillCheckExpPlatformEtc> searchDataPage = mapper.findSearchDataPage(page, entity);

        Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(),
                ShiroSecurityUtil.getPrincipal().getIsSuperUser());

        IPage<TBMerchantBillCheckExpPlatformEtcExportExcelVo> convert = searchDataPage
                .convert(c -> TBMerchantBillCheckExpPlatformEtcExportExcelVo.getInstance(c, merchantInfo));

        //添加小计与总计
        if(searchDataPage.getRecords().size()>0){
            convert.getRecords().add(getSubTotal(searchDataPage.getRecords()));
            convert.getRecords().add(getAllTotal(entity));
        }
        return convert;
    }


    @Override
    public String errorRegister(TBMerchantBillCheckExpPlatformEtc param) {
        if (StrUtil.isBlank(param.getMerchantId())) {
            return "运营方不能为空";
        }
        if (StrUtil.isBlank(param.getMerchantGroupId())) {
            return "拓展方不能为空";
        }
        if (StrUtil.isBlank(param.getSiteId())) {
            return "场景不能为空";
        }
        if (StrUtil.isBlank(param.getTransDate())) {
            return "交易日期不能为空";
        }
        if (StrUtil.isBlank(param.getRemark())) {
            return "备注不能为空";
        }
        if(StrUtil.isBlank(param.getConfirmMan())){
            return "确认人必填";
        }
        TBMerchantBillCheckExpPlatformEtc one = mapper.getOne(param);
        param.triggerErrorRegister();
        if(one == null){
            mapper.insert(param);
        }else if(one.canErrorRegister()){
            mapper.update(param);
        }else{
            return "系统内部,该记录状态无法进行异常登记";
        }
        return "成功";
    }

    @Override
    public String confirmRegister(TBMerchantBillCheckExpPlatformEtc param) {
        if (StrUtil.isBlank(param.getMerchantId())) {
            return "运营方不能为空";
        }
        if (StrUtil.isBlank(param.getMerchantGroupId())) {
            return "拓展方不能为空";
        }
        if (StrUtil.isBlank(param.getSiteId())) {
            return "场景不能为空";
        }
        if (StrUtil.isBlank(param.getTransDate())) {
            return "交易日期不能为空";
        }
        if (StrUtil.isBlank(param.getRemark())) {
            return "备注不能为空";
        }
        if (param.getTotalAmount() == null) {
            return "确认流水数不能为空";
        }
        if (param.getTotalAmount() == null) {
            return "确认流水金额不能为空";
        }
        TBMerchantBillCheckExpPlatformEtc one = mapper.getOne(param);
        param.triggerConfirmRegister();
        if(one == null){
            mapper.insert(param);
        }else if(one.canConfirmRegister()){
            mapper.update(param);
        }else{
            return "系统内部,该记录状态无法进行确认登记";
        }
        return "成功";

    }

    private TBMerchantBillCheckExpPlatformEtcExportExcelVo getSubTotal(List<TBMerchantBillCheckExpPlatformEtc> list) {
        TBMerchantBillCheckExpPlatformEtc result = new TBMerchantBillCheckExpPlatformEtc();
        long systemTotalSum = 0;
        long systemTotalAmountSum = 0;
        long otherTotalSum = 0;
        long otherTotalAmountSum = 0;
        for (TBMerchantBillCheckExpPlatformEtc e : list) {

            Long systemTotalCount = e.getSystemTotalCount();
            Long systemTotalAmount = e.getSystemTotalAmount();
            Long otherTotalCount = e.getOtherTotalCount();
            Long otherTotalAmount = e.getOtherTotalAmount();
            systemTotalSum += nullReturnZero(systemTotalCount);
            systemTotalAmountSum += nullReturnZero(systemTotalAmount);
            otherTotalSum += nullReturnZero(otherTotalCount);
            otherTotalAmountSum += nullReturnZero(otherTotalAmount);
        }
        result.setSystemTotalCount(systemTotalSum);
        result.setSystemTotalAmount(systemTotalAmountSum);
        result.setOtherTotalCount(otherTotalSum);
        result.setOtherTotalAmount(otherTotalAmountSum);
        return TBMerchantBillCheckExpPlatformEtcExportExcelVo.getSumTotalInstance(result,"小计");
    }

    private TBMerchantBillCheckExpPlatformEtcExportExcelVo getAllTotal(TBMerchantBillCheckExpPlatformEtcSearchVo s){
        TBMerchantBillCheckExpPlatformEtc result = mapper.getSum(s);
        return TBMerchantBillCheckExpPlatformEtcExportExcelVo.getSumTotalInstance(result,"合计");
    }

    private static Long nullReturnZero(Long num) {
        return num == null ? 0L : num;
    }
}
