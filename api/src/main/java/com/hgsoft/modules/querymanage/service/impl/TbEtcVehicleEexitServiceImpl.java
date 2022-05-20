package com.hgsoft.modules.querymanage.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.querymanage.entity.TbEtcVehicleEexit;
import com.hgsoft.modules.querymanage.mapper.TbEtcVehicleEexitMapper;
import com.hgsoft.modules.querymanage.service.TbEtcVehicleEexitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("com.hgsoft.modules.bankbillcheck.service.TbEtcVehicleEexitServiceImpl")
public class TbEtcVehicleEexitServiceImpl implements TbEtcVehicleEexitService {

    @Autowired
    private TbEtcVehicleEexitMapper mapper;

    @Autowired
    private MerchantManageService merchantManageService;

    @Override
    public ModelAndView exportData(HttpServletRequest request, TbEtcVehicleEexit s) {
        List<TbEtcVehicleEexit> list = mapper.findSearchDataAll(s);
        final Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(),
                ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        list.forEach(c->c.triggerShowState(merchantInfo));
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        Date now = new Date();
        String yyyyMMddHHmmss = DateUtil.format(now, "yyyyMMddHHmmss");
        mv.addObject(NormalExcelConstants.FILE_NAME, "车辆查询-" + yyyyMMddHHmmss);
        mv.addObject(NormalExcelConstants.CLASS, TbEtcVehicleEexit.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("车辆查询", "车辆查询"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "车辆查询模板");
        mv.addObject(NormalExcelConstants.CLASS, TbEtcVehicleEexit.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("车辆查询表", "车辆查询"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    @Override
    public IPage<TbEtcVehicleEexit> findSearchDataPage(Page<TbEtcVehicleEexit> page, TbEtcVehicleEexit entity) {
        IPage<TbEtcVehicleEexit> searchDataPage = mapper.findSearchDataPage(page, entity);
        final Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(),
                ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        searchDataPage.getRecords().forEach(c->c.triggerShowState(merchantInfo));
        return searchDataPage;
    }
}
