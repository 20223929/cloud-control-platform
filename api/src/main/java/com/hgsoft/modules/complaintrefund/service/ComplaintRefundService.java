package com.hgsoft.modules.complaintrefund.service;

import com.hgsoft.ecip.framework.common.response.ResultBean;
import com.hgsoft.modules.complaintrefund.entity.ComplaintRefund;
import com.hgsoft.modules.complaintrefund.entity.OnlineRefundEexit;
import com.hgsoft.modules.complaintrefund.entity.RefundApply;
import com.hgsoft.modules.report.entity.PageVo;

import javax.servlet.http.HttpServletResponse;

/**
 * 投诉退费管理service
 * Created by 吴鉴武 on 2021/5/7 16:08
 */
public interface ComplaintRefundService {

    /**
     * 分页查询投诉退费管理数据
     *
     * @param page
     * @param complaintRefund
     * @return
     */
    PageVo<ComplaintRefund> findPage(PageVo<ComplaintRefund> page, ComplaintRefund complaintRefund);

    /**
     * 导出Excel
     *
     * @param complaintRefund
     */
    void exportExcel(HttpServletResponse response, ComplaintRefund complaintRefund);

    /**
     * 查找可退费的流水
     *
     * @param refundEexit
     * @return
     */
    PageVo<OnlineRefundEexit> findRefundEexit(PageVo<OnlineRefundEexit> pageVo, OnlineRefundEexit refundEexit);

    /**
     * 保存草稿
     *
     * @param refundApply
     */
    ResultBean<Void> saveApplyDraft(RefundApply refundApply);

    /**
     * 修改草稿
     *
     * @param refundApply
     */
    ResultBean<Void> editApplyDraft(RefundApply refundApply);

    /**
     * 删除草稿
     *
     * @param sysId
     */
    ResultBean<Void> deleteApplyDraft(Long sysId);

    /**
     * 保存投诉退费信息
     *
     * @param complaintRefund
     */
    ResultBean<Void> saveApply(ComplaintRefund complaintRefund);

    /**
     * 处理投诉退费信息
     *
     * @param complaintRefund
     */
    ResultBean<Void> handleApplyAndRefundEexit(ComplaintRefund complaintRefund);

    /**
     * 更新查看退费结果
     *
     * @param sysId
     * @return
     */
    ResultBean<Void> searchRefundResult(Long sysId);

    /**
     * 获取详情
     *
     * @param sysId
     * @return
     */
    ComplaintRefund getDetail(Long sysId, String method);
}
