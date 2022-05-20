package com.hgsoft.modules.monitor.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.annotation.AopLog;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.monitor.entity.ChargeExcepion;
import com.hgsoft.modules.monitor.service.ChargeExcepionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 收费异常管理Controller
 * @author 吴鉴武
 * @version 2021-04-21 22:35:10
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "收费异常管理")
@RestController("com.hgsoft.modules.monitor.web.controller.ChargeExcepionController")
@RequestMapping("api/v1/monitor/chargeExcepion")
public class ChargeExcepionController {

    @Autowired
    private ChargeExcepionService chargeExcepionService;
    @Autowired
    private UserMerchantPermissionswrapper wrapper;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询收费异常管理", notes = "分页查询收费异常管理")
    public ResultBean<IPage<ChargeExcepion>> findPage(Page<ChargeExcepion> page, @RequestBody ChargeExcepion chargeExcepion){
         return wrapper.permissionsCheck(null,null,(param)->{
             chargeExcepion.setUserMerchantParam(param);
             return chargeExcepionService.chargeExcepionPage(page, chargeExcepion);
        });
    }

    /**
     * 确认收费异常记录
     */
    @PutMapping("/confirm")
    @ApiOperation(value = "更新收费异常管理数据", notes = "更新收费异常管理数据")
    @RequiresPermissions(value = {"chargeExcepion:edit"})
    @AopLog(module = "tb_etc_static", optType = "edit", description = "更新收费异常管理")
    public ResultBean<Void> updateChargeExcepion(@RequestBody List<ChargeExcepion> list){
        chargeExcepionService.confirm(list);
        return ResultHandler.ok();
    }


    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出收费异常管理数据", notes = "导出收费异常管理数据")
    @RequiresPermissions(value = {"chargeExcepion:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody ChargeExcepion chargeExcepion) {
        return wrapper.permissionsCheck(null,null,(param)-> {
            chargeExcepion.setUserMerchantParam(param);
            return chargeExcepionService.exportData(request, chargeExcepion);
        }).getData();
    }
}