package com.hgsoft.modules.report.web.controller;

import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.SettlementReport;
import com.hgsoft.modules.report.service.SettlementReportService;
import lombok.extern.slf4j.Slf4j;
import com.hgsoft.ecip.framework.common.annotation.AopLog;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;

/**
 * 资金结算报表Controller
 * @author 郑裕强
 * @version 2022-05-04 23:25:53
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "资金结算报表")
@RestController("com.hgsoft.modules.report.web.controller.SettlementReportController")
@RequestMapping("/api/v1/report/settlementReport")
public class SettlementReportController {

    @Autowired
    private SettlementReportService settlementReportService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询资金结算报表", notes = "分页查询资金结算报表")
    public ResultBean<PageVo<SettlementReport>> findPage(PageVo<SettlementReport> page, @RequestBody SettlementReport settlementReport){
        getNodeLevel(settlementReport);
        return ResultHandler.ok(settlementReportService.settlementReportPage(page, settlementReport));
    }

    /**
     * 根据id更新资金结算报表转账状态
     */
    @PutMapping("/{sysId}")
    @ApiOperation(value = "根据id更新资金结算报表转账状态", notes = "根据id更新资金结算报表转账状态")
    @RequiresPermissions(value = {"settlementReport:edit"})
    @AopLog(module = "tb_settlement_report", optType = "edit", description = "根据id更新资金结算报表转账状态")
    public ResultBean<Void> updateSettlementReport(@PathVariable String sysId){
        settlementReportService.updateBankTransferSta(sysId, 1);
        return ResultHandler.ok();
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出资金结算报表数据", notes = "导出资金结算报表数据")
    @RequiresPermissions(value = {"settlementReport:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody SettlementReport settlementReport) {
        getNodeLevel(settlementReport);
        return settlementReportService.exportData(request, settlementReport);
    }

    /**
     * 获取运营方节点
     * @param entity
     * @return
     */
    public SettlementReport getNodeLevel(SettlementReport entity) {
        String[] timeScope = entity.getTimeScope();
        if (timeScope.length > 0) {
            entity.setBeginTrxDate(timeScope[0]).setEndTrxDate(timeScope[1]);
        }
        String nodeLevel = entity.getNodeLevel();
        String operatorCode = entity.getSearchId();
        if (StringUtils.isNotEmpty(nodeLevel)){
            switch (nodeLevel){
                case "1" : entity.setMerchantGroupId(operatorCode);break;
                case "2" : entity.setMerchantId(operatorCode);break;
                case "3" : entity.setSiteId(operatorCode);break;
            }
        }
        return entity;
    }

}