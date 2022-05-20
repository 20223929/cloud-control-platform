package com.hgsoft.modules.querymanage.web.controller.shanxi;

import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.querymanage.entity.shanxi.GasStationQuery;
import com.hgsoft.modules.querymanage.service.shanxi.GasStationQueryService;
import com.hgsoft.modules.report.entity.PageVo;
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

/**
 * 加油站流水查询Controller
 *
 * @author 吴鉴武
 * @version 2021-04-26 01:51:24
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "加油站流水查询")
@RestController("com.hgsoft.modules.querymanage.web.controller.shanxi.GasStationQueryController")
@RequestMapping("api/v1/querymanage/gasStationQuery")
public class GasStationQueryController {

    @Autowired
    private GasStationQueryService gasStationQueryService;
    @Autowired
    private UserMerchantPermissionswrapper wrapper;

    /**
     * 分页查询加油站流水查询
     *
     * @param page
     * @param gasStationQuery
     * @return
     */
    @PostMapping("/data")
    @ApiOperation(value = "分页查询加油站流水查询", notes = "分页查询加油站流水查询")
    public ResultBean<PageVo<GasStationQuery>> findPage(PageVo<GasStationQuery> page, @RequestBody GasStationQuery gasStationQuery) {
        return wrapper.permissionsCheck(gasStationQuery.getNodeLevel(), gasStationQuery.getSearchId(), (param) -> {
            gasStationQuery.setUserMerchantParam(param);
            return gasStationQueryService.findPage(page, gasStationQuery);
        });
    }

    /**
     * 查看加油站流水详情
     *
     * @param queryParam
     * @return
     */
    @RequiresPermissions("gasStationQuery:detail")
    @PostMapping("/detail")
    @ApiOperation(value = "查看加油站流水详情", notes = "查看加油站流水详情")
    public ResultBean<GasStationQuery> detail(@RequestBody GasStationQuery queryParam) {
        return ResultHandler.ok(gasStationQueryService.findDataById(queryParam));
    }

    /**
     * 导出加油站流水信息
     *
     * @param gasStationQuery
     * @return
     */
    @RequiresPermissions("gasStationQuery:export")
    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出加油站流水信息", notes = "导出加油站流水信息")
    public ModelAndView exportExcel(@RequestBody GasStationQuery gasStationQuery) {
        return wrapper.permissionsCheck(gasStationQuery.getNodeLevel(), gasStationQuery.getSearchId(), (param) -> {
            gasStationQuery.setUserMerchantParam(param);
            return gasStationQueryService.exportExcel(gasStationQuery);
        }).getData();
    }
}