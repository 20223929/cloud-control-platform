package com.hgsoft.modules.complaintrefund.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 银行退费流水查询 Entity
 * @author 雷新辉
 * @version 2021-05-08 02:08:38
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="联机退费流水表", description="银行退费流水查询")
@TableName(value = "tb_online_refund_eexit", resultMap = "BaseResultMap")
public class OnlineRefundEexit extends DataEntity<OnlineRefundEexit> implements Serializable {

    private static final long serialVersionUID = 1L;

    public OnlineRefundEexit() {
        super();
        this.setIdType(IDTYPE_IDWORKER);
    }

    /** 查询参数 */
    private String[] timeScope;


    @Length(min=1, max=20, message="系统编号长度必须介于 1 和 20 之间")
    @Excel(name="系统编号", width = 25)
    @TableId("sys_id")
    @ApiModelProperty(value = "系统编号")
    private String sysId;		// 系统编号

    @NotNull(message="退费日期不能为空")
    @Excel(name="退费日期", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("refund_time")
    @ApiModelProperty(value = "退费日期")
    private Date refundTime;		// 退费日期

    @Length(min=1, max=64, message="拓展方长度必须介于 1 和 64 之间")
    @Excel(name="拓展方", width = 25)
    @TableField("merchant_group_id_name")
    @ApiModelProperty(value = "拓展方")
    private String merchantGroupName;		// 拓展方

    @Length(min=1, max=64, message="运营方长度必须介于 1 和 64 之间")
    @Excel(name="运营方", width = 25)
    @TableField("merchant_id_name")
    @ApiModelProperty(value = "运营方")
    private String merchantName;		// 运营方

    @Length(min=1, max=64, message="场景长度必须介于 1 和 64 之间")
    @Excel(name="场景", width = 25)
    @TableField("site_id_name")
    @ApiModelProperty(value = "场景")
    private String siteName;		// 场景

    @NotNull(message="退费类型不能为空")
    @Excel(name="退费类型", width = 25)
    @TableField("refund_type")
    @ApiModelProperty(value = "退费类型")
    private Integer refundType;		// 退费类型

    @Length(min=1, max=64, message="退费类型长度必须介于 1 和 64 之间")
    @Excel(name="退费类型", width = 25)
    @TableField("refund_type_name")
    @ApiModelProperty(value = "退费类型")
    private String refundTypeName;		// 退费类型

    @Length(min=1, max=64, message="服务类型长度必须介于 1 和 64 之间")
    @Excel(name="服务类型", width = 25)
    @TableField("service_type_name")
    @ApiModelProperty(value = "服务类型")
    private String serviceTypeName;		// 服务类型

    @Length(min=1, max=40, message="退费流水号长度必须介于 1 和 40 之间")
    @Excel(name="退费流水号", width = 25)
    @TableField("refund_trans_id")
    @ApiModelProperty(value = "退费流水号")
    private String refundTransId;		// 退费流水号

    @Length(min=1, max=40, message="交易流水号长度必须介于 1 和 40 之间")
    @Excel(name="交易流水号", width = 25)
    @TableField("transaction_id")
    @ApiModelProperty(value = "交易流水号")
    private String transactionId;		// 交易流水号

    @Length(min=1, max=60, message="拓展方长度必须介于 1 和 60 之间")
    @Excel(name="拓展方", width = 25)
    @TableField("merchant_group_id")
    @ApiModelProperty(value = "拓展方")
    private String merchantGroupId;		// 拓展方

    @Length(min=1, max=60, message="运营方长度必须介于 1 和 60 之间")
    @Excel(name="运营方", width = 25)
    @TableField("merchant_id")
    @ApiModelProperty(value = "运营方")
    private String merchantId;		// 运营方

    @Length(min=1, max=60, message="场景长度必须介于 1 和 60 之间")
    @Excel(name="场景", width = 25)
    @TableField("site_id")
    @ApiModelProperty(value = "场景")
    private String siteId;		// 场景

    @NotNull(message="入场时间不能为空")
    @Excel(name="入场时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("en_time")
    @ApiModelProperty(value = "入场时间")
    private Date enTime;		// 入场时间

    private String enTimeStr;

    @NotNull(message="出场时间不能为空")
    @Excel(name="出场时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("ex_time")
    @ApiModelProperty(value = "出场时间")
    private Date exTime;		// 出场时间

    private String exTimeStr;

    @NotNull(message="原交易时间不能为空")
    @Excel(name="原交易时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("trans_time")
    @ApiModelProperty(value = "原交易时间")
    private Date transTime;		// 原交易时间

    private String tradeDay;

    @Length(min=1, max=20, message="车牌号码长度必须介于 1 和 20 之间")
    @Excel(name="车牌号码", width = 25)
    @TableField("vehicle_plate")
    @ApiModelProperty(value = "车牌号码")
    private String vehiclePlate;		// 车牌号码

    @NotNull(message="服务类型不能为空")
    @Excel(name="服务类型", width = 25)
    @TableField("service_type")
    @ApiModelProperty(value = "服务类型")
    private Integer serviceType;		// 服务类型

    @Length(min=1, max=11, message="优惠金额长度必须介于 1 和 11 之间")
    @Excel(name="优惠金额", width = 25)
    @TableField("merchant_discount_fee")
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal merchantDiscountFee;		// 优惠金额

    @Length(min=0, max=40, message="退费订单号长度必须介于 0 和 40 之间")
    @Excel(name="退费订单号", width = 25)
    @TableField("refund_order_id")
    @ApiModelProperty(value = "退费订单号")
    private String refundOrderId;		// 退费订单号

    @Length(min=0, max=60, message="银行退费订单号长度必须介于 0 和 60 之间")
    @Excel(name="银行退费订单号", width = 25)
    @TableField("bank_refund_order_id")
    @ApiModelProperty(value = "银行退费订单号")
    private String bankRefundOrderId;		// 银行退费订单号

    @Length(min=1, max=11, message="退费金额长度必须介于 1 和 11 之间")
    @Excel(name="退费金额", width = 25)
    @TableField("refund_fee")
    @ApiModelProperty(value = "退费金额")
    private BigDecimal refundFee;		// 退费金额

    @Length(min=1, max=60, message="银行订单号长度必须介于 1 和 60 之间")
    @Excel(name="银行订单号", width = 25)
    @TableField("bank_deduction_order_id")
    @ApiModelProperty(value = "银行订单号")
    private String bankDeductionOrderId;		// 银行订单号

    @NotNull(message="退费状态，0-待退费、1-退费中、2-退费成功、3-退费失败不能为空")
    @Excel(name="退费状态，0-待退费、1-退费中、2-退费成功、3-退费失败", width = 25)
    @TableField("refund_state")
    @ApiModelProperty(value = "退费状态，0-待退费、1-退费中、2-退费成功、3-退费失败")
    private Integer refundState;		// 退费状态，0-待退费、1-退费中、2-退费成功、3-退费失败

    @NotNull(message="退费申请表_系统编号不能为空")
    @Excel(name="退费申请表_系统编号", width = 25)
    @TableField("tb_refund_apply_sys_id")
    @ApiModelProperty(value = "退费申请表_系统编号")
    private Long tbRefundApplySysId;		// 退费申请表_系统编号

    @Length(min=1, max=60, message="四级设备编号长度必须介于 1 和 60 之间")
    @Excel(name="四级设备编号", width = 25)
    @TableField("equipment_id")
    @ApiModelProperty(value = "四级设备编号")
    private String equipmentId;		// 四级设备编号

    @NotNull(message="车牌颜色不能为空")
    @Excel(name="车牌颜色", width = 25)
    @TableField("vehicle_color")
    @ApiModelProperty(value = "车牌颜色")
    private Integer vehicleColor;		// 车牌颜色

    private String vehicleColorDesc;

    @NotNull(message="车型不能为空")
    @Excel(name="车型", width = 25)
    @TableField("vehicle_type")
    @ApiModelProperty(value = "车型")
    private Integer vehicleType;		// 车型

    @Length(min=1, max=11, message="应收金额长度必须介于 1 和 11 之间")
    @Excel(name="应收金额", width = 25)
    @TableField("merchant_pay_fee")
    @ApiModelProperty(value = "应收金额")
    private BigDecimal merchantPayFee;		// 应收金额

    @Length(min=1, max=11, message="实收金额长度必须介于 1 和 11 之间")
    @Excel(name="实收金额", width = 25)
    @TableField("merchant_real_fee")
    @ApiModelProperty(value = "实收金额")
    private BigDecimal merchantRealFee;		// 实收金额

    @NotNull(message="商户结算金额不能为空")
    @Excel(name="商户结算金额", width = 25)
    @TableField("merchant_settlement_fee")
    @ApiModelProperty(value = "商户结算金额")
    private BigDecimal merchantSettlementFee;		// 商户结算金额

    @NotNull(message="清算服务费不能为空")
    @Excel(name="清算服务费", width = 25)
    @TableField("clear_service_fee")
    @ApiModelProperty(value = "清算服务费")
    private Double clearServiceFee;		// 清算服务费

    @NotNull(message="清算服务费费率不能为空")
    @Excel(name="清算服务费费率", width = 25)
    @TableField("clear_service_fee_rate")
    @ApiModelProperty(value = "清算服务费费率")
    private Double clearServiceFeeRate;		// 清算服务费费率

    @NotNull(message="银行手续费不能为空")
    @Excel(name="银行手续费", width = 25)
    @TableField("bank_service_fee")
    @ApiModelProperty(value = "银行手续费")
    private Double bankServiceFee;		// 银行手续费

    @NotNull(message="银行手续费费率不能为空")
    @Excel(name="银行手续费费率", width = 25)
    @TableField("bank_service_fee_rate")
    @ApiModelProperty(value = "银行手续费费率")
    private Double bankServiceFeeRate;		// 银行手续费费率

    @Excel(name="更新时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;		// 更新时间

    @Length(min=0, max=500, message="银行退费描述长度必须介于 0 和 500 之间")
    @Excel(name="银行退费描述", width = 25)
    @TableField("bank_refund_msg")
    @ApiModelProperty(value = "银行退费描述")
    private String bankRefundMsg;		// 银行退费描述

    @Excel(name="退费完成时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("refund_complete_time")
    @ApiModelProperty(value = "退费完成时间")
    private Date refundCompleteTime;		// 退费完成时间

    @Excel(name="对账结果", width = 25)
    @TableField("bill_result")
    @ApiModelProperty(value = "对账结果")
    private Integer billResult;		// 对账结果

    @Excel(name="对账时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("bill_time")
    @ApiModelProperty(value = "对账时间")
    private Date billTime;		// 对账时间

    @NotNull(message="扣款时间不能为空")
    @Excel(name="扣款时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("deduction_time")
    @ApiModelProperty(value = "扣款时间")
    private Date deductionTime;		// 扣款时间

    private String deductionTimeStr;

    @NotNull(message="创建时间不能为空")
    @Excel(name="创建时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;		// 创建时间

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginRefundTime;		// 开始 退费日期

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endRefundTime;		// 结束 退费日期

    private String deductionOrderId;

    private String tradeMode;
}