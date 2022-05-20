package com.hgsoft.modules.settlement.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.bankbillcheck.entity.TbEtcTransactionEexitExportExeclVo;
import com.hgsoft.modules.settlement.entity.SettlementSearchVo;
import com.hgsoft.modules.settlement.service.TbCenterSettlementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 联网中心结算明细Controller
 * @author 何志豪
 * @version 2021-05-08 05:00:37
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "联网中心结算明细")
@RestController("com.hgsoft.modules.settlement.web.controller.TbCenterSettlementDetailController")
@RequestMapping("api/v1/settlement/tbCenterSettlementDetail")
public class TbCenterSettlementDetailController {

    @Autowired
    private TbCenterSettlementService service;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询联网中心结算明细", notes = "分页查询联网中心结算明细")
    public ResultBean<IPage<TbEtcTransactionEexitExportExeclVo>> findPage(Page<TbEtcTransactionEexitExportExeclVo> page, @RequestBody SettlementSearchVo vo){
        return ResultHandler.ok(service.findEexitPage(page, vo));
    }

    @GetMapping("/excelTemplate")
    @ApiOperation(value = "下载联网中心结算明细模板", notes = "下载联网中心结算明细模板")
    @RequiresPermissions(value = {"tbCenterSettlementDetail:download"})
    public ModelAndView exportTbCenterSettlementDetailFileTemplate(HttpServletRequest request) {
        return service.exportTemplateForEexit(request);
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出联网中心结算明细数据", notes = "导出联网中心结算明细数据")
    @RequiresPermissions(value = {"tbCenterSettlementDetail:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody SettlementSearchVo vo) {
        Long countEexit = service.countEexit(vo);
        if(countEexit !=null&&countEexit>20000){
            ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
            mv.addObject("message", "导出明细记录超过2万条，无法导出");
            return mv;
        }
        return service.exportDataForEexit(request, vo);
    }


}