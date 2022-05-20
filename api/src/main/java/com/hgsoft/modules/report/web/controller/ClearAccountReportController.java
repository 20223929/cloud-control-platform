package com.hgsoft.modules.report.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.report.entity.ClearAccountReport;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.service.ClearAccountReportService;
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
 * 省中心清分对账报表Controller
 * @author 吴鉴武
 * @date 2021-04-19 23:29:04
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "省中心清分对账报表")
@RestController("com.hgsoft.modules.merchant.web.controller.ClearAccountReportController")
@RequestMapping("api/v1/merchant/clearAccountReport")
public class ClearAccountReportController {

    @Autowired
    private ClearAccountReportService clearAccountReportService;
    @Autowired
    private UserMerchantPermissionswrapper wrapper;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询省中心清分对账报表", notes = "分页查询省中心清分对账报表")
    public ResultBean<IPage<ClearAccountReport>> findPage(PageVo<ClearAccountReport> page, @RequestBody ClearAccountReport clearAccountReport){
        return wrapper.permissionsCheck(clearAccountReport.getNodeLevel(),clearAccountReport.getMerchantId(),(param)->{
            clearAccountReport.setUserMerchantParam(param);
            return clearAccountReportService.clearAccountReportPage(page, clearAccountReport);
        });
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出省中心清分对账报表数据", notes = "导出省中心清分对账报表数据")
    @RequiresPermissions(value = {"clearAccountReport:export"})
    public void exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody ClearAccountReport clearAccountReport) {
        wrapper.permissionsCheck(clearAccountReport.getNodeLevel(),clearAccountReport.getMerchantId(),(param)-> {
            clearAccountReport.setUserMerchantParam(param);
            clearAccountReportService.exportData(request, response, clearAccountReport);
            return null;
        });
    }
}