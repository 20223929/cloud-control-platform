package com.hgsoft.modules.bankbillcheck.web.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.bankbillcheck.entity.TBMerchantBillCheckExpPlatformEtc;
import com.hgsoft.modules.bankbillcheck.entity.TBMerchantBillCheckExpPlatformEtcExportExcelVo;
import com.hgsoft.modules.bankbillcheck.entity.TBMerchantBillCheckExpPlatformEtcSearchVo;
import com.hgsoft.modules.bankbillcheck.service.TBMerchantBillCheckExpPlatformEtcService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "商户对账菜单")
@RestController("com.hgsoft.modules.bankbillcheck.web.controller.TBMerchantBillCheckExpPlatformEtcController")
@RequestMapping("api/v1/bankbillcheck/tbMerchantBillCheckExpPlatformEtc")
public class TBMerchantBillCheckExpPlatformEtcController {
    @Autowired
    private TBMerchantBillCheckExpPlatformEtcService service;

    @Autowired
    private UserMerchantPermissionswrapper permissionswrapper;

    @Autowired
    private MerchantManageService merchantManageService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询商户对账核对表", notes = "分页查询银行对账核对表")
    public ResultBean<IPage<TBMerchantBillCheckExpPlatformEtcExportExcelVo>> findPage(Page<TBMerchantBillCheckExpPlatformEtc> page, @RequestBody TBMerchantBillCheckExpPlatformEtcSearchVo vo) {
        return permissionswrapper.permissionsCheck(vo.getMerchantGroupId(),
                vo.getMerchantId(),
                vo.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                    vo.setSiteId(siteId);
                },
                () -> {
                    vo.setBeginTransDate(vo.getTimeScope()[0]);
                    vo.setEndTransDate(vo.getTimeScope()[1]);
                    if(StrUtil.isNotBlank(vo.getConfirmState())){
                        vo.setConfirmStateList(Stream.of(vo.getConfirmState().split(StrUtil.COMMA)).map(Integer::parseInt).collect(Collectors.toList()));
                    }
                    return service.findSearchDataPage(page, vo);
                });
    }

    @GetMapping("/excelTemplate")
    @ApiOperation(value = "下载商户对账核对表模板", notes = "下载商户对账核对表模板")
    @RequiresPermissions(value = {"TBMerchantBillCheckExpPlatformEtc:download"})
    public ModelAndView exportTbBankBillCheckFileTemplate(HttpServletRequest request) {
        return service.exportTemplate(request);
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出商户对账核对表数据", notes = "导出商户对账核对表数据")
    @RequiresPermissions(value = {"TBMerchantBillCheckExpPlatformEtc:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody TBMerchantBillCheckExpPlatformEtcSearchVo vo) {
        return permissionswrapper.permissionsCheck(
                vo.getMerchantGroupId(),
                vo.getMerchantId(),
                vo.getSiteId(),
                (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                    vo.setSiteId(siteId);
                },
                () -> {
                    vo.setBeginTransDate(vo.getTimeScope()[0]);
                    vo.setEndTransDate(vo.getTimeScope()[1]);
                    if(StrUtil.isNotBlank(vo.getConfirmState())){
                        vo.setConfirmStateList(Stream.of(vo.getConfirmState().split(StrUtil.COMMA)).map(Integer::parseInt).collect(Collectors.toList()));
                    }
                    return service.exportData(request, vo);
                }).getData();
    }

    @PostMapping("/errorRegister")
    @ApiOperation(value = "异常登记", notes = "触发异常登记")
    public ResultBean<String> errorRegister(@RequestBody TBMerchantBillCheckExpPlatformEtc vo, Model model) {
        return permissionswrapper.permissionsCheck(vo.getMerchantGroupId(), vo.getMerchantId(), vo.getSiteId(), () -> {
            String realName = ShiroSecurityUtil.getPrincipal().getRealName();
            vo.setConfirmMan(realName);
            return service.errorRegister(vo);
        });
    }

    @PostMapping("/confirmRegister")
    @ApiOperation(value = "确认登记", notes = "触发确认登记")
    public ResultBean<String> confirmRegister(@RequestBody TBMerchantBillCheckExpPlatformEtc vo, Model model) {
        return permissionswrapper.permissionsCheck(vo.getMerchantGroupId(), vo.getMerchantId(), vo.getSiteId(), () -> {
            String realName = ShiroSecurityUtil.getPrincipal().getRealName();
            vo.setConfirmMan(realName);
            return service.confirmRegister(vo);
        });
    }

    @PostMapping("/batchConfirmRegister")
    @ApiOperation(value = "确认登记", notes = "触发确认登记")
    public ResultBean<String> batchConfirmRegister(@RequestBody List<TBMerchantBillCheckExpPlatformEtc> vos, Model model) {
        String userId = ShiroSecurityUtil.getPrincipal().getId();
        Boolean isSuperUser = ShiroSecurityUtil.getPrincipal().getIsSuperUser();
        Map<String, String> allMerchantByUserId = merchantManageService.getAllMerchantByUserId(userId, isSuperUser);
        if (!isSuperUser) {
            for (TBMerchantBillCheckExpPlatformEtc vo : vos) {
                String merchantGroupId = vo.getMerchantGroupId();
                String merchantId = vo.getMerchantId();
                String siteId = vo.getSiteId();
                if (!allMerchantByUserId.containsKey(merchantGroupId))
                    return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对一级商户" + merchantGroupId + "的处理权限");
                if (!allMerchantByUserId.containsKey(merchantId))
                    return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对二级商户" + merchantId + "的处理权限");
                if (!allMerchantByUserId.containsKey(siteId))
                    return ResultHandler.error("用户" + ShiroSecurityUtil.getPrincipal().getRealName() + "不含对三级级商户" + siteId + "的处理权限");
            }
        }
        for (TBMerchantBillCheckExpPlatformEtc vo : vos) {
            String realName = ShiroSecurityUtil.getPrincipal().getRealName();
            vo.setConfirmMan(realName);
            service.confirmRegister(vo);
        }
        return ResultHandler.ok("批量确认成功");
    }


}
