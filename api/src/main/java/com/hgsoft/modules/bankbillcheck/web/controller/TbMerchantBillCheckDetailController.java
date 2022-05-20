package com.hgsoft.modules.bankbillcheck.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.modules.bankbillcheck.entity.TbMerchantBillCheckDetail;
import com.hgsoft.modules.bankbillcheck.entity.TbMerchantBillCheckDetailExportExcelVo;
import com.hgsoft.modules.bankbillcheck.entity.TbMerchantBillCheckDetailSearchVo;
import com.hgsoft.modules.bankbillcheck.service.TbMerchantBillCheckDetailService;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
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
@Api(tags = "商户对账异常明细")
@RestController("com.hgsoft.modules.bankbillcheck.web.controller.TbMerchantBillCheckDetailController")
@RequestMapping("api/v1/bankbillcheck/TbMerchantBillCheckDetail")
public class TbMerchantBillCheckDetailController {
    @Autowired
    private UserMerchantPermissionswrapper permissionswrapper;

    @Autowired
    private TbMerchantBillCheckDetailService service;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询商户对账核对表", notes = "分页查询银行对账核对表")
    public ResultBean<IPage<TbMerchantBillCheckDetailExportExcelVo>> findPage(Page<TbMerchantBillCheckDetail> page, @RequestBody TbMerchantBillCheckDetailSearchVo vo) {
        return permissionswrapper.permissionsCheck(vo.getMerchantGroupId(),
                vo.getMerchantId(),
                vo.getSiteId(), (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                    vo.setSiteId(siteId);
                }, () -> {
                    return service.findSearchDataPage(page, vo);
                });
    }

    @GetMapping("/excelTemplate")
    @ApiOperation(value = "下载商户对账异常明细核对模板", notes = "下载商户对账异常明细核对模板")
    @RequiresPermissions(value = {"TbMerchantBillCheckDetail:download"})
    public ModelAndView exportTemplate(HttpServletRequest request) {
        return service.exportTemplate(request);
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出下载商户对账异常明细数据", notes = "导出下载商户对账异常明细数据")
    @RequiresPermissions(value = {"TbMerchantBillCheckDetail:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody TbMerchantBillCheckDetailSearchVo vo) {
        return permissionswrapper.permissionsCheck(
                vo.getMerchantGroupId(),
                vo.getMerchantId(),
                vo.getSiteId(), (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                    vo.setSiteId(siteId);
                }, () -> {
                    return service.exportData(request, vo);
                }).getData();
    }
}
