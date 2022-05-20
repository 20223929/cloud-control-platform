package com.hgsoft.modules.report.web.controller;

import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.SignStatisticReport;
import com.hgsoft.modules.report.service.SignStatisticReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 二次签约统计报表Controller
 * @author 吴鉴武
 * @version 2021-04-20 22:01:36
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "二次签约统计报表")
@RestController("com.hgsoft.modules.report.web.controller.SignStatisticReportController")
@RequestMapping("api/v1/report/signStatisticReport")
public class SignStatisticReportController {

    @Autowired
    private SignStatisticReportService signStatisticReportService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询二次签约统计报表", notes = "分页查询二次签约统计报表")
    public ResultBean<PageVo<SignStatisticReport>> findPage(PageVo<SignStatisticReport> page, @RequestBody SignStatisticReport signStatisticReport){
        return ResultHandler.ok(signStatisticReportService.signStatisticReportPage(page, signStatisticReport));
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出二次签约统计报表数据", notes = "导出二次签约统计报表数据")
    @RequiresPermissions(value = {"signStatisticReport:export"})
    public void exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody SignStatisticReport signStatisticReport) {
        signStatisticReportService.exportData(request,response, signStatisticReport);
    }
}