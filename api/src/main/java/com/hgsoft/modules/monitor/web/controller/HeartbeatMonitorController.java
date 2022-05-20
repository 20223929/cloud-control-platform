package com.hgsoft.modules.monitor.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.monitor.entity.HeartbeatMonitor;
import com.hgsoft.modules.monitor.service.HeartbeatMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 车道监控Controller
 * @author 吴鉴武
 * @version 2021-06-01 22:47:13
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "车道监控")
@RestController("com.hgsoft.modules.monitor.web.controller.HeartbeatMonitorController")
@RequestMapping("api/v1/monitor/heartbeatMonitor")
public class HeartbeatMonitorController {

    @Autowired
    private HeartbeatMonitorService heartbeatMonitorService;
    @Autowired
    private UserMerchantPermissionswrapper wrapper;

    /**
     * 分页查询车道监控
     * @param page
     * @param heartbeatMonitor
     * @return
     */
    @PostMapping("/data")
    @ApiOperation(value = "分页查询车道监控", notes = "分页查询车道监控")
    public ResultBean<IPage<HeartbeatMonitor>> findPage(Page<HeartbeatMonitor> page, @RequestBody HeartbeatMonitor heartbeatMonitor){
        return wrapper.permissionsCheck(heartbeatMonitor.getNodeLevel(),heartbeatMonitor.getSearchId(),(param) -> {
            heartbeatMonitor.setUserMerchantParam(param);
            return heartbeatMonitorService.heartbeatMonitorPage(page, heartbeatMonitor);
        });
    }

    /**
     * 导出车道监控数据
     * @param request
     * @param response
     * @param heartbeatMonitor
     * @return
     */
    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出车道监控数据", notes = "导出车道监控数据")
    @RequiresPermissions(value = {"heartbeatMonitor:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody HeartbeatMonitor heartbeatMonitor) {
        return wrapper.permissionsCheck(heartbeatMonitor.getNodeLevel(),heartbeatMonitor.getSearchId(),(param) -> {
            heartbeatMonitor.setUserMerchantParam(param);
            return heartbeatMonitorService.exportData(response, heartbeatMonitor);
        }).getData();
    }
}