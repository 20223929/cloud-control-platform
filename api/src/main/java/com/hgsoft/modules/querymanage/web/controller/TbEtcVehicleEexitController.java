package com.hgsoft.modules.querymanage.web.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.querymanage.entity.TbEtcVehicleEexit;
import com.hgsoft.modules.querymanage.service.TbEtcVehicleEexitService;
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
@Api(tags = "车辆信息查询")
@RestController("com.hgsoft.modules.querymanage.web.controller.TbEtcVehicleEexitController")
@RequestMapping("api/v1/querymanage/tbEtcVehicleEexit")
public class TbEtcVehicleEexitController {
    @Autowired
    private UserMerchantPermissionswrapper permissionswrapper;

    @Autowired
    private TbEtcVehicleEexitService service;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询车辆信息", notes = "分页查询车辆信息")
    public ResultBean<IPage<TbEtcVehicleEexit>> findPage(Page<TbEtcVehicleEexit> page, @RequestBody TbEtcVehicleEexit vo) {
        return permissionswrapper.permissionsCheck(vo.getMerchantGroupId(),
                vo.getMerchantId(),
                vo.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                    vo.setSiteId(siteId);
                }, () -> {
                    vo.setBeginSearchDate(DateUtil.parse(vo.getTimeScope()[0]));
                    vo.setEndSearchDate(DateUtil.parse(vo.getTimeScope()[1]));
                    return service.findSearchDataPage(page, vo);
                });
    }

    @GetMapping("/excelTemplate")
    @ApiOperation(value = "下载车辆查询表模板", notes = "下载车辆查询表模板")
    @RequiresPermissions(value = {"tbEtcVehicleEexit:download"})
    public ModelAndView exportTbBankBillCheckFileTemplate(HttpServletRequest request) {
        return service.exportTemplate(request);
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出车辆查询表数据", notes = "导出车辆查询表数据")
    @RequiresPermissions(value = {"tbEtcVehicleEexit:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody TbEtcVehicleEexit vo) {
        return permissionswrapper.permissionsCheck(
                vo.getMerchantGroupId(),
                vo.getMerchantId(),
                vo.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                    vo.setSiteId(siteId);
                },  () -> {
                    vo.setBeginSearchDate(DateUtil.parse(vo.getTimeScope()[0]));
                    vo.setEndSearchDate(DateUtil.parse(vo.getTimeScope()[1]));
                    return service.exportData(request, vo);
                }).getData();
    }
}
