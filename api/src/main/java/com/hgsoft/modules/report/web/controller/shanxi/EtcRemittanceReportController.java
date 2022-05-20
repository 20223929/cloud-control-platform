package com.hgsoft.modules.report.web.controller.shanxi;

import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.shanxi.EtcRemittanceReport;
import com.hgsoft.modules.report.service.shanxi.EtcRemittanceReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * 联网中心汇款报表Controller
 * @author 吴鉴武
 * @version 2021-04-23 01:46:07
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "联网中心汇款报表")
@RestController("com.hgsoft.modules.report.web.controller.EtcRemittanceReportController")
@RequestMapping("api/v1/report/etcRemittanceReport")
public class EtcRemittanceReportController {

    @Autowired
    private EtcRemittanceReportService etcRemittanceReportService;
    @Autowired
    private UserMerchantPermissionswrapper wrapper;

    /**
     * 分页查询联网中心汇款报表
     * @param page
     * @param etcRemittanceReport
     * @return
     */
    @PostMapping("/data")
    @ApiOperation(value = "分页查询联网中心汇款报表", notes = "分页查询联网中心汇款报表")
    public ResultBean<PageVo<EtcRemittanceReport>> findPage(PageVo<EtcRemittanceReport> page, @RequestBody EtcRemittanceReport etcRemittanceReport){
        return wrapper.permissionsCheck(etcRemittanceReport.getNodeLevel(),etcRemittanceReport.getSearchId(),(param) -> {
            etcRemittanceReport.setUserMerchantParam(param);
            return etcRemittanceReportService.findPage(page,etcRemittanceReport);
        });
    }

    /**
     * 导出联网中心汇款报表
     * @param response
     * @param etcRemittanceReport
     */
    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出联网中心汇款报表", notes = "导出联网中心汇款报表")
    public void exportExcel(HttpServletResponse response,@RequestBody EtcRemittanceReport etcRemittanceReport){
        wrapper.permissionsCheck(etcRemittanceReport.getNodeLevel(),etcRemittanceReport.getSearchId(),(param) -> {
            etcRemittanceReport.setUserMerchantParam(param);
            etcRemittanceReportService.exportExcel(response,etcRemittanceReport);
            return null;
        });
    }
}