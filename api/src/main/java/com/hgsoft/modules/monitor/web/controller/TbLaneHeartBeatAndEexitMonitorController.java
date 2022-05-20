package com.hgsoft.modules.monitor.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.monitor.entity.TbLaneHeartBeatAndEexitMonitor;
import com.hgsoft.modules.monitor.service.TbLaneHeartBeatAndEexitMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "前端软件实时统计")
@RestController("com.hgsoft.modules.monitor.web.controller.TbLaneHeartBeatAndEexitMonitorController")
@RequestMapping("api/v1/monitor/tbLaneHeartBeatAndEexitMonitor")
public class TbLaneHeartBeatAndEexitMonitorController {
    @Autowired
    private UserMerchantPermissionswrapper permissionswrapper;

    @Autowired
    private TbLaneHeartBeatAndEexitMonitorService service;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询前端软件实时查询", notes = "分页查询前端软件实时查询")
    public ResultBean<IPage<TbLaneHeartBeatAndEexitMonitor>> findPage(Page<TbLaneHeartBeatAndEexitMonitor> page,
                                                                      @RequestBody TbLaneHeartBeatAndEexitMonitor vo) {
        return permissionswrapper.permissionsCheck(vo.getMerchantGroupId(),
                vo.getMerchantId(),
                null,
                (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                }, () -> {
                    return service.findSearchDataPage(page, vo);
                });
    }

    @GetMapping("/excelTemplate")
    @ApiOperation(value = "下载前端软件实时查询表模板", notes = "下载前端软件实时查询表模板")
    @RequiresPermissions(value = {"tbEtcVehicleEexit:download"})
    public ModelAndView exportTbBankBillCheckFileTemplate(HttpServletRequest request) {
        return service.exportTemplate(request);
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出前端软件实时查询表数据", notes = "导出前端软件实时查询表数据")
    @RequiresPermissions(value = {"tbEtcVehicleEexit:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody TbLaneHeartBeatAndEexitMonitor vo) {
        return permissionswrapper.permissionsCheck(
                vo.getMerchantGroupId(),
                vo.getMerchantId(),
                null,
                (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                },
                () -> {
                    return service.exportData(request, vo);
                }).getData();
    }
}
