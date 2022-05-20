package com.hgsoft.modules.settlement.web.controller;

import com.hgsoft.modules.settlement.entity.TbBankSettlementDetail;
import com.hgsoft.modules.settlement.service.TbBankSettlementDetailService;

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
 * 银行预结算明细Controller
 * @author 何志豪
 * @version 2021-05-08 05:37:06
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "银行预结算明细")
@RestController("com.hgsoft.modules.settlement.web.controller.TbBankSettlementDetailController")
@RequestMapping("api/v1/settlement/tbBankSettlementDetail")
public class TbBankSettlementDetailController {

    @Autowired
    private TbBankSettlementDetailService tbBankSettlementDetailService;

    @PostMapping("/data")
    @ApiOperation(value = "分页查询银行预结算明细", notes = "分页查询银行预结算明细")
    public ResultBean<IPage<TbBankSettlementDetail>> findPage(Page<TbBankSettlementDetail> page, @RequestBody TbBankSettlementDetail tbBankSettlementDetail){
        return ResultHandler.ok(tbBankSettlementDetailService.tbBankSettlementDetailPage(page, tbBankSettlementDetail));
    }

    /**
     * 查询list 集合
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询银行预结算明细集合", notes = "查询银行预结算明细集合")
    public ResultBean<List<TbBankSettlementDetail>> list(@RequestBody TbBankSettlementDetail tbBankSettlementDetail) {
        return ResultHandler.ok(tbBankSettlementDetailService.findList(tbBankSettlementDetail));
    }

    /**
      * 根据主键查询数据
      */
    @GetMapping("/{transactionId}")
    @ApiOperation(value = "根据主键查询银行预结算明细数据", notes = "根据主键查询银行预结算明细数据")
    public ResultBean<TbBankSettlementDetail> findByPrimaryKey(@PathVariable String transactionId){
        return ResultHandler.ok(tbBankSettlementDetailService.getByPrimaryKey(transactionId));
    }

    @PostMapping("/findDataByIds")
    @ApiOperation(value = "根据ID查询银行预结算明细数据", notes = "根据ID查询银行预结算明细数据")
    public ResultBean<Collection<TbBankSettlementDetail>> findDataByIds(@RequestBody List<String> transactionIds){
        if (transactionIds == null || transactionIds.isEmpty()) {
            return ResultHandler.ok(new ArrayList<>());
        }
        return ResultHandler.ok(tbBankSettlementDetailService.listByIds(transactionIds));
    }

    @PostMapping("/findDataByPropValues")
    @ApiOperation(value = "根据propField, values查询数据", notes = "根据propField, values查询数据")
    public ResultBean<List<TbBankSettlementDetail>> findDataByPropValues(String propField, @RequestBody List<String> propValues){
        if (propValues == null || propValues.isEmpty() || StringUtils.isBlank(propField)) {
            return ResultHandler.ok(new ArrayList<>());
        }
        return ResultHandler.ok(tbBankSettlementDetailService.list(new QueryWrapper<TbBankSettlementDetail>().in(propField, propValues)));
    }

    /**
     * 保存
     */
    @PostMapping()
    @ApiOperation(value = "保存银行预结算明细", notes = "保存银行预结算明细")
    @RequiresPermissions(value = {"tbBankSettlementDetail:add"})
    @AopLog(module = "tb_bank_settlement_detail", optType = "add", description = "新增银行预结算明细")
    public ResultBean<Void> saveTbBankSettlementDetail(@RequestBody TbBankSettlementDetail tbBankSettlementDetail, Model model){
        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = ValidateUtil.beanValidator(tbBankSettlementDetail);
        if (StringUtils.isNotBlank(errMsg)){
            return ResultHandler.error(errMsg);
        }
        tbBankSettlementDetailService.saveTbBankSettlementDetail(tbBankSettlementDetail);
        model.addAttribute("id", tbBankSettlementDetail.getTransactionId());
        return ResultHandler.ok();
    }

    /**
     * 根据id更新数据
     */
    @PutMapping("/{transactionId}")
    @ApiOperation(value = "更新银行预结算明细数据", notes = "更新银行预结算明细数据")
    @RequiresPermissions(value = {"tbBankSettlementDetail:edit"})
    @AopLog(module = "tb_bank_settlement_detail", optType = "edit", description = "更新银行预结算明细")
    public ResultBean<Void> updateTbBankSettlementDetail(@PathVariable String transactionId, @RequestBody TbBankSettlementDetail tbBankSettlementDetail, Model model){
        tbBankSettlementDetail.setTransactionId(transactionId);

        /**
         * 后台hibernate-validation插件校验
         */
        String errMsg = ValidateUtil.beanValidator(tbBankSettlementDetail);
        if (StringUtils.isNotBlank(errMsg)){
            return ResultHandler.error(errMsg);
        }

        tbBankSettlementDetailService.update(tbBankSettlementDetail, new LambdaQueryWrapper<TbBankSettlementDetail>()
            .eq(TbBankSettlementDetail::getTransactionId, transactionId));
        model.addAttribute("id", transactionId);

        return ResultHandler.ok();
    }


    /**
      * 批量删除(物理删除)
      */
    @DeleteMapping("/deleteByIds")
    @ApiOperation(value = "移除银行预结算明细数据", notes = "移除银行预结算明细数据")
    @RequiresPermissions(value = {"tbBankSettlementDetail:delete"})
    @AopLog(module = "tb_bank_settlement_detail", optType = "delete", recordId = "ids", description = "删除银行预结算明细")
    public ResultBean<Void> deleteByIds(@RequestBody List<String> ids, Model model) {
        tbBankSettlementDetailService.deleteByPrimaryKey(ids);
        model.addAttribute("ids", StringUtils.wrap(StringUtils.join(ids, ","), ","));
        return ResultHandler.ok();
    }

    @GetMapping("/excelTemplate")
    @ApiOperation(value = "下载银行预结算明细模板", notes = "下载银行预结算明细模板")
    @RequiresPermissions(value = {"tbBankSettlementDetail:download"})
    public ModelAndView exportTbBankSettlementDetailFileTemplate(HttpServletRequest request) {
        return tbBankSettlementDetailService.exportTemplate(request);
    }

    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出银行预结算明细数据", notes = "导出银行预结算明细数据")
    @RequiresPermissions(value = {"tbBankSettlementDetail:export"})
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, @RequestBody TbBankSettlementDetail tbBankSettlementDetail) {
        return tbBankSettlementDetailService.exportData(request, tbBankSettlementDetail);
    }

    /**
     * @param file
     * @return
     * @description 导入数据皆是新建，有id则判断是否冲突，冲突则报错
     */
    @PostMapping("/importExcel")
    @ApiOperation(value = "导入银行预结算明细数据", notes = "导入银行预结算明细数据")
    @RequiresPermissions(value = {"tbBankSettlementDetail:import"})
    @AopLog(module = "tb_bank_settlement_detail", optType = "import", recordId = "ids", description = "导入银行预结算明细")
    public ResultBean importExcel(@RequestParam("file") MultipartFile file, @RequestParam("isNewPk") boolean isNewPk,
                                  String strategy, Model model) throws Exception {
        try {
            List<String> ids = tbBankSettlementDetailService.importExcel(file, isNewPk, strategy);
            model.addAttribute("ids", StringUtils.wrap(StringUtils.join(ids, ","), ','));
        } catch (BusinessException e) {
            log.error("导入数据出错", e);
            throw new BusinessException("导入数据出错");
        }
        return ResultHandler.ok();
    }

}