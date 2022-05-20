package com.hgsoft.modules.querymanage.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
/**
 * 银行退费流水查询 Entity
 * @author 雷新辉
 * @version 2021-04-22 09:25:22
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="联机退费流水表", description="银行退费流水查询")
//@TableName(value = "tb_online_refund_eexit", resultMap = "BaseResultMap")
public class TbOnlineRefundEexit extends DataEntity<TbOnlineRefundEexit> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TbOnlineRefundEexit() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	@Length(min=1, max=20, message="系统编号长度必须介于 1 和 20 之间")
    @TableId("sys_id")
	@ApiModelProperty(value = "系统编号")
	private String sysId;		// 系统编号

	@Length(min=1, max=64, message="拓展方长度必须介于 1 和 64 之间")
	@Excel(name="拓展方", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "拓展方")
	private String merchantGroupIdName;		// 拓展方

	@Length(min=1, max=64, message="运营方长度必须介于 1 和 64 之间")
	@Excel(name="运营方", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "运营方")
	private String merchantIdName;		// 运营方

	@Length(min=1, max=64, message="场景长度必须介于 1 和 64 之间")
	@Excel(name="场景", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "场景")
	private String siteIdName;		// 场景

	@TableField(exist = false)
	@Excel(name = "车牌号码", width = 25)
	private String vehicleId;//车牌号码

	@Length(min=1, max=40, message="交易流水号长度必须介于 1 和 40 之间")
	@Excel(name="原交易流水号", width = 25)
	@TableField("transaction_id")
	@ApiModelProperty(value = "原交易流水号")
	private String transactionId;		// 交易流水号

	@Length(min=1, max=40, message="退费流水号长度必须介于 1 和 40 之间")
	@Excel(name="退费交易流水号", width = 25)
	@TableField("refund_trans_id")
	@ApiModelProperty(value = "退费交易流水号")
	private String refundTransId;		// 退费流水号

	@Excel(name="投诉单号", width = 25)
	@TableField("refund_apply_no")
	@ApiModelProperty(value = "投诉单号")
	private String refundApplyNo;		// 投诉单号

	@Length(min=1, max=64, message="退费类型长度必须介于 1 和 64 之间")
	@Excel(name="退费类型", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "退费类型")
	private String refundTypeName;		// 退费类型

	@Excel(name="服务类型", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "服务类型")
	private String serviceTypeName;		// 服务类型

	@Excel(name = "原支付交易金额(元)", width = 25)
	@TableField("merchant_real_fee")
	@ApiModelProperty(value = "原支付交易金额")
	private String merchantRealFee;

	@Length(min=1, max=11, message="优惠金额长度必须介于 1 和 11 之间")
	@Excel(name="优惠金额(元)", width = 25)
	@TableField("merchant_discount_fee")
	@ApiModelProperty(value = "优惠金额")
	private String merchantDiscountFee;		// 优惠金额

	@TableField("merchant_pay_fee")
	@ApiModelProperty(value = "应收金额")
	private Integer merchantPayFee;

	@Length(min=1, max=11, message="退费金额长度必须介于 1 和 11 之间")
	@Excel(name="退费金额(元)", width = 25)
	@TableField("refund_fee")
	@ApiModelProperty(value = "退费金额")
	private String refundFee;		// 退费金额

	@NotNull(message="退费日期不能为空")
    @Excel(name="退费时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(exist = false)
	@ApiModelProperty(value = "退费时间")
	private Date applyTime;		// 退费日期

	@NotNull(message="原交易时间不能为空")
	@Excel(name="交易时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@TableField("trans_time")
	@ApiModelProperty(value = "原交易时间")
	private Date transTime;		// 原交易时间

	@NotNull(message="退费状态，0-待退费、1-退费中、2-退费成功、3-退费失败不能为空")
	@TableField(exist = false)
	@Excel(name="状态", width = 25)
	@ApiModelProperty(value = "退费状态")
	private String refundStateName;

	@Length(min=0, max=60, message="退费订单号长度必须介于 0 和 60 之间")
//	@Excel(name="退费订单号", width = 25)
	@TableField("refund_order_id")
	@ApiModelProperty(value = "退费订单号")
	private String refundOrderId;		// 退费订单号

	@NotNull(message="服务类型不能为空")
	@TableField("service_type")
	@ApiModelProperty(value = "服务类型")
	private Integer serviceType;		// 服务类型

	@Length(min=1, max=60, message="拓展方长度必须介于 1 和 60 之间")
    @TableField("merchant_group_id")
	@ApiModelProperty(value = "拓展方")
	private String merchantGroupId;		// 拓展方

	@Length(min=1, max=60, message="运营方长度必须介于 1 和 60 之间")
    @TableField("merchant_id")
	@ApiModelProperty(value = "运营方")
	private String merchantId;		// 运营方

	@Length(min=1, max=60, message="场景长度必须介于 1 和 60 之间")
    @TableField("site_id")
	@ApiModelProperty(value = "场景")
	private String siteId;		// 场景

	@Length(min=1, max=20, message="车牌号码长度必须介于 1 和 20 之间")
	@TableField("vehicle_plate")
	@ApiModelProperty(value = "车牌号码")
	private String vehiclePlate;		// 车牌号码

	@TableField("vehicle_color")
	@ApiModelProperty(value = "车牌颜色")
	private Integer vehicleColor;		// 车牌颜色

	@NotNull(message="退费类型不能为空")
	@TableField(exist = false)
	@ApiModelProperty(value = "退费类型")
	private Integer refundType;		// 退费类型

	@NotNull(message="退费状态，0-待退费、1-退费中、2-退费成功、3-退费失败不能为空")
    @TableField("refund_state")
	@ApiModelProperty(value = "退费状态")
	private Integer refundState;		// 退费状态，0-待退费、1-退费中、2-退费成功、3-退费失败

	@TableField(exist = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date beginRefundTime;		// 开始 退费日期

	@TableField(exist = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endRefundTime;		// 结束 退费日期

	@TableField(exist = false)
	private String operatorCode;//运营方

	@TableField(exist = false)
	private String nodeLevel;//运营方节点

	@TableField(exist = false)
	private String[] timeScope;//时间范围

	@TableField("tb_refund_apply_sys_id")
	@ApiModelProperty(value = "退费申请表_系统编号")
	private Long tbRefundApplySysId;		// 退费申请表_系统编号
}