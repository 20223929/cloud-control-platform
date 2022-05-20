package com.hgsoft.modules.settlement.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.settlement.entity.SettlementSearchVo;
import com.hgsoft.modules.settlement.entity.TbCenterSettlement;
import com.hgsoft.modules.settlement.service.TbCenterSettlementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 联网中心结算Controller
 *
 * @author 何志豪
 * @version 2021-05-08 04:55:35
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "联网中心结算")
@RestController("com.hgsoft.modules.settlement.web.controller.TbCenterSettlementController")
@RequestMapping("api/v1/settlement/tbCenterSettlement")
public class TbCenterSettlementController {

    @Autowired
    private TbCenterSettlementService tbCenterSettlementService;

    @Autowired
    private UserMerchantPermissionswrapper permissionswrapper;

    @Autowired
    private MerchantManageService merchantManageService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询联网中心结算", notes = "分页查询联网中心结算")
    public ResultBean<IPage<TbCenterSettlement>> findPage(Page<TbCenterSettlement> page, @RequestBody SettlementSearchVo vo) {
        return permissionswrapper.permissionsCheck(vo.getMerchantGroupId(), vo.getMerchantId(), vo.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                    vo.setSiteId(siteId);
                }, () -> {
                    vo.setBeginClearDate(vo.getTimeScope()[0]);
                    vo.setEndClearDate(vo.getTimeScope()[1]);
                    return tbCenterSettlementService.tbCenterSettlementPage(page, vo);
                });
    }

    @PostMapping("/confirm")
    @ApiOperation(value = "首次确认", notes = "用户点击首次确认")
    public ResultBean<String> confirm(@RequestBody TbCenterSettlement e, Model model) {
        return permissionswrapper.permissionsCheck(e.getMerchantGroupId(), e.getMerchantId(), e.getSiteId(), () -> {
            String realName = ShiroSecurityUtil.getPrincipal().getRealName();
            e.setConfirmMan(realName);
            tbCenterSettlementService.confirm(e);
            return "结算确认成功";
        });
    }

    @PostMapping("/batchConfirm")
    @ApiOperation(value = "首次确认", notes = "用户点击首次确认")
    public ResultBean<String> batchConfirm(@RequestBody List<TbCenterSettlement> records, Model model) {

        String userId = ShiroSecurityUtil.getPrincipal().getId();
        Boolean isSuperUser = ShiroSecurityUtil.getPrincipal().getIsSuperUser();
        Map<String, String> allMerchantByUserId = merchantManageService.getAllMerchantByUserId(userId, isSuperUser);
        if (!isSuperUser) {
            for (TbCenterSettlement centerSettlement : records) {
                String merchantGroupId = centerSettlement.getMerchantGroupId();
                String merchantId = centerSettlement.getMerchantId();
                String siteId = centerSettlement.getSiteId();
                if (!allMerchantByUserId.containsKey(merchantGroupId))
                    return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对一级商户" + merchantGroupId + "的处理权限");
                if (!allMerchantByUserId.containsKey(merchantId))
                    return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对二级商户" + merchantId + "的处理权限");
                if (!allMerchantByUserId.containsKey(siteId))
                    return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对三级级商户" + siteId + "的处理权限");
            }
        }
        for (TbCenterSettlement billCheck : records) {
            String realName = ShiroSecurityUtil.getPrincipal().getRealName();
            billCheck.setConfirmMan(realName);
            tbCenterSettlementService.confirm(billCheck);
        }
        return ResultHandler.ok("批量确认成功");
    }

    @GetMapping("/excelTemplate")
    @ApiOperation(value = "下载联网中心结算模板", notes = "下载联网中心结算模板")
    @RequiresPermissions(value = {"tbCenterSettlement:download"})
    public ModelAndView exportTbCenterSettlementFileTemplate(HttpServletRequest request) {
        return tbCenterSettlementService.exportTemplate(request);
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出联网中心结算数据", notes = "导出联网中心结算数据")
    @RequiresPermissions(value = {"tbCenterSettlement:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody SettlementSearchVo vo) {
        return permissionswrapper.permissionsCheck(vo.getMerchantGroupId(), vo.getMerchantId(), vo.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                    vo.setSiteId(siteId);
                }, () -> {
                    vo.setBeginClearDate(vo.getTimeScope()[0]);
                    vo.setEndClearDate(vo.getTimeScope()[1]);
                    return tbCenterSettlementService.exportData(request, vo);
                }).getData();
    }
}