package com.hgsoft.modules.gdetc.web.controller;

import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.gdetc.entity.GdetcProfit;
import com.hgsoft.modules.gdetc.entity.QueryCondition;
import com.hgsoft.modules.gdetc.service.GdetcProfitService;
import com.hgsoft.modules.report.entity.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 联合电服收益控制层
 * Created by 吴鉴武
 * date:2022/5/6 15:42
 */
@RestController
@Api("联合电服收益控制层")
@RequestMapping("api/v1/gdetcprofit")
@RequiredArgsConstructor
public class GdetcProfitController {

    private final GdetcProfitService gdetcProfitService;

    /**
     * 根据条件分页查询收益数据
     *
     * @param pageVo
     * @param condition
     * @return
     */
    @RequiresPermissions("gdetc:view")
    @ApiOperation(value = "根据条件分页查询收益数据", notes = "根据条件分页查询收益数据")
    @PostMapping("data")
    public ResultBean<PageVo<GdetcProfit>> findPage(PageVo<GdetcProfit> pageVo, @RequestBody QueryCondition condition) {
        return ResultHandler.ok(gdetcProfitService.findPage(pageVo, condition));
    }

    /**
     * 根据条件导出收益数据
     *
     * @param condition
     * @return
     */
    @RequiresPermissions("gdetc:export")
    @ApiOperation(value = "根据条件导出收益数据", notes = "根据条件导出收益数据")
    @PostMapping("exportExcel")
    public ModelAndView export(@RequestBody QueryCondition condition, HttpServletRequest request, HttpServletResponse response) {
        return gdetcProfitService.exportData(condition, request, response);
    }
}
