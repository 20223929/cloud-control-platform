package com.hgsoft.modules.settlementreject.web.controller;

import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.settlementreject.entity.SettlementReject;
import com.hgsoft.modules.settlementreject.service.SettlementRejectService;
import lombok.extern.slf4j.Slf4j;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;


/**
 * 退费详情表Controller
 * @author 郑裕强
 * @version 2022-05-08 03:40:27
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "退费详情表")
@RestController("com.hgsoft.modules.settlementreject.web.controller.SettlementRejectController")
@RequestMapping("/api/v1/settlementreject/settlementReject")
public class SettlementRejectController {

    @Autowired
    private SettlementRejectService settlementRejectService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询退费详情表", notes = "分页查询退费详情表")
    public ResultBean<PageVo<SettlementReject>> findPage(PageVo<SettlementReject> page, @RequestBody SettlementReject settlementReject){
        return ResultHandler.ok(settlementRejectService.settlementRejectPage(page, settlementReject));
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出退费详情表数据", notes = "导出退费详情表数据")
    @RequiresPermissions(value = {"settlementReject:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody SettlementReject settlementReject) {
        return settlementRejectService.exportData(request, settlementReject);
    }
}