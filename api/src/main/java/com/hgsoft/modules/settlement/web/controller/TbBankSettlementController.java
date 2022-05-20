package com.hgsoft.modules.settlement.web.controller;

import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.settlement.entity.TbBankSettlement;
import com.hgsoft.modules.settlement.entity.TbBankSettlementDetail;
import com.hgsoft.modules.settlement.service.TbBankSettlementService;
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

/**
 * 银行预结算Controller
 * @author 何志豪
 * @version 2021-05-08 05:23:48
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "银行预结算")
@RestController("com.hgsoft.modules.settlement.web.controller.TbBankSettlementController")
@RequestMapping("api/v1/settlement/tbBankSettlement")
public class TbBankSettlementController {

    @Autowired
    private TbBankSettlementService tbBankSettlementService;
    @Autowired
    private UserMerchantPermissionswrapper wrapper;

    /**
     * 分页查询银行预结算
     * @param page
     * @param tbBankSettlement
     * @return
     */
    @PostMapping("/data")
    @ApiOperation(value = "分页查询银行预结算", notes = "分页查询银行预结算")
    public ResultBean<PageVo<TbBankSettlement>> findPage(PageVo<TbBankSettlement> page, @RequestBody TbBankSettlement tbBankSettlement){
        return wrapper.permissionsCheck(tbBankSettlement.getNodeLevel(),tbBankSettlement.getSearchId(),(param) -> {
            tbBankSettlement.setUserMerchantParam(param);
            return tbBankSettlementService.tbBankSettlementPage(page, tbBankSettlement);
        });
    }

    /**
     *银行预结算明细查询
     * @param page
     * @param detail
     * @return
     */
    @PostMapping("/detail")
    @ApiOperation(value = "银行预结算明细查询", notes = "银行预结算明细查询")
    @RequiresPermissions("tbBankSettlement:view")
    public ResultBean<PageVo<TbBankSettlementDetail>> detail(PageVo<TbBankSettlementDetail> page, @RequestBody TbBankSettlementDetail detail){
        return ResultHandler.ok(tbBankSettlementService.detail(page,detail));
    }

    /**
     * 导出银行预结算数据
     * @param request
     * @param response
     * @param tbBankSettlement
     * @return
     */
    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出银行预结算数据", notes = "导出银行预结算数据")
    @RequiresPermissions(value = {"tbBankSettlement:export"})
    public void exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody TbBankSettlement tbBankSettlement) {
        wrapper.permissionsCheck(tbBankSettlement.getNodeLevel(),tbBankSettlement.getSearchId(),(param) -> {
            tbBankSettlement.setUserMerchantParam(param);
            tbBankSettlementService.exportData(response, tbBankSettlement);
            return null;
        });
    }

    /**
     * 导出银行预结算明细数据
     * @param request
     * @param response
     * @param detail
     * @return
     */
    @PostMapping("/exportDetailExcel")
    @ApiOperation(value = "导出银行预结算明细数据", notes = "导出银行预结算明细数据")
    @RequiresPermissions(value = {"tbBankSettlement:export"})
    public void exportDetailData(HttpServletRequest request, HttpServletResponse response, @RequestBody TbBankSettlementDetail detail) {
        tbBankSettlementService.exportDetailData(response,detail);
    }
}