package com.hgsoft.modules.report.web.controller.shanxi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.shanxi.EtcPayReport;
import com.hgsoft.modules.report.service.shanxi.EtcPayReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 联网中心扣款报表Controller
 * @author 吴鉴武
 * @version 2021-04-22 23:14:25
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "联网中心扣款报表")
@RestController("com.hgsoft.modules.report.web.controller.EtcPayReportController")
@RequestMapping("api/v1/report/etcPayReport")
public class EtcPayReportController {

    @Autowired
    private UserMerchantPermissionswrapper wrapper;
    @Autowired
    private EtcPayReportService etcPayReportService;

    /**
     * 分页查询联网中心扣款报表
     * @param page
     * @param etcPayReport
     * @return
     */
    @PostMapping("/data")
    @ApiOperation(value = "分页查询联网中心扣款报表", notes = "分页查询联网中心扣款报表")
    public ResultBean<IPage<EtcPayReport>> findPage(PageVo<EtcPayReport> page, @RequestBody EtcPayReport etcPayReport){
        return wrapper.permissionsCheck(etcPayReport.getNodeLevel(),etcPayReport.getSearchId(),(param) -> {
            etcPayReport.setUserMerchantParam(param);
            return etcPayReportService.findPage(page,etcPayReport);
        });
    }

    /**
     * 导出联网中心扣款报表
     * @param etcPayReport
     * @param response
     */
    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出联网中心扣款报表", notes = "导出联网中心扣款报表")
    public void exportExcel(@RequestBody EtcPayReport etcPayReport,HttpServletResponse response){
        wrapper.permissionsCheck(etcPayReport.getNodeLevel(),etcPayReport.getSearchId(),(param) -> {
            etcPayReport.setUserMerchantParam(param);
            etcPayReportService.exportExcel(response,etcPayReport);
            return null;
        });
    }
}