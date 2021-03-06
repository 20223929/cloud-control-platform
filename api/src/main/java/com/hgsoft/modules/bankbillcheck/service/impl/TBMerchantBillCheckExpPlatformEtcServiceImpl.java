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
        mv.addObject(NormalExcelConstants.FILE_NAME, "?????????????????????-" + yyyyMMddHHmmss);
        mv.addObject(NormalExcelConstants.CLASS, TBMerchantBillCheckExpPlatformEtcExportExcelVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("??????????????????", "??????????????????"));
        mv.addObject(NormalExcelConstants.DATA_LIST, results);
        return mv;
    }

    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "????????????????????????");
        mv.addObject(NormalExcelConstants.CLASS, TBMerchantBillCheckExpPlatformEtcExportExcelVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("?????????????????????", "??????????????????"));
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

        //?????????????????????
        if(searchDataPage.getRecords().size()>0){
            convert.getRecords().add(getSubTotal(searchDataPage.getRecords()));
            convert.getRecords().add(getAllTotal(entity));
        }
        return convert;
    }


    @Override
    public String errorRegister(TBMerchantBillCheckExpPlatformEtc param) {
        if (StrUtil.isBlank(param.getMerchantId())) {
            return "?????????????????????";
        }
        if (StrUtil.isBlank(param.getMerchantGroupId())) {
            return "?????????????????????";
        }
        if (StrUtil.isBlank(param.getSiteId())) {
            return "??????????????????";
        }
        if (StrUtil.isBlank(param.getTransDate())) {
            return "????????????????????????";
        }
        if (StrUtil.isBlank(param.getRemark())) {
            return "??????????????????";
        }
        if(StrUtil.isBlank(param.getConfirmMan())){
            return "???????????????";
        }
        TBMerchantBillCheckExpPlatformEtc one = mapper.getOne(param);
        param.triggerErrorRegister();
        if(one == null){
            mapper.insert(param);
        }else if(one.canErrorRegister()){
            mapper.update(param);
        }else{
            return "????????????,???????????????????????????????????????";
        }
        return "??????";
    }

    @Override
    public String confirmRegister(TBMerchantBillCheckExpPlatformEtc param) {
        if (StrUtil.isBlank(param.getMerchantId())) {
            return "?????????????????????";
        }
        if (StrUtil.isBlank(param.getMerchantGroupId())) {
            return "?????????????????????";
        }
        if (StrUtil.isBlank(param.getSiteId())) {
            return "??????????????????";
        }
        if (StrUtil.isBlank(param.getTransDate())) {
            return "????????????????????????";
        }
        if (StrUtil.isBlank(param.getRemark())) {
            return "??????????????????";
        }
        if (param.getTotalAmount() == null) {
            return "???????????????????????????";
        }
        if (param.getTotalAmount() == null) {
            return "??????????????????????????????";
        }
        TBMerchantBillCheckExpPlatformEtc one = mapper.getOne(param);
        param.triggerConfirmRegister();
        if(one == null){
            mapper.insert(param);
        }else if(one.canConfirmRegister()){
            mapper.update(param);
        }else{
            return "????????????,???????????????????????????????????????";
        }
        return "??????";

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
        return TBMerchantBillCheckExpPlatformEtcExportExcelVo.getSumTotalInstance(result,"??????");
    }

    private TBMerchantBillCheckExpPlatformEtcExportExcelVo getAllTotal(TBMerchantBillCheckExpPlatformEtcSearchVo s){
        TBMerchantBillCheckExpPlatformEtc result = mapper.getSum(s);
        return TBMerchantBillCheckExpPlatformEtcExportExcelVo.getSumTotalInstance(result,"??????");
    }

    private static Long nullReturnZero(Long num) {
        return num == null ? 0L : num;
    }
}
