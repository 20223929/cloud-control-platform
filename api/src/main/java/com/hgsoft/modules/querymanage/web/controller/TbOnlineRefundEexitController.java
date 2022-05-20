package com.hgsoft.modules.querymanage.web.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.Page;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.querymanage.entity.TbOnlineRefundEexit;
import com.hgsoft.modules.querymanage.service.TbOnlineRefundEexitService;
import com.hgsoft.modules.report.entity.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 银行退费流水查询Controller
 * @author 雷新辉
 * @version 2021-04-22 09:25:22
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "银行退费流水查询")
@RestController("com.hgsoft.modules.querymanage.web.controller.TbOnlineRefundEexitController")
@RequestMapping("api/v1/querymanage/tbOnlineRefundEexit")
public class TbOnlineRefundEexitController {

    @Autowired
    private TbOnlineRefundEexitService tbOnlineRefundEexitService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询银行退费流水查询", notes = "分页查询银行退费流水查询")
    public ResultBean<IPage<TbOnlineRefundEexit>> findPage(PageVo<TbOnlineRefundEexit> page, @RequestBody TbOnlineRefundEexit tbOnlineRefundEexit){
        String[] timeScope = tbOnlineRefundEexit.getTimeScope();
        if (timeScope.length > 0){
            tbOnlineRefundEexit.setBeginRefundTime(DateUtil.parse(timeScope[0]));
            tbOnlineRefundEexit.setEndRefundTime(DateUtil.parse(timeScope[1]));
        }
        getNodeLevel(tbOnlineRefundEexit);
        return ResultHandler.ok(tbOnlineRefundEexitService.tbOnlineRefundEexitPage(page, tbOnlineRefundEexit));
    }

    /**
      * 根据tbRefundApplySysId查询数据
      */
    @GetMapping("/{tbRefundApplySysId}")
    @ApiOperation(value = "根据主键查询银行退费流水查询数据", notes = "根据主键查询银行退费流水查询数据")
    public ResultBean<TbOnlineRefundEexit> findByPrimaryKey(@PathVariable String tbRefundApplySysId){
        return ResultHandler.ok(tbOnlineRefundEexitService.findUniqueByProperty(tbRefundApplySysId));
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出银行退费流水查询数据", notes = "导出银行退费流水查询数据")
    @RequiresPermissions(value = {"tbOnlineRefundEexit:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody TbOnlineRefundEexit tbOnlineRefundEexit) {
        String[] timeScope = tbOnlineRefundEexit.getTimeScope();
        if (timeScope.length > 0){
            tbOnlineRefundEexit.setBeginRefundTime(DateUtil.parse(timeScope[0]));
            tbOnlineRefundEexit.setEndRefundTime(DateUtil.parse(timeScope[1]));
        }
        getNodeLevel(tbOnlineRefundEexit);
        return tbOnlineRefundEexitService.exportData(request, tbOnlineRefundEexit);
    }

    /**
     * 获取运营方节点
     * @param eexit
     * @return
     */
    public TbOnlineRefundEexit getNodeLevel(TbOnlineRefundEexit eexit) {
        String nodeLevel = eexit.getNodeLevel();
        String operatorCode = eexit.getOperatorCode();
        if (StringUtils.isNotEmpty(nodeLevel)){
            switch (nodeLevel){
                case "1" : eexit.setMerchantGroupId(operatorCode);break;
                case "2" : eexit.setMerchantId(operatorCode);break;
                case "3" : eexit.setSiteId(operatorCode);break;
            }
        }
        return eexit;
    }
}