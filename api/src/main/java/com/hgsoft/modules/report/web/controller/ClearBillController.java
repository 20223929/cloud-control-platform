package com.hgsoft.modules.report.web.controller;

import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.report.entity.ClearBill;
import com.hgsoft.modules.report.entity.PageVo;
import com.hgsoft.modules.report.service.ClearBillService;
import lombok.extern.slf4j.Slf4j;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import org.apache.commons.lang3.StringUtils;
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
 * 清分对账报表Controller
 * @author 郑裕强
 * @version 2022-05-05 02:34:08
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "清分对账报表")
@RestController("com.hgsoft.modules.report.web.controller.ClearBillController")
@RequestMapping("/api/v1/report/clearBill")
public class ClearBillController {

    @Autowired
    private ClearBillService clearBillService;
    @Autowired
    private UserMerchantPermissionswrapper wrapper;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询清分对账报表", notes = "分页查询清分对账报表")
    public ResultBean<PageVo<ClearBill>> findPage(PageVo<ClearBill> page, @RequestBody ClearBill queryParam){
        getNodeLevel(queryParam);
        return ResultHandler.ok(clearBillService.clearBillPage(page, queryParam));
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出清分对账报表数据", notes = "导出清分对账报表数据")
    @RequiresPermissions(value = {"clearBill:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody ClearBill clearBill) {
        getNodeLevel(clearBill);
        return clearBillService.exportData(request, clearBill);
    }

    /**
     * 获取运营方节点
     * @param entity
     * @return
     */
    public ClearBill getNodeLevel(ClearBill entity) {
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
}