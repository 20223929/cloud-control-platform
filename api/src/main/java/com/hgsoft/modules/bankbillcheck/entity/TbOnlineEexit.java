package com.hgsoft.modules.bankbillcheck.entity;

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
import java.util.Date;
/**
 * 联机交易流水表 Entity
 * @author 何志豪
 * @version 2021-06-18 05:28:43
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="联机交易流水表", description="联机交易流水表")
@TableName(value = "tb_online_eexit", resultMap = "BaseResultMap")
public class TbOnlineEexit extends DataEntity<TbOnlineEexit> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TbOnlineEexit() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	@NotNull(message="系统编号不能为空")
    //@Excel(name="系统编号", width = 25)
    @TableId("sys_id")
	@ApiModelProperty(value = "系统编号")
	private Long sysId;		// 系统编号

	@Length(min=1, max=40, message="交易流水号长度必须介于 1 和 40 之间")
    @Excel(name="交易流水号", width = 25)
    @TableField("transaction_id")
	@ApiModelProperty(value = "交易流水号")
	private String transactionId;		// 交易流水号

	@NotNull(message="服务类型不能为空")
    @Excel(name="服务类型", width = 25)
    @TableField("service_type")
	@ApiModelProperty(value = "服务类型")
	private Integer serviceType;		// 服务类型

	@Length(min=1, max=60, message="一级运营商长度必须介于 1 和 60 之间")
    @Excel(name="一级运营商", width = 25)
    @TableField("merchant_group_id")
	@ApiModelProperty(value = "一级运营商")
	private String merchantGroupId;		// 一级运营商

	@Length(min=1, max=60, message="二级运营商长度必须介于 1 和 60 之间")
    @Excel(name="二级运营商", width = 25)
    @TableField("merchant_id")
	@ApiModelProperty(value = "二级运营商")
	private String merchantId;		// 二级运营商

	@Length(min=1, max=60, message="三级运营商长度必须介于 1 和 60 之间")
    @Excel(name="三级运营商", width = 25)
    @TableField("site_id")
	@ApiModelProperty(value = "三级运营商")
	private String siteId;		// 三级运营商

	@Length(min=1, max=60, message="四级设备编号长度必须介于 1 和 60 之间")
    @Excel(name="四级设备编号", width = 25)
    @TableField("equipment_id")
	@ApiModelProperty(value = "四级设备编号")
	private String equipmentId;		// 四级设备编号

	@Length(min=1, max=60, message="设备编号长度必须介于 1 和 60 之间")
    @Excel(name="设备编号", width = 25)
    @TableField("device_id")
	@ApiModelProperty(value = "设备编号")
	private String deviceId;		// 设备编号

	@Length(min=1, max=20, message="车牌号码长度必须介于 1 和 20 之间")
    @Excel(name="车牌号码", width = 25)
    @TableField("vehicle_plate")
	@ApiModelProperty(value = "车牌号码")
	private String vehiclePlate;		// 车牌号码

	@NotNull(message="车牌颜色不能为空")
    @Excel(name="车牌颜色", width = 25)
    @TableField("vehicle_color")
	@ApiModelProperty(value = "车牌颜色")
	private Integer vehicleColor;		// 车牌颜色

	@NotNull(message="车型不能为空")
    @Excel(name="车型", width = 25)
    @TableField("vehicle_type")
	@ApiModelProperty(value = "车型")
	private Integer vehicleType;		// 车型

	@NotNull(message="入场时间不能为空")
    @Excel(name="入场时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("en_time")
	@ApiModelProperty(value = "入场时间")
	private Date enTime;		// 入场时间

	@NotNull(message="出场时间不能为空")
    @Excel(name="出场时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("ex_time")
	@ApiModelProperty(value = "出场时间")
	private Date exTime;		// 出场时间

	@NotNull(message="交易时间不能为空")
    @Excel(name="交易时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("trans_time")
	@ApiModelProperty(value = "交易时间")
	private Date transTime;		// 交易时间

	@Length(min=0, max=20, message="签约银行长度必须介于 0 和 20 之间")
    @Excel(name="签约银行", width = 25)
    @TableField("sign_bank")
	@ApiModelProperty(value = "签约银行")
	private String signBank;		// 签约银行

	@Length(min=0, max=4000, message="具体应用字段长度必须介于 0 和 4000 之间")
    @Excel(name="具体应用字段", width = 25)
    @TableField("detail")
	@ApiModelProperty(value = "具体应用字段")
	private String detail;		// 具体应用字段

    @Excel(name="对账结果", width = 25)
    @TableField("bill_result")
	@ApiModelProperty(value = "对账结果")
	private Integer billResult;		// 对账结果

    @Excel(name="对账时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("bill_time")
	@ApiModelProperty(value = "对账时间")
	private Date billTime;		// 对账时间

	@Length(min=1, max=11, message="应收金额，单位：分长度必须介于 1 和 11 之间")
    @Excel(name="应收金额，单位：分", width = 25)
    @TableField("merchant_pay_fee")
	@ApiModelProperty(value = "应收金额，单位：分")
	private Integer merchantPayFee;		// 应收金额，单位：分

	@Length(min=1, max=11, message="实收金额，单位：分；实收金额 = 应收金额 - 优惠金额长度必须介于 1 和 11 之间")
    @Excel(name="实收金额，单位：分；实收金额 = 应收金额 - 优惠金额", width = 25)
    @TableField("merchant_real_fee")
	@ApiModelProperty(value = "实收金额，单位：分；实收金额 = 应收金额 - 优惠金额")
	private Integer merchantRealFee;		// 实收金额，单位：分；实收金额 = 应收金额 - 优惠金额

	@Length(min=1, max=11, message="优惠金额，单位：分长度必须介于 1 和 11 之间")
    @Excel(name="优惠金额，单位：分", width = 25)
    @TableField("merchant_discount_fee")
	@ApiModelProperty(value = "优惠金额，单位：分")
	private Integer merchantDiscountFee;		// 优惠金额，单位：分

	@NotNull(message="商户结算金额，单位：分不能为空")
    @Excel(name="商户结算金额，单位：分", width = 25)
    @TableField("merchant_settlement_fee")
	@ApiModelProperty(value = "商户结算金额，单位：分")
	private Double merchantSettlementFee;		// 商户结算金额，单位：分

	@NotNull(message="清算服务费，单位：分不能为空")
    @Excel(name="清算服务费，单位：分", width = 25)
    @TableField("clear_service_fee")
	@ApiModelProperty(value = "清算服务费，单位：分")
	private Double clearServiceFee;		// 清算服务费，单位：分

	@NotNull(message="清算服务费费率不能为空")
    @Excel(name="清算服务费费率", width = 25)
    @TableField("clear_service_fee_rate")
	@ApiModelProperty(value = "清算服务费费率")
	private Double clearServiceFeeRate;		// 清算服务费费率

	@NotNull(message="银行手续费，单位：分不能为空")
    @Excel(name="银行手续费，单位：分", width = 25)
    @TableField("bank_service_fee")
	@ApiModelProperty(value = "银行手续费，单位：分")
	private Double bankServiceFee;		// 银行手续费，单位：分

	@NotNull(message="银行手续费费率不能为空")
    @Excel(name="银行手续费费率", width = 25)
    @TableField("bank_service_fee_rate")
	@ApiModelProperty(value = "银行手续费费率")
	private Double bankServiceFeeRate;		// 银行手续费费率

	@Length(min=1, max=40, message="扣款交易流水号长度必须介于 1 和 40 之间")
    @Excel(name="扣款交易流水号", width = 25)
    @TableField("deduction_order_id")
	@ApiModelProperty(value = "扣款交易流水号")
	private String deductionOrderId;		// 扣款交易流水号

    @Excel(name="扣款状态，1-扣款成功、2-扣款失败", width = 25)
    @TableField("deduction_state")
	@ApiModelProperty(value = "扣款状态，1-扣款成功、2-扣款失败")
	private Integer deductionState;		// 扣款状态，1-扣款成功、2-扣款失败

    @Excel(name="扣款时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("deduction_time")
	@ApiModelProperty(value = "扣款时间")
	private Date deductionTime;		// 扣款时间

	@Length(min=0, max=11, message="扣款金额，单位：分长度必须介于 0 和 11 之间")
    @Excel(name="扣款金额，单位：分", width = 25)
    @TableField("deduction_fee")
	@ApiModelProperty(value = "扣款金额，单位：分")
	private Integer deductionFee;		// 扣款金额，单位：分

	@Length(min=0, max=11, message="银行优惠金额，单位：分长度必须介于 0 和 11 之间")
    @Excel(name="银行优惠金额，单位：分", width = 25)
    @TableField("bank_discount_fee")
	@ApiModelProperty(value = "银行优惠金额，单位：分")
	private Integer bankDiscountFee;		// 银行优惠金额，单位：分

	@Length(min=0, max=11, message="银行实扣金额，单位：分；银行实扣金额= 扣款金额 - 银行优惠金额长度必须介于 0 和 11 之间")
    @Excel(name="银行实扣金额，单位：分；银行实扣金额= 扣款金额 - 银行优惠金额", width = 25)
    @TableField("bank_real_deduction_fee")
	@ApiModelProperty(value = "银行实扣金额，单位：分；银行实扣金额= 扣款金额 - 银行优惠金额")
	private Integer bankRealDeductionFee;		// 银行实扣金额，单位：分；银行实扣金额= 扣款金额 - 银行优惠金额

	@Length(min=0, max=60, message="银行订单号长度必须介于 0 和 60 之间")
    @Excel(name="银行订单号", width = 25)
    @TableField("bank_deduction_order_id")
	@ApiModelProperty(value = "银行订单号")
	private String bankDeductionOrderId;		// 银行订单号

	@NotNull(message="创建时间不能为空")
    @Excel(name="创建时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("create_time")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;		// 创建时间

    @Excel(name="更新时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("update_time")
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;		// 更新时间

}