package com.hgsoft.modules.bankbillcheck.web.controller;

import com.hgsoft.modules.bankbillcheck.entity.TbCenterBillCheckDetail;
import com.hgsoft.modules.bankbillcheck.service.TbCenterBillCheckDetailService;

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
 * 联网中心对账核对明细表Controller
 * @author 何志豪
 * @version 2021-04-21 02:34:34
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "联网中心对账核对明细表")
@RestController("com.hgsoft.modules.bankbillcheck.web.controller.TbCenterBillCheckDetailController")
@RequestMapping("api/v1/bankbillcheck/tbCenterBillCheckDetail")
public class TbCenterBillCheckDetailController {

    @Autowired
    private TbCenterBillCheckDetailService tbCenterBillCheckDetailService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询联网中心对账核对明细", notes = "分页查询联网中心对账核对明细")
    public ResultBean<IPage<TbCenterBillCheckDetail>> findPage(Page<TbCenterBillCheckDetail> page, @RequestBody TbCenterBillCheckDetail tbCenterBillCheckDetail){
        return ResultHandler.ok(tbCenterBillCheckDetailService.tbCenterBillCheckDetailPage(page, tbCenterBillCheckDetail));
    }

    /**
     * 查询list 集合
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询联网中心对账核对明细集合", notes = "查询联网中心对账核对明细集合")
    public ResultBean<List<TbCenterBillCheckDetail>> list(@RequestBody TbCenterBillCheckDetail tbCenterBillCheckDetail) {
        return ResultHandler.ok(tbCenterBillCheckDetailService.findList(tbCenterBillCheckDetail));
    }

    /**
      * 根据主键查询数据
      */
    @GetMapping("/{transDate}/{merchantGroupId}/{merchantId}/{siteId}/{equipmentId}")
    @ApiOperation(value = "根据主键查询联网中心对账核对明细数据", notes = "根据主键查询联网中心对账核对明细数据")
    public ResultBean<TbCenterBillCheckDetail> findByPrimaryKey(@PathVariable String transDate, @PathVariable String merchantGroupId, @PathVariable String merchantId, @PathVariable String siteId, @PathVariable String equipmentId){
        return ResultHandler.ok(tbCenterBillCheckDetailService.getByPrimaryKey(transDate, merchantGroupId, merchantId, siteId, equipmentId));
    }

}