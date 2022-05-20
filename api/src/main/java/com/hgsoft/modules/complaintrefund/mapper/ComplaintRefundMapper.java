package com.hgsoft.modules.complaintrefund.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hgsoft.ecip.framework.core.presistence.DataMapper;
import com.hgsoft.modules.complaintrefund.entity.ComplaintRefund;
import com.hgsoft.modules.complaintrefund.entity.OnlineRefundEexit;
import com.hgsoft.modules.complaintrefund.entity.RefundApply;
import com.hgsoft.modules.report.entity.PageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 投诉信息处理MAPPER接口
 *
 * @author 吴鉴武
 * @version 2021-05-07 02:23:35
 */
@Repository("com.hgsoft.modules.complaintrefund.mapper.ComplaintRefundMapper")
public interface ComplaintRefundMapper extends DataMapper<ComplaintRefund> {
    /**
     * 分页查询投诉退费信息
     *
     * @param page
     * @param entity
     * @return
     */
    @Override
    PageVo<ComplaintRefund> findPage(IPage<ComplaintRefund> page, @Param("entity") ComplaintRefund entity);

    /**
     * 根据条件查找信息列表
     *
     * @param entity
     * @return
     */
    List<ComplaintRefund> findListByConditions(@Param("entity") ComplaintRefund entity);

    /**
     * 获取总计
     *
     * @param entity
     * @return
     */
    ComplaintRefund getSum(@Param("entity") ComplaintRefund entity);

    /**
     * 查找可退费的流水
     *
     * @return
     */
    PageVo<OnlineRefundEexit> findRefundEexit(IPage<OnlineRefundEexit> page, @Param("entity") OnlineRefundEexit entity, @Param("provinceId") String provinceId);

    /**
     * 查找退费流水详情
     *
     * @param transactionId
     * @return
     */
    OnlineRefundEexit findRefundEexitOne(@Param("transactionId") String transactionId);

    /**
     * 插入退费申请记录
     *
     * @param refundApply
     */
    void saveApply(@Param("apply") RefundApply refundApply);

    /**
     * 更新退费申请记录
     *
     * @param refundApply
     */
    void updateApply(@Param("apply") RefundApply refundApply);

    /**
     * 修改退费申请记录的处理结果和投诉时间
     *
     * @param sysId
     * @param result
     */
    void updateHandleResultAndComplaintTime(@Param("sysId") Long sysId, @Param("result") Integer result);

    /**
     * 删除退费申请记录
     *
     * @param sysId
     */
    void deleteApply(@Param("sysId") Long sysId);

    /**
     * 处理退费申请记录
     *
     * @param refundApply
     */
    void handleApply(@Param("apply") RefundApply refundApply);

    /**
     * 更新退费申请退费结果
     *
     * @param refundApply
     */
    void updateApplyRefundResult(@Param("apply") RefundApply refundApply);

    /**
     * 更新在线退费退费结果
     *
     * @param refundEexit
     */
    void updateOnlineRefundResult(@Param("refundEexit") OnlineRefundEexit refundEexit);

    /**
     * 插入在线退费流水
     *
     * @param refundEexit
     */
    void saveOnlineRefundEexit(@Param("refundEexit") OnlineRefundEexit refundEexit);

    /**
     * 获取退费申请记录
     *
     * @param sysId
     * @return
     */
    RefundApply getApplyById(@Param("sysId") Long sysId);

    /**
     * 获取指定退费状态的退费流水记录
     *
     * @param transactionId
     * @param refundResult
     * @return
     */
    OnlineRefundEexit getOnlineRefundEexitById(@Param("transactionId") String transactionId, @Param("refundResult") Integer refundResult);
}
