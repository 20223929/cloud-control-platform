package com.hgsoft.modules.bankbillcheck.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheckDetail;
import com.hgsoft.modules.bankbillcheck.service.TbBankBillCheckDetailService;
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
import java.util.List;

/**
 * 银行对账明细表Controller
 *
 * @author 何志豪
 * @version 2021-04-19 03:07:10
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "银行对账明细表")
@RestController("com.hgsoft.modules.bankbillcheck.web.controller.TbBankBillCheckDetailController")
@RequestMapping("api/v1/bankbillcheck/tbBankBillCheckDetail")
public class TbBankBillCheckDetailController {

    @Autowired
    private TbBankBillCheckDetailService tbBankBillCheckDetailService;

    @Autowired
    private UserMerchantPermissionswrapper permissionswrapper;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询银行对账明细表", notes = "分页查询银行对账明细表")
    public ResultBean<IPage<TbBankBillCheckDetail>> findPage(Page<TbBankBillCheckDetail> page, @RequestBody TbBankBillCheckDetail detail) {
        return permissionswrapper.permissionsCheck(detail.getMerchantGroupId(), detail.getMerchantId(), detail.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
                    detail.setMerchantGroupId(merchantGroupId);
                    detail.setMerchantId(merchantId);
                    detail.setSiteId(siteId);
                }, () -> {
                    return tbBankBillCheckDetailService.tbBankBillCheckDetailPage(page, detail);
                }
        );
    }

    /**
     * 查询list 集合
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询银行对账明细表集合", notes = "查询银行对账明细表集合")
    public ResultBean<List<TbBankBillCheckDetail>> list(@RequestBody TbBankBillCheckDetail detail) {
        return permissionswrapper.permissionsCheck(detail.getMerchantGroupId(), detail.getMerchantId(), detail.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
                    detail.setMerchantGroupId(merchantGroupId);
                    detail.setMerchantId(merchantId);
                    detail.setSiteId(siteId);
                }, () -> {
                    return tbBankBillCheckDetailService.findList(detail);
                });
    }

    /**
     * 根据主键查询数据
     */
    @GetMapping("/{transDate}/{merchantGroupId}/{merchantId}/{siteId}/{equipmentId}")
    @ApiOperation(value = "根据主键查询银行对账明细表数据", notes = "根据主键查询银行对账明细表数据")
    public ResultBean<TbBankBillCheckDetail> findByPrimaryKey(@PathVariable String transDate, @PathVariable String merchantGroupId, @PathVariable String merchantId, @PathVariable String siteId, @PathVariable String equipmentId) {
        return permissionswrapper.permissionsCheck(merchantGroupId, merchantId, siteId, () -> {
            return tbBankBillCheckDetailService.getByPrimaryKey(transDate, merchantGroupId, merchantId, siteId, equipmentId);
        });
    }

    @GetMapping("/excelTemplate")
    @ApiOperation(value = "下载银行对账明细表模板", notes = "下载银行对账明细表模板")
    @RequiresPermissions(value = {"tbBankBillCheckDetail:download"})
    public ModelAndView exportTbBankBillCheckDetailFileTemplate(HttpServletRequest request) {
        return tbBankBillCheckDetailService.exportTemplate(request);
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出银行对账明细表数据", notes = "导出银行对账明细表数据")
    @RequiresPermissions(value = {"detail:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody TbBankBillCheckDetail detail) {
        return permissionswrapper.permissionsCheck(detail.getMerchantGroupId(), detail.getMerchantId(), detail.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
                    detail.setMerchantGroupId(merchantGroupId);
                    detail.setMerchantId(merchantId);
                    detail.setSiteId(siteId);
                }, () -> {
                    return tbBankBillCheckDetailService.exportData(request, detail);
                }).getData();
    }
}