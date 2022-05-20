package com.hgsoft.modules.bankbillcheck.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.bankbillcheck.entity.TbMerchantBillCheckDetail;
import com.hgsoft.modules.bankbillcheck.entity.TbMerchantBillCheckDetailExportExcelVo;
import com.hgsoft.modules.bankbillcheck.entity.TbMerchantBillCheckDetailSearchVo;
import com.hgsoft.modules.bankbillcheck.mapper.TbMerchantBillCheckDetailMapper;
import com.hgsoft.modules.bankbillcheck.service.TbMerchantBillCheckDetailService;
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

@Service("com.hgsoft.modules.bankbillcheck.service.TbMerchantBillCheckDetailServiceImpl")
public class TbMerchantBillCheckDetailServiceImpl implements TbMerchantBillCheckDetailService {

    @Autowired
    TbMerchantBillCheckDetailMapper mapper;

    @Autowired
    private MerchantManageService merchantManageService;

    @Override
    public ModelAndView exportData(HttpServletRequest request, TbMerchantBillCheckDetailSearchVo s) {
        List<TbMerchantBillCheckDetail> list = mapper.findSearchDataAll(s);
        Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(),
                ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        List<TbMerchantBillCheckDetailExportExcelVo> results = list.stream()
                .map(c -> TbMerchantBillCheckDetailExportExcelVo.getInstance(c, merchantInfo))
                .collect(Collectors.toList());
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        Date now = new Date();
        String yyyyMMddHHmmss = DateUtil.format(now, "yyyyMMddHHmmss");
        mv.addObject(NormalExcelConstants.FILE_NAME, "商户对账异常明细-" + yyyyMMddHHmmss);
        mv.addObject(NormalExcelConstants.CLASS, TbMerchantBillCheckDetailExportExcelVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商户对账异常明细", "商户对账异常明细"));
        mv.addObject(NormalExcelConstants.DATA_LIST, results);
        return mv;
    }

    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "商户异常明细模板");
        mv.addObject(NormalExcelConstants.CLASS, TbMerchantBillCheckDetailExportExcelVo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商户异常明细模板", "商户异常明细模板"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    @Override
    public IPage<TbMerchantBillCheckDetailExportExcelVo> findSearchDataPage(Page<TbMerchantBillCheckDetail> page, TbMerchantBillCheckDetailSearchVo entity) {
        IPage<TbMerchantBillCheckDetail> searchDataPage = mapper.findSearchDataPage(page, entity);

        Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(),
                ShiroSecurityUtil.getPrincipal().getIsSuperUser());

        IPage<TbMerchantBillCheckDetailExportExcelVo> convert = searchDataPage
                .convert(c -> TbMerchantBillCheckDetailExportExcelVo.getInstance(c, merchantInfo));

        return convert;
    }
}
