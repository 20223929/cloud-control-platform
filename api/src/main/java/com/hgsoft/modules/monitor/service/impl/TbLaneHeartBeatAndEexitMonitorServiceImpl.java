package com.hgsoft.modules.monitor.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.ecip.auto.poi.def.NormalExcelConstants;
import com.hgsoft.ecip.auto.poi.excel.entity.ExportParams;
import com.hgsoft.ecip.auto.poi.view.EcipEntityExcelView;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.monitor.entity.TbLaneHeartBeatAndEexitMonitor;
import com.hgsoft.modules.monitor.mapper.TBLaneHeartBeatAndEexitMonitorMapper;
import com.hgsoft.modules.monitor.service.TbLaneHeartBeatAndEexitMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("com.hgsoft.modules.monitor.service.impl.TbLaneHeartBeatAndEexitMonitorServiceImpl")
public class TbLaneHeartBeatAndEexitMonitorServiceImpl implements TbLaneHeartBeatAndEexitMonitorService {

    @Autowired
    private MerchantManageService merchantManageService;

    @Autowired
    private TBLaneHeartBeatAndEexitMonitorMapper mapper;

    @Override
    public ModelAndView exportData(HttpServletRequest request, TbLaneHeartBeatAndEexitMonitor s) {
        List<TbLaneHeartBeatAndEexitMonitor> list = mapper.findSearchDataAll(s);
        final Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(),
                ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        list.forEach(c->c.triggerShowState(merchantInfo));
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        Date now = new Date();
        String yyyyMMddHHmmss = DateUtil.format(now, "yyyyMMddHHmmss");
        mv.addObject(NormalExcelConstants.FILE_NAME, "前端软件实时统计-" + yyyyMMddHHmmss);
        mv.addObject(NormalExcelConstants.CLASS, TbLaneHeartBeatAndEexitMonitor.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("前端软件实时统计", "前端软件实时统计"));
        mv.addObject(NormalExcelConstants.DATA_LIST, list);
        return mv;
    }

    @Override
    public ModelAndView exportTemplate(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView(new EcipEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "前端软件实时统计");
        mv.addObject(NormalExcelConstants.CLASS, TbLaneHeartBeatAndEexitMonitor.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("前端软件实时统计", "前端软件实时统计"));
        mv.addObject(NormalExcelConstants.DATA_LIST, new ArrayList<>());
        return mv;
    }

    @Override
    public IPage<TbLaneHeartBeatAndEexitMonitor> findSearchDataPage(Page<TbLaneHeartBeatAndEexitMonitor> page, TbLaneHeartBeatAndEexitMonitor entity) {
        IPage<TbLaneHeartBeatAndEexitMonitor> searchDataPage = mapper.findSearchDataPage(page, entity);
        final Map<String, String> merchantInfo = merchantManageService.getAllMerchantByUserId(ShiroSecurityUtil.getPrincipal().getId(),
                ShiroSecurityUtil.getPrincipal().getIsSuperUser());
        searchDataPage.getRecords().forEach(c->c.triggerShowState(merchantInfo));
        return searchDataPage;
    }
}
