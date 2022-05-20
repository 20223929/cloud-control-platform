package com.hgsoft.modules.complaintrefund.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.DataEntity;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 投诉信息处理 Entity
 * @author 吴鉴武
 * @version 2021-04-25 04:01:38
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="投诉信息处理", description="投诉信息处理")
@TableName(value = "tb_complaint_refund", resultMap = "BaseResultMap")
public class ComplaintRefund extends DataEntity<ComplaintRefund> implements Serializable {

    private static final long serialVersionUID = 1L;

    public ComplaintRefund() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

    /** 查询参数 */
    private String[] timeScope;
    private String searchId;
    private Integer nodeLevel;
    private UserMerchantPermissionswrapper.UserMerchantParam userMerchantParam;

    @TableId("sys_id")
    private Long sysId;

    @Excel(name="投诉时间", width = 25)
    @TableField("complaint_time")
	@ApiModelProperty(value = "投诉时间")
	private String complaintTime;		// 投诉时间

	@Excel(name="退费处理时间", width = 25)
	@TableField("apply_time")
	@ApiModelProperty(value = "退费处理时间")
	private String applyTime;

	@Length(min=1, max=64, message="投诉单号长度必须介于 1 和 64 之间")
    @Excel(name="投诉单号", width = 40)
    @TableField("complaint_id")
	@ApiModelProperty(value = "投诉单号")
	private String complaintId;		// 投诉单号

	@NotNull(message="投诉通道不能为空")
    @TableField("complaint_channel")
	@ApiModelProperty(value = "投诉通道")
	private Integer complaintChannel;		// 投诉通道

	@Excel(name="投诉通道", width = 25)
	private String complaintChannelDesc;

	@NotNull(message="投诉类型不能为空")
//    @Excel(name="投诉类型", width = 25)
    @TableField("complaint_type")
	@ApiModelProperty(value = "投诉类型")
	private String complaintTypeDesc;		// 投诉类型

	@Length(min=1, max=64, message="车牌号码长度必须介于 1 和 64 之间")
    @Excel(name="车牌号码", width = 25)
    @TableField("vehicle_number")
	@ApiModelProperty(value = "车牌号码")
	private String vehicleNumber;		// 车牌号码

	@TableField("vehicle_color")
	@ApiModelProperty(value = "车牌颜色")
	private Integer vehicleColor;		// 车牌号码

	@Excel(name="车牌颜色", width = 25)
	private String vehicleColorDesc;

	@NotNull(message="服务类型不能为空")
    @TableField("service_type")
	@ApiModelProperty(value = "服务类型")
	private Integer serviceType;		// 服务类型

	@Excel(name="服务类型", width = 25)
	private String serviceTypeDesc;

	@Length(min=1, max=64, message="拓展方长度必须介于 1 和 64 之间")
    @TableField("merchant_group_id")
	@ApiModelProperty(value = "拓展方")
	private String merchantGroupId;		// 拓展方

	@Excel(name="拓展方", width = 25)
	private String merchantGroupName;

	@Length(min=1, max=64, message="服务方长度必须介于 1 和 64 之间")
    @TableField("merchant_id")
	@ApiModelProperty(value = "服务方")
	private String merchantId;		// 服务方

	@Excel(name="服务方", width = 25)
	private String merchantName;		// 服务方

	@Length(min=1, max=64, message="场景长度必须介于 1 和 64 之间")
	@ApiModelProperty(value = "场景")
	@TableField("site_id")
	private String siteId;		// 场景

	@Excel(name="场景", width = 25)
	private String siteName;		// 场景

	@NotNull(message="处理状态不能为空")
//    @Excel(name="处理状态", width = 25)
    @TableField("handle_status")
	@ApiModelProperty(value = "处理状态")
	private String refundApplyHandlerResultDesc;		// 处理状态

	private Integer refundFlag;		// 是否退费

	@NotNull(message="是否退费不能为空")
    @Excel(name="是否退费", width = 25)
    @TableField("refund_flag_desc")
	@ApiModelProperty(value = "是否退费")
	private String refundFlagDesc;		// 是否退费

	@NotNull(message="退费形式不能为空")
    @TableField("refund_type")
	@ApiModelProperty(value = "退费形式")
	private Integer refundType;		// 退费形式

	@Excel(name="退费形式", width = 25)
	private String refundTypeDesc;

	@NotNull(message="退费状态不能为空")
    @TableField("refund_status")
	@ApiModelProperty(value = "退费状态")
	private Integer refundStatus;		// 退费状态

	@Excel(name="退费状态", width = 25)
	private String refundStatusDesc;

	@Length(min=1, max=64, message="交易流水号长度必须介于 1 和 64 之间")
    @Excel(name="交易流水号", width = 40)
    @TableField("transaction_id")
	@ApiModelProperty(value = "交易流水号")
	private String transactionId;		// 交易流水号

	@NotNull(message="客户实付金额不能为空")
    @Excel(name="客户实付金额(元)", width = 25)
    @TableField("actual_pay_fee")
	@ApiModelProperty(value = "客户实付金额")
	private BigDecimal actualPayFee;		// 客户实付金额

	@NotNull(message="退费金额不能为空")
    @Excel(name="退费金额(元)", width = 25)
    @TableField("refund_fee")
	@ApiModelProperty(value = "退费金额")
	private BigDecimal refundFee;		// 退费金额

	private Integer refundApplyHandlerResult; //退费申请处理结果 0-待审核 1-审核通过 2-审核不通过

	private RefundApply apply;

	private OnlineRefundEexit refundEexit;

}