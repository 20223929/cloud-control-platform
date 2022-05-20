package com.hgsoft.modules.report.web.controller;

import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.report.entity.MerchantAccountReport;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.service.TbEtcTransactionEexitService;
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
import java.io.IOException;

/**
 * 商户应收金额报表Controller
 * @author 吴鉴武
 * @date 2021-04-15 03:08:01
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "商户应收金额报表")
@RestController("com.hgsoft.modules.demo.web.controller.TbEtcTransactionEexitController")
@RequestMapping("api/v1/demo/tbEtcTransactionEexit")
public class TbEtcTransactionEexitController {

    @Autowired
    private TbEtcTransactionEexitService tbEtcTransactionEexitService;
    @Autowired
    private UserMerchantPermissionswrapper wrapper;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询商户应收金额报表数据", notes = "分页查询商户应收金额报表数据")
    public ResultBean<PageVo<MerchantAccountReport>> findPage(PageVo<MerchantAccountReport> page, @RequestBody MerchantAccountReport queryParam){
        return wrapper.permissionsCheck(queryParam.getNodeLevel(),queryParam.getMerchantId(),(param)->{
            queryParam.setUserMerchantParam(param);
            return tbEtcTransactionEexitService.tbEtcTransactionEexitPage(page, queryParam);
        });
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出商户应收金额报表数据", notes = "导出商户应收金额报表数据")
    @RequiresPermissions(value = {"tbEtcTransactionEexit:export"})
    public void exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody MerchantAccountReport report) throws IOException {
        wrapper.permissionsCheck(report.getNodeLevel(),report.getMerchantId(),(param)->{
            report.setUserMerchantParam(param);
            tbEtcTransactionEexitService.exportData(request,response, report);
            return null;
        });
    }
}