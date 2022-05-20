package com.hgsoft.modules.report.web.controller;

import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.entity.ServiceRecvReport;
import com.hgsoft.modules.report.service.ServiceRecvReportService;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
 * 服务方应收报表Controller
 * @author
 * @version 2022-04-28 05:44:26
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "服务方应收报表")
@RestController("com.hgsoft.modules.report.web.controller.ServiceRecvReportController")
@RequestMapping("/api/v1/report/serviceRecvReport")
public class ServiceRecvReportController {

    @Autowired
    private ServiceRecvReportService serviceRecvReportService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询服务方应收报表", notes = "分页查询服务方应收报表")
    public ResultBean<IPage<ServiceRecvReport>> findPage(PageVo<ServiceRecvReport> page, @RequestBody ServiceRecvReport serviceRecvReport){
        getConditions(serviceRecvReport);
        return ResultHandler.ok(serviceRecvReportService.serviceRecvReportPage(page, serviceRecvReport));
    }

    /**
     * 查询list 集合
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询服务方应收报表集合", notes = "查询服务方应收报表集合")
    public ResultBean<List<ServiceRecvReport>> list(@RequestBody ServiceRecvReport serviceRecvReport) {
        getConditions(serviceRecvReport);
        return ResultHandler.ok(serviceRecvReportService.findList(serviceRecvReport));
    }

    /**
      * 根据主键查询数据
      */
    @GetMapping("/{sysId}")
    @ApiOperation(value = "根据主键查询服务方应收报表数据", notes = "根据主键查询服务方应收报表数据")
    public ResultBean<ServiceRecvReport> findByPrimaryKey(@PathVariable String sysId){
        return ResultHandler.ok(serviceRecvReportService.getByPrimaryKey(sysId));
    }

    @PostMapping("/findDataByIds")
    @ApiOperation(value = "根据ID查询服务方应收报表数据", notes = "根据ID查询服务方应收报表数据")
    public ResultBean<Collection<ServiceRecvReport>> findDataByIds(@RequestBody List<String> sysIds){
        if (sysIds == null || sysIds.isEmpty()) {
            return ResultHandler.ok(new ArrayList<>());
        }
        return ResultHandler.ok(serviceRecvReportService.listByIds(sysIds));
    }

    @PostMapping("/findDataByPropValues")
    @ApiOperation(value = "根据propField, values查询数据", notes = "根据propField, values查询数据")
    public ResultBean<List<ServiceRecvReport>> findDataByPropValues(String propField, @RequestBody List<String> propValues){
        if (propValues == null || propValues.isEmpty() || StringUtils.isBlank(propField)) {
            return ResultHandler.ok(new ArrayList<>());
        }
        return ResultHandler.ok(serviceRecvReportService.list(new QueryWrapper<ServiceRecvReport>().in(propField, propValues)));
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出服务方应收报表数据", notes = "导出服务方应收报表数据")
    @RequiresPermissions(value = {"serviceRecvReport:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody ServiceRecvReport serviceRecvReport) {
        getConditions(serviceRecvReport);
        return serviceRecvReportService.exportData(request, serviceRecvReport);
    }

    /**
     * 获取运营方节点
     * @param entity
     * @return
     */
    public ServiceRecvReport getNodeLevel(ServiceRecvReport entity) {
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
    private ServiceRecvReport getConditions(ServiceRecvReport entity) {
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