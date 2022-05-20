package com.hgsoft.modules.bankbillcheck.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.bankbillcheck.entity.TbCenterBillCheck;
import com.hgsoft.modules.bankbillcheck.entity.TbCenterBillCheckSearchVo;
import com.hgsoft.modules.bankbillcheck.service.TbCenterBillCheckService;
import com.hgsoft.modules.merchantcommon.MerchantManageService;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 联网中心对账核对表Controller
 *
 * @author 何志豪
 * @version 2021-04-21 02:30:17
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "联网中心对账核对表")
@RestController("com.hgsoft.modules.bankbillcheck.web.controller.TbCenterBillCheckController")
@RequestMapping("api/v1/bankbillcheck/tbCenterBillCheck")
public class TbCenterBillCheckController {

    @Autowired
    private TbCenterBillCheckService tbCenterBillCheckService;

    @Autowired
    private UserMerchantPermissionswrapper permissionswrapper;

    @Autowired
    private MerchantManageService merchantManageService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询联网中心对账核对", notes = "分页查询联网中心对账核对")
    public ResultBean<IPage<TbCenterBillCheck>> findPage(Page<TbCenterBillCheck> page, @RequestBody TbCenterBillCheckSearchVo check) {
        return permissionswrapper.permissionsCheck(check.getMerchantGroupId(), check.getMerchantId(), check.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
            check.setMerchantGroupId(merchantGroupId);
            check.setMerchantId(merchantId);
            check.setSiteId(siteId);
        }, () -> {
            return tbCenterBillCheckService.findSearchDataPage(page, check);
        });
    }

    /**
     * 查询list 集合
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询联网中心对账核对集合", notes = "查询联网中心对账核对集合")
    public ResultBean<List<TbCenterBillCheck>> list(@RequestBody TbCenterBillCheckSearchVo check) {
        return permissionswrapper.permissionsCheck(check.getMerchantGroupId(),
                check.getMerchantId(),
                check.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
                    check.setMerchantGroupId(merchantGroupId);
                    check.setMerchantId(merchantId);
                    check.setSiteId(siteId);
                },
                () -> {
                    return tbCenterBillCheckService.findSearchDataAll(check);
                });
    }

    /**
     * 根据主键查询数据
     */
    @GetMapping("/{transDate}/{merchantGroupId}/{merchantId}/{siteId}")
    @ApiOperation(value = "根据主键查询联网中心对账核对数据", notes = "根据主键查询联网中心对账核对数据")
    public ResultBean<TbCenterBillCheck> findByPrimaryKey(@PathVariable String transDate, @PathVariable String merchantGroupId, @PathVariable String merchantId, @PathVariable String siteId) {
        return permissionswrapper.permissionsCheck(merchantGroupId, merchantId, siteId, () -> {
            return tbCenterBillCheckService.getByPrimaryKey(transDate, merchantGroupId, merchantId, siteId);
        });
    }

    @PostMapping("/confirm")
    @ApiOperation(value = "首次确认", notes = "用户点击首次确认")
    public ResultBean<String> confirm(@RequestBody TbCenterBillCheckSearchVo billCheck, Model model) {
        return permissionswrapper.permissionsCheck(billCheck.getMerchantGroupId(), billCheck.getMerchantId(), billCheck.getSiteId(), () -> {
            String realName = ShiroSecurityUtil.getPrincipal().getRealName();
            billCheck.setConfirmMan(realName);
            tbCenterBillCheckService.confirm(billCheck);
            return "首次确认成功";
        });

    }

    @PostMapping("/batchConfirm")
    @ApiOperation(value = "批量首次确认", notes = "用户点击批量首次确认")
    public ResultBean<String> batchConfirm(@RequestBody List<TbCenterBillCheckSearchVo> billChecks) {
        String userId = ShiroSecurityUtil.getPrincipal().getId();
        Boolean isSuperUser = ShiroSecurityUtil.getPrincipal().getIsSuperUser();
        Map<String, String> allMerchantByUserId = merchantManageService.getAllMerchantByUserId(userId, isSuperUser);
        if (!isSuperUser) {
            for (TbCenterBillCheckSearchVo billCheck : billChecks) {
                String merchantGroupId = billCheck.getMerchantGroupId();
                String merchantId = billCheck.getMerchantId();
                String siteId = billCheck.getSiteId();
                if (!allMerchantByUserId.containsKey(merchantGroupId))
                    return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对一级商户" + merchantGroupId + "的处理权限");
                if (!allMerchantByUserId.containsKey(merchantId))
                    return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对二级商户" + merchantId + "的处理权限");
                if (!allMerchantByUserId.containsKey(siteId))
                    return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对三级级商户" + siteId + "的处理权限");
            }
        }
        for (TbCenterBillCheckSearchVo billCheck : billChecks) {
            String realName = ShiroSecurityUtil.getPrincipal().getRealName();
            billCheck.setConfirmMan(realName);
            tbCenterBillCheckService.confirm(billCheck);
        }
        return ResultHandler.ok("批量确认成功");
    }

    @PostMapping("/batchSecondConfirm")
    @ApiOperation(value = "批量补确认", notes = "用户点击批量补确认")
    public ResultBean<String> batchSecondConfirm(@RequestBody List<TbCenterBillCheckSearchVo> billChecks) {
        String userId = ShiroSecurityUtil.getPrincipal().getId();
        Boolean isSuperUser = ShiroSecurityUtil.getPrincipal().getIsSuperUser();
        Map<String, String> allMerchantByUserId = merchantManageService.getAllMerchantByUserId(userId, isSuperUser);
        if (!isSuperUser) {
            for (TbCenterBillCheckSearchVo billCheck : billChecks) {
                String merchantGroupId = billCheck.getMerchantGroupId();
                String merchantId = billCheck.getMerchantId();
                String siteId = billCheck.getSiteId();
                if (!allMerchantByUserId.containsKey(merchantGroupId))
                    return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对一级商户" + merchantGroupId + "的处理权限");
                if (!allMerchantByUserId.containsKey(merchantId))
                    return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对二级商户" + merchantId + "的处理权限");
                if (!allMerchantByUserId.containsKey(siteId))
                    return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对三级级商户" + siteId + "的处理权限");
            }
        }
        for (TbCenterBillCheckSearchVo billCheck : billChecks) {
            String realName = ShiroSecurityUtil.getPrincipal().getRealName();
            billCheck.setSecondConfirmMan(realName);
            tbCenterBillCheckService.secondConfirm(billCheck);
        }
        return ResultHandler.ok("批量补确认成功");
    }

    @PostMapping("/secondConfirm")
    @ApiOperation(value = "二次确认", notes = "用户点击补确认")
    public ResultBean<String> secondConfirm(@RequestBody TbCenterBillCheckSearchVo billCheck, Model model) {
        return permissionswrapper.permissionsCheck(billCheck.getMerchantGroupId(), billCheck.getMerchantId(), billCheck.getSiteId(), () -> {
            String realName = ShiroSecurityUtil.getPrincipal().getRealName();
            billCheck.setSecondConfirmMan(realName);
            tbCenterBillCheckService.secondConfirm(billCheck);
            return "首次确认成功";
        });
    }

    @GetMapping("/excelTemplate")
    @ApiOperation(value = "下载联网中心对账核对模板", notes = "下载联网中心对账核对模板")
    @RequiresPermissions(value = {"tbCenterBillCheck:download"})
    public ModelAndView exportTbCenterBillCheckFileTemplate(HttpServletRequest request) {
        return tbCenterBillCheckService.exportTemplate(request);
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出联网中心对账核对数据", notes = "导出联网中心对账核对数据")
    @RequiresPermissions(value = {"tbCenterBillCheck:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody TbCenterBillCheckSearchVo check) {
        return permissionswrapper.permissionsCheck(check.getMerchantGroupId(),
                check.getMerchantId(),
                check.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
                    check.setMerchantGroupId(merchantGroupId);
                    check.setMerchantId(merchantId);
                    check.setSiteId(siteId);
                },
                () -> {
                    return tbCenterBillCheckService.exportData(request, check);
                }).getData();
    }

    @GetMapping("/excelTemplateEexit")
    @ApiOperation(value = "下载联网中心对账核对模板", notes = "下载联网中心对账核对模板")
    @RequiresPermissions(value = {"tbCenterBillCheck:download"})
    public ModelAndView exportTbEtcTransactionEexitFileTemplate(HttpServletRequest request) {
        return tbCenterBillCheckService.exportTemplate(request);
    }

    @PostMapping("/exportExcelEexit")
    @ApiOperation(value = "导出联网中心对账核对数据", notes = "导出联网中心对账核对数据")
    @RequiresPermissions(value = {"tbCenterBillCheck:export"})
    public ModelAndView exportTbEtcTransactionEexitData(HttpServletRequest request, HttpServletResponse response, @RequestBody TbCenterBillCheckSearchVo vo) {

        if (!permissionswrapper.permissionsCheck(vo.getMerchantGroupId(), vo.getMerchantId(), vo.getSiteId())) {
            ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
            mv.addObject("message", "用户不含导出"
                    + vo.getMerchantGroupId()
                    + "," + vo.getMerchantId()
                    + "," + vo.getSiteId() + "的权限");
            return mv;
        }

        Long detailCount = tbCenterBillCheckService.countEexit(vo);
        if (detailCount >= 20000) {
            ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
            mv.addObject("message", "导出明细记录超过2万条，无法导出");
            return mv;
        }
        return tbCenterBillCheckService.exportEexitDetailData(request, vo);
    }
}