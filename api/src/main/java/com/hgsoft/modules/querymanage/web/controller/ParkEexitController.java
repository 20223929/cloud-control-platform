package com.hgsoft.modules.querymanage.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.querymanage.entity.ParkEexit;
import com.hgsoft.modules.querymanage.service.ParkEexitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 停车场流水查询Controller
 * @author Andy
 * @version 2021-04-16 22:34:19
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "停车场流水查询")
@RestController("com.hgsoft.modules.querymanage.web.controller.ParkEexitController")
@RequestMapping("api/v1/querymanage/parkEexit")
public class ParkEexitController {

    @Autowired
    private ParkEexitService parkEexitService;
    @Autowired
    private UserMerchantPermissionswrapper wrapper;

    /**
     * 分页查询停车场流水查询
     * @param page
     * @param parkEexit
     * @return
     */
    @PostMapping("/data")
    @ApiOperation(value = "分页查询停车场流水查询", notes = "分页查询停车场流水查询")
    public ResultBean<IPage<ParkEexit>> findPage(Page<ParkEexit> page, @RequestBody ParkEexit parkEexit){
        return wrapper.permissionsCheck(parkEexit.getNodeLevel(),parkEexit.getSearchId(),(param) -> {
            parkEexit.setUserMerchantParam(param);
            return parkEexitService.parkEexitPage(page, parkEexit);
        });
    }

    /**
     * 根据条件查询停车场流水查询数据
     */
    @PostMapping("/detail")
    @ApiOperation(value = "根据条件查询停车场流水查询数据", notes = "根据条件查询停车场流水查询数据")
    public ResultBean<ParkEexit> info(@RequestBody ParkEexit parkEexit){
        return ResultHandler.ok(parkEexitService.findUniqueByProperty(parkEexit));
    }

    /**
     * 导出停车场流水查询数据
     * @param request
     * @param response
     * @param parkEexit
     * @return
     */
    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出停车场流水查询数据", notes = "导出停车场流水查询数据")
    @RequiresPermissions(value = {"parkEexit:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody ParkEexit parkEexit) {
        return wrapper.permissionsCheck(parkEexit.getNodeLevel(),parkEexit.getSearchId(),(param) -> {
            parkEexit.setUserMerchantParam(param);
            return parkEexitService.exportData(request, parkEexit);
        }).getData();
    }
}