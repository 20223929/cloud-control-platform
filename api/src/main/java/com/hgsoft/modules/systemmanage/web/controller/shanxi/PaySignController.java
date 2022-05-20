package com.hgsoft.modules.systemmanage.web.controller.shanxi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.systemmanage.entity.shanxi.PaySign;
import com.hgsoft.modules.systemmanage.service.PaySignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 二次签约名单管理Controller
 * @author 吴鉴武
 * @version 2021-04-23 02:57:56
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "二次签约名单管理")
@RestController("com.hgsoft.modules.systemmanage.web.controller.shanxi.PaySignController")
@RequestMapping("api/v1/manage/paySign")
@RequiredArgsConstructor
public class PaySignController {

    private final PaySignService paySignService;

    /**
     * 分页查询二次签约名单管理
     * @param page
     * @param paySign
     * @return
     */
    @PostMapping("/data")
    @ApiOperation(value = "分页查询二次签约名单管理", notes = "分页查询二次签约名单管理")
    public ResultBean<IPage<PaySign>> findPage(Page<PaySign> page, @RequestBody PaySign paySign){
       return ResultHandler.ok(paySignService.findPage(page,paySign));
    }

    /**
     * 修改二签名单状态
     * @param sysId
     * @param paySign
     * @return
     */
    @PutMapping("/editStatus/{sysId}")
    @RequiresPermissions("paySign:edit")
    @ApiOperation(value = "修改二签名单状态",notes = "修改二签名单状态")
    public ResultBean<String> editStatus(@PathVariable Long sysId,@RequestBody PaySign paySign){
        return ResultHandler.ok(paySignService.editStatus(sysId,paySign.getStatus(),paySign.getVehicleNumber()));
    }

    /**
     * 导出二次签约名单数据
     * @param paySign
     * @return
     */
    @PostMapping("/exportExcel")
    @RequiresPermissions("paySign:export")
    @ApiOperation(value = "导出二次签约名单数据", notes = "导出二次签约名单数据")
    public ModelAndView exportExcel(@RequestBody PaySign paySign){
        return paySignService.exportExcel(paySign);
    }
}