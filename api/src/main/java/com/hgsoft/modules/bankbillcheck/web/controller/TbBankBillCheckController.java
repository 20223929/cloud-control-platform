package com.hgsoft.modules.bankbillcheck.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.annotation.AopLog;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.ecip.framework.shiro.ShiroSecurityUtil;
import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheck;
import com.hgsoft.modules.bankbillcheck.entity.TbBankBillCheckSearchVo;
import com.hgsoft.modules.bankbillcheck.service.TbBankBillCheckService;
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
 * 银行对账核对表Controller
 *
 * @author 何志豪
 * @version 2021-04-18 23:35:35
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "银行对账核对表")
@RestController("com.hgsoft.modules.bankbillcheck.web.controller.TbBankBillCheckController")
@RequestMapping("api/v1/bankbillcheck/tbBankBillCheck")
public class TbBankBillCheckController {

    @Autowired
    private TbBankBillCheckService tbBankBillCheckService;

    @Autowired
    private UserMerchantPermissionswrapper permissionswrapper;

    @Autowired
    private MerchantManageService merchantManageService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询银行对账核对表", notes = "分页查询银行对账核对表")
    public ResultBean<IPage<TbBankBillCheck>> findPage(Page<TbBankBillCheck> page, @RequestBody TbBankBillCheckSearchVo vo) {
        return permissionswrapper.permissionsCheck(vo.getMerchantGroupId(),
                vo.getMerchantId(),
                vo.getSiteId(), (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                    vo.setSiteId(siteId);
                }, () -> {
                    return tbBankBillCheckService.findSearchDataPage(page, vo);
                });
    }


    /**
     * 根据主键查询数据
     */
    @GetMapping("/{transDate}/{merchantGroupId}/{merchantId}/{siteId}")
    @ApiOperation(value = "根据主键查询银行对账核对表数据", notes = "根据主键查询银行对账核对表数据")
    public ResultBean<TbBankBillCheck> findByPrimaryKey(@PathVariable String transDate, @PathVariable String merchantGroupId, @PathVariable String merchantId, @PathVariable String siteId) {
        return ResultHandler.ok(tbBankBillCheckService.getByPrimaryKey(transDate, merchantGroupId, merchantId, siteId));
    }

    /**
     * 根据id更新数据
     */
    @PutMapping("/{transDate}/{merchantGroupId}/{merchantId}/{siteId}")
    @ApiOperation(value = "更新银行对账核对表数据", notes = "更新银行对账核对表数据")
    @RequiresPermissions(value = {"tbBankBillCheck:edit"})
    @AopLog(module = "tb_bank_bill_check", optType = "edit", description = "更新银行对账核对表")
    public ResultBean<String> updateTbBankBillCheck(@PathVariable String transDate, @PathVariable String merchantGroupId, @PathVariable String merchantId, @PathVariable String siteId, @RequestBody TbBankBillCheck tbBankBillCheck, Model model) {
        return permissionswrapper.permissionsCheck(merchantGroupId, merchantId, siteId, () -> {
            tbBankBillCheck.setTransDate(transDate);
            tbBankBillCheck.setMerchantGroupId(merchantGroupId);
            tbBankBillCheck.setMerchantId(merchantId);
            tbBankBillCheck.setSiteId(siteId);
            tbBankBillCheck.setConfirmMan(ShiroSecurityUtil.getPrincipal().getRealName());
            tbBankBillCheck.setConfirmState("1");//已经确认
            this.tbBankBillCheckService.confirmCheckSumAndDetail(tbBankBillCheck);
            return "确认完成";
        });

    }

    /**
     * 批量确认
     *
     * @param
     * @return
     */
    @PostMapping("/batchConfirm")
    @ApiOperation(value = "批量首次确认", notes = "用户点击批量首次确认")
    public ResultBean<String> batchConfirm(@RequestBody List<TbBankBillCheck> billChecks) {
        String userId = ShiroSecurityUtil.getPrincipal().getId();
        Boolean isSuperUser = ShiroSecurityUtil.getPrincipal().getIsSuperUser();
        Map<String, String> allMerchantByUserId = merchantManageService.getAllMerchantByUserId(userId, isSuperUser);
        if (!isSuperUser) {
            for (TbBankBillCheck billCheck : billChecks) {
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
        for (TbBankBillCheck billCheck : billChecks) {
            String realName = ShiroSecurityUtil.getPrincipal().getRealName();
            billCheck.setConfirmMan(realName);
            billCheck.setConfirmState("1");//已经确认
            tbBankBillCheckService.confirmCheckSumAndDetail(billCheck);
        }
        return ResultHandler.ok("批量确认成功");
    }

    @GetMapping("/excelTemplate")
    @ApiOperation(value = "下载银行对账核对表模板", notes = "下载银行对账核对表模板")
    @RequiresPermissions(value = {"tbBankBillCheck:download"})
    public ModelAndView exportTbBankBillCheckFileTemplate(HttpServletRequest request) {
        return tbBankBillCheckService.exportTemplate(request);
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出银行对账核对表数据", notes = "导出银行对账核对表数据")
    @RequiresPermissions(value = {"tbBankBillCheck:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody TbBankBillCheckSearchVo vo) {
        return permissionswrapper.permissionsCheck(
                vo.getMerchantGroupId(),
                vo.getMerchantId(),
                vo.getSiteId(), (merchantGroupId, merchantId, siteId) -> {
                    vo.setMerchantGroupId(merchantGroupId);
                    vo.setMerchantId(merchantId);
                    vo.setSiteId(siteId);
                }, () -> {
                    return tbBankBillCheckService.exportData(request, vo);
                }).getData();
    }

    @GetMapping("/excelEexitTemplate")
    @ApiOperation(value = "下载银行对账核对表模板", notes = "下载银行对账核对表模板")
    @RequiresPermissions(value = {"tbBankBillCheck:download"})
    public ModelAndView exportEexitTemplate(HttpServletRequest request) {
        return tbBankBillCheckService.exportEexitTemplate(request);
    }

    @PostMapping("/exportEexitExcel") // TODO 这种写法不一定行，主要是返回结果，我先试一下写内部的
    @ApiOperation(value = "导出银行对账核对表数据", notes = "导出银行对账核对表数据")
    @RequiresPermissions(value = {"tbBankBillCheck:export"})
    public ModelAndView exportEexitData(HttpServletRequest request, HttpServletResponse response, @RequestBody TbBankBillCheckSearchVo vo) {

        if (!permissionswrapper.permissionsCheck(vo.getMerchantGroupId(), vo.getMerchantId(), vo.getSiteId())) {
            ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
            mv.addObject("message", "用户不含导出"
                    + vo.getMerchantGroupId()
                    + "," + vo.getMerchantId()
                    + "," + vo.getSiteId() + "的权限");
            return mv;
        }

        Long detailCount = tbBankBillCheckService.countEexit(vo);
        if (detailCount >= 20000) {
            ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
            mv.addObject("message", "导出明细记录超过2万条，无法导出");
            return mv;
        }
        return tbBankBillCheckService.exportEexitDetailData(request, vo);
    }
}