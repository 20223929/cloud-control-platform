package com.hgsoft.modules.complaintrefund.web.controller.shanxi;

import cn.hutool.core.util.StrUtil;
import com.hgsoft.ecip.dynamic.datasource.annotation.DS;
import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.ecip.framework.common.response.ResultHandler;
import com.hgsoft.modules.complaintrefund.entity.ComplaintRefund;
import com.hgsoft.modules.complaintrefund.entity.OnlineRefundEexit;
import com.hgsoft.modules.complaintrefund.entity.RefundApply;
import com.hgsoft.modules.complaintrefund.service.ComplaintRefundService;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.report.entity.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 投诉信息处理Controller
 *
 * @author 吴鉴武
 * @version 2021-04-25 04:01:38
 */

@Slf4j
@DS("exp-platform-etc")
@Api(tags = "投诉信息处理")
@RestController("com.hgsoft.modules.complaintrefund.web.controller.shanxi.ComplaintRefundController")
@RequestMapping("api/v1/complaintrefund/tbComplaintRefund")
public class ComplaintRefundController {

    @Autowired
    private ComplaintRefundService complaintRefundService;
    @Autowired
    private UserMerchantPermissionswrapper wrapper;
    @Resource(name = "businessRedisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * 分页查询投诉信息处理
     *
     * @param page
     * @param complaintRefund
     * @return
     */
    @PostMapping("/data")
    @ApiOperation(value = "分页查询投诉信息处理", notes = "分页查询投诉信息处理")
    public ResultBean<PageVo<ComplaintRefund>> findPage(PageVo<ComplaintRefund> page, @RequestBody ComplaintRefund complaintRefund) {
        return wrapper.permissionsCheck(complaintRefund.getNodeLevel(), complaintRefund.getSearchId(), (param) -> {
            complaintRefund.setUserMerchantParam(param);
            return complaintRefundService.findPage(page, complaintRefund);
        });
    }

    /**
     * 保存投诉退费信息草稿
     *
     * @param refundApply
     */
    @PostMapping("/saveDraft")
    @ApiOperation(value = "保存投诉退费信息草稿", notes = "保存投诉退费信息草稿")
    @RequiresPermissions("complaintRefund:handle")
    public ResultBean<Void> saveApplyDraft(@RequestBody RefundApply refundApply) {
        return complaintRefundService.saveApplyDraft(refundApply);
    }

    /**
     * 修改草稿
     *
     * @param refundApply
     * @return
     */
    @PutMapping("/editDraft")
    @ApiOperation(value = "修改投诉退费信息草稿", notes = "修改投诉退费信息草稿")
    @RequiresPermissions("complaintRefund:handle")
    public ResultBean<Void> editDraft(@RequestBody RefundApply refundApply) {
        return complaintRefundService.editApplyDraft(refundApply);
    }

    /**
     * 删除草稿
     *
     * @param sysId
     * @return
     */
    @PutMapping("/deleteDraft/{sysId}")
    @ApiOperation(value = "删除投诉退费信息草稿", notes = "删除投诉退费信息草稿")
    @RequiresPermissions("complaintRefund:handle")
    public ResultBean<Void> deleteDraft(@PathVariable Long sysId) {
        return complaintRefundService.deleteApplyDraft(sysId);
    }

    /**
     * 保存投诉退费信息
     *
     * @param complaintRefund
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存投诉退费信息", notes = "保存投诉退费信息")
    @RequiresPermissions("complaintRefund:add")
    public ResultBean<Void> saveComplaintInfo(@RequestBody ComplaintRefund complaintRefund) {
        complaintRefundService.saveApply(complaintRefund);
        return ResultHandler.ok();
    }

    @PostMapping("/handle")
    @ApiOperation(value = "处理投诉退费信息", notes = "处理投诉退费信息")
    @RequiresPermissions("complaintRefund:handle")
    public ResultBean<Void> handleComplaintInfo(@RequestBody ComplaintRefund complaintRefund) {
        return complaintRefundService.handleApplyAndRefundEexit(complaintRefund);
    }

    /**
     * 更新退费结果
     *
     * @param sysId
     * @return
     */
    @PutMapping("/searchRefundResult/{sysId}")
    @ApiOperation(value = "删除投诉退费信息草稿", notes = "删除投诉退费信息草稿")
    @RequiresPermissions("complaintRefund:handle")
    public ResultBean<Void> searchRefundResult(@PathVariable Long sysId) {
        return complaintRefundService.searchRefundResult(sysId);
    }

    /**
     * 获取投诉单号
     *
     * @return
     */
    @GetMapping("/getRefundNo")
    @ApiOperation(value = "获取投诉单号", notes = "获取投诉单号")
    public ResultBean<String> getRefundNo() {
        String refundNo = StrUtil.fillBefore(redisTemplate.opsForValue().increment("refundApplyNo").toString(), '0', 10);
        return ResultHandler.ok(refundNo);
    }

    /**
     * 获取可退费的流水信息
     *
     * @param page
     * @param refundEexit
     * @return
     */
    @PostMapping("/getTransactions")
    @ApiOperation(value = "获取可退费的流水信息", notes = "获取可退费的流水信息")
    public ResultBean<PageVo<OnlineRefundEexit>> getTransactionsByCondition(PageVo<OnlineRefundEexit> page, @RequestBody OnlineRefundEexit refundEexit) {
        return ResultHandler.ok(complaintRefundService.findRefundEexit(page, refundEexit));
    }

    /**
     * 获取投诉信息详情
     *
     * @param sysId
     * @return
     */
    @GetMapping("/detail/{sysId}/{method}")
    @ApiOperation(value = "获取投诉信息详情", notes = "获取投诉信息详情")
    public ResultBean<ComplaintRefund> detail(@PathVariable Long sysId, @PathVariable String method) {
        return ResultHandler.ok(complaintRefundService.getDetail(sysId, method));
    }

    /**
     * 导出投诉信息
     *
     * @param response
     * @param complaintRefund
     */
    @RequiresPermissions("complaintRefund:export")
    @PostMapping("/exportExcel")
    @ApiOperation(value = "导出投诉信息", notes = "导出投诉信息")
    public void exportExcel(HttpServletResponse response, @RequestBody ComplaintRefund complaintRefund) {
        wrapper.permissionsCheck(complaintRefund.getNodeLevel(), complaintRefund.getSearchId(), (param) -> {
            complaintRefund.setUserMerchantParam(param);
            complaintRefundService.exportExcel(response, complaintRefund);
            return null;
        });
    }
}