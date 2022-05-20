package com.hgsoft.modules.report.web.controller.shanxi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.report.entity.shanxi.EtcOffLineSettlement;
import com.hgsoft.modules.report.service.shanxi.EtcOffLineSettlementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 山西
 * 2.1.联网中心脱机交易对账报表
 */
@Slf4j
@DS("exp-platform-etc")
@Api(tags = "联网中心脱机对账")
@RestController("com.hgsoft.modules.web.controller.shanxi.EtcOffLineSettlementController")
@RequestMapping("api/v1/sx/EtcOffLineSettlementController")
public class EtcOffLineSettlementController {

    @Autowired
    private UserMerchantPermissionswrapper permissionswrapper;

    @Autowired
    private EtcOffLineSettlementService service;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询联网中心脱机对账报表", notes = "分页查询联网中心脱机对账报表")
    public ResultBean<IPage<EtcOffLineSettlement>> findPage(Page<EtcOffLineSettlement> page, @RequestBody EtcOffLineSettlement vo) {
        return permissionswrapper.permissionsCheck(vo.getMerchantGroupId(),
                vo.getMerchantId(),
                vo.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                    vo.setSiteId(siteId);
                }, () -> {
                    return service.findSearchDataPage(page, vo);
                });
    }

    @GetMapping("/excelTemplate")
    @ApiOperation(value = "下载联网中心脱机对账报表模板", notes = "下载联网中心脱机对账报表模板")
    @RequiresPermissions(value = {"tbEtcVehicleEexit:download"})
    public ModelAndView exportTbBankBillCheckFileTemplate(HttpServletRequest request) {
        return service.exportTemplate(request);
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出联网中心脱机对账报表", notes = "导出联网中心脱机对账报表")
    @RequiresPermissions(value = {"tbEtcVehicleEexit:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody EtcOffLineSettlement vo) {
        return permissionswrapper.permissionsCheck(
                vo.getMerchantGroupId(),
                vo.getMerchantId(),
                vo.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                    vo.setSiteId(siteId);
                },  () -> {
                    return service.exportData(request, vo);
                }).getData();
    }
}
