package com.hgsoft.modules.bankbillcheck.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.bankbillcheck.entity.*;
import com.hgsoft.modules.bankbillcheck.service.BankCheckService;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.report.entity.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by 吴鉴武 on 2021/7/19 11:18
 */
@Slf4j
@DS("exp-platform-etc")
@Api(tags = "银行对账核对表")
@RestController("com.hgsoft.modules.bankbillcheck.web.controller.BankCheckController")
@RequestMapping("api/v1/bankbillcheck/bankCheck")
@RequiredArgsConstructor
public class BankCheckController {

    private final UserMerchantPermissionswrapper permissionswrapper;
    private final BankCheckService bankCheckService;

    /**
     * 分页查询银行对账信息
     * @param page
     * @param bankCheck
     * @return
     */
    @PostMapping("/data")
    @ApiOperation(value = "分页查询银行对账信息", notes = "分页查询银行对账信息")
    public ResultBean<IPage<BankCheck>> findPage(PageVo<BankCheck> page, @RequestBody BankCheck bankCheck){
        return permissionswrapper.permissionsCheck(bankCheck.getNodeLevel(), bankCheck.getMerchantId(),(param) -> {
            bankCheck.setUserMerchantParam(param);
            return bankCheckService.findBankCheckPage(page, bankCheck);
        });
    }

    /**
     * 分页查询银行对账异常信息
     * @param page
     * @param bankCheckException
     * @return
     */
    @PostMapping("/exception")
    @ApiOperation(value = "分页查询银行对账异常信息", notes = "分页查询银行对账异常信息")
    public ResultBean<IPage<BankCheckException>> findExceptionPage(PageVo<BankCheckException> page, @RequestBody BankCheckException bankCheckException){
        return ResultHandler.ok(bankCheckService.findBankCheckExceptionPage(page,bankCheckException));
    }

    /**
     * 查询明细列表
     * @param bankCheckDetail
     * @return
     */
    @PostMapping("/detail")
    @ApiOperation(value = "查询明细列表", notes = "查询明细列表")
    public ResultBean<List<BankCheckDetail>> findDetailList(@RequestBody BankCheckDetail bankCheckDetail){
        return ResultHandler.ok(bankCheckService.findDetailList(bankCheckDetail));
    }

    /**
     * 保存确认信息
     * @param list
     * @return
     */
    @PostMapping("/confirm/save")
    @ApiOperation(value = "保存确认信息", notes = "保存确认信息")
    public ResultBean<ResponseEntity> saveConfirmData(@RequestBody List<BankCheckConfirm> list){
        return ResultHandler.ok(bankCheckService.saveConfirmData(list));
    }

    /**
     * 导出银行对账信息
     * @param bankCheck
     * @return
     */
    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出银行对账信息", notes = "导出银行对账信息")
    public ModelAndView exportExcel(@RequestBody BankCheck bankCheck){
        return permissionswrapper.permissionsCheck(bankCheck.getNodeLevel(), bankCheck.getMerchantId(),(param) -> {
            bankCheck.setUserMerchantParam(param);
            return bankCheckService.exportExcel(bankCheck);
        }).getData();
    }

    /**
     * 导出银行对账异常信息
     * @param bankCheckException
     * @return
     */
    @PostMapping("/exportExceptionExcel")
    @ApiOperation(value = "导出银行对账异常信息", notes = "导出银行对账异常信息")
    public ModelAndView exportExceptionExcel(@RequestBody BankCheckException bankCheckException){
        return bankCheckService.exportExceptionExcel(bankCheckException);
    }

    /**
     * 导出流水明细
     * @param bankCheck
     * @return
     */
    @PostMapping("/exportDetail")
    @ApiOperation(value = "导出流水明细", notes = "导出流水明细")
    public ModelAndView exportDetailExcel(@RequestBody BankCheck bankCheck){
        return permissionswrapper.permissionsCheck(bankCheck.getNodeLevel(), bankCheck.getMerchantId(),(param) -> {
            bankCheck.setUserMerchantParam(param);
            return bankCheckService.exportDetailExcel(bankCheck);
        }).getData();
    }
}
