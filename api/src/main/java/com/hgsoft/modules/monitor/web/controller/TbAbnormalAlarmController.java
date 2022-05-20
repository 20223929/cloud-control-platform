package com.hgsoft.modules.monitor.web.controller;

import cn.hutool.core.date.DateUtil;
import com.hgsoft.modules.monitor.entity.TbAbnormalAlarm;
import com.hgsoft.modules.monitor.service.TbAbnormalAlarmService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.util.ValidateUtil;
import com.hgsoft.ecip.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import com.hgsoft.ecip.framework.common.annotation.AopLog;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import org.springframework.ui.Model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

/**
 * 异常报警管理Controller
 * @author 雷新辉
 * @version 2021-07-06 03:40:21
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "异常报警管理")
@RestController("com.hgsoft.modules.monitor.web.controller.TbAbnormalAlarmController")
@RequestMapping("api/v1/monitor/tbAbnormalAlarm")
public class TbAbnormalAlarmController {

    @Autowired
    private TbAbnormalAlarmService tbAbnormalAlarmService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询异常报警管理", notes = "分页查询异常报警管理")
    public ResultBean<IPage<TbAbnormalAlarm>> findPage(Page<TbAbnormalAlarm> page, @RequestBody TbAbnormalAlarm tbAbnormalAlarm){
        String[] timeScope = tbAbnormalAlarm.getTimeScope();
        if (timeScope.length > 0){
            tbAbnormalAlarm.setBeginAlarmTime(DateUtil.parse(timeScope[0]));
            tbAbnormalAlarm.setEndAlarmTime(DateUtil.parse(timeScope[1]));
        }
        return ResultHandler.ok(tbAbnormalAlarmService.tbAbnormalAlarmPage(page, tbAbnormalAlarm));
    }

    /**
     * 查询list 集合
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询异常报警管理集合", notes = "查询异常报警管理集合")
    public ResultBean<List<TbAbnormalAlarm>> list(@RequestBody TbAbnormalAlarm tbAbnormalAlarm) {
        return ResultHandler.ok(tbAbnormalAlarmService.findList(tbAbnormalAlarm));
    }

    /**
      * 根据主键查询数据
      */
    @GetMapping("/{sysId}")
    @ApiOperation(value = "根据主键查询异常报警管理数据", notes = "根据主键查询异常报警管理数据")
    public ResultBean<TbAbnormalAlarm> findByPrimaryKey(@PathVariable String sysId){
        return ResultHandler.ok(tbAbnormalAlarmService.getByPrimaryKey(sysId));
    }

    @PostMapping("/findDataByIds")
    @ApiOperation(value = "根据ID查询异常报警管理数据", notes = "根据ID查询异常报警管理数据")
    public ResultBean<Collection<TbAbnormalAlarm>> findDataByIds(@RequestBody List<String> sysIds){
        if (sysIds == null || sysIds.isEmpty()) {
            return ResultHandler.ok(new ArrayList<>());
        }
        return ResultHandler.ok(tbAbnormalAlarmService.listByIds(sysIds));
    }

    @PostMapping("/findDataByPropValues")
    @ApiOperation(value = "根据propField, values查询数据", notes = "根据propField, values查询数据")
    public ResultBean<List<TbAbnormalAlarm>> findDataByPropValues(String propField, @RequestBody List<String> propValues){
        if (propValues == null || propValues.isEmpty() || StringUtils.isBlank(propField)) {
            return ResultHandler.ok(new ArrayList<>());
        }
        return ResultHandler.ok(tbAbnormalAlarmService.list(new QueryWrapper<TbAbnormalAlarm>().in(propField, propValues)));
    }

    /**
     * 批量处理
     */
    @PutMapping("/dealByIds")
    @ApiOperation(value = "处理报警管理数据", notes = "处理异常报警管理数据")
    @RequiresPermissions(value = {"abnormalAlarm:deal"})
    @AopLog(module = "tb_abnormal_alarm", optType = "deal", recordId = "ids", description = "删除异常报警管理")
    public ResultBean<Void> dealByIds(@RequestBody List<String> ids, Model model) {
        tbAbnormalAlarmService.dealByPrimaryKey(ids);
        model.addAttribute("ids", StringUtils.wrap(StringUtils.join(ids, ","), ","));
        return ResultHandler.ok();
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出异常报警管理数据", notes = "导出异常报警管理数据")
    @RequiresPermissions(value = {"abnormalAlarm:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody TbAbnormalAlarm tbAbnormalAlarm) {
        String[] timeScope = tbAbnormalAlarm.getTimeScope();
        if (timeScope.length > 0){
            tbAbnormalAlarm.setBeginAlarmTime(DateUtil.parse(timeScope[0]));
            tbAbnormalAlarm.setEndAlarmTime(DateUtil.parse(timeScope[1]));
        }
        return tbAbnormalAlarmService.exportData(request, tbAbnormalAlarm);
    }


}