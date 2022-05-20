package com.hgsoft.modules.report.web.controller;

import com.hgsoft.modules.report.entity.AgentReport;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.service.AgentReportService;

import lombok.extern.slf4j.Slf4j;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hgsoft.ecip.dynamic.datasource.annotation.DS;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 代理商收益报表Controller
 * @author
 * @version 2022-05-06 23:07:31
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "代理商收益报表")
@RestController("com.hgsoft.modules.report.web.controller.AgentReportController")
@RequestMapping("/api/v1/report/agentReport")
public class AgentReportController {

    @Autowired
    private AgentReportService agentReportService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询代理商收益报表", notes = "分页查询代理商收益报表")
    public ResultBean<PageVo<AgentReport>> findPage(PageVo<AgentReport> page, @RequestBody AgentReport agentReport){
        getConditions(agentReport);
        return ResultHandler.ok(agentReportService.agentReportPage(page, agentReport));
    }

    /**
     * 查询list 集合
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询代理商收益报表集合", notes = "查询代理商收益报表集合")
    public ResultBean<List<AgentReport>> list(@RequestBody AgentReport agentReport) {
        getConditions(agentReport);
        return ResultHandler.ok(agentReportService.findList(agentReport));
    }

    /**
      * 根据主键查询数据
      */
    @GetMapping("/{sysId}")
    @ApiOperation(value = "根据主键查询代理商收益报表数据", notes = "根据主键查询代理商收益报表数据")
    public ResultBean<AgentReport> findByPrimaryKey(@PathVariable String sysId){
        return ResultHandler.ok(agentReportService.getByPrimaryKey(sysId));
    }

    @PostMapping("/findDataByIds")
    @ApiOperation(value = "根据ID查询代理商收益报表数据", notes = "根据ID查询代理商收益报表数据")
    public ResultBean<Collection<AgentReport>> findDataByIds(@RequestBody List<String> sysIds){
        if (sysIds == null || sysIds.isEmpty()) {
            return ResultHandler.ok(new ArrayList<>());
        }
        return ResultHandler.ok(agentReportService.listByIds(sysIds));
    }

    @PostMapping("/findDataByPropValues")
    @ApiOperation(value = "根据propField, values查询数据", notes = "根据propField, values查询数据")
    public ResultBean<List<AgentReport>> findDataByPropValues(String propField, @RequestBody List<String> propValues){
        if (propValues == null || propValues.isEmpty() || StringUtils.isBlank(propField)) {
            return ResultHandler.ok(new ArrayList<>());
        }
        return ResultHandler.ok(agentReportService.list(new QueryWrapper<AgentReport>().in(propField, propValues)));
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出代理商收益报表数据", notes = "导出代理商收益报表数据")
    @RequiresPermissions(value = {"agentReport:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody AgentReport agentReport) {
        getConditions(agentReport);
        return agentReportService.exportData(request, agentReport);
    }

    /**
     * 获取运营方节点
     * @param entity
     * @return
     */
    public AgentReport getNodeLevel(AgentReport entity) {
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

    /**
     * 组装查询条件
     * @param entity
     * @return
     */
    private AgentReport getConditions(AgentReport entity) {
        String[] timeScope = entity.getTimeScope();
        if (entity.getTollSettlementInterval() != null && entity.getTollSettlementInterval() == 3) {
            if (timeScope.length > 0) {
                entity.setBeginTrxDate(timeScope[0].substring(0, 7)).setEndTrxDate(timeScope[1].substring(0, 7));
            }
        } else {
            if (timeScope.length > 0) {
                entity.setBeginTrxDate(timeScope[0].substring(0, 10)).setEndTrxDate(timeScope[1].substring(0, 10));
            }
        }
        getNodeLevel(entity);
        return entity;
    }
}