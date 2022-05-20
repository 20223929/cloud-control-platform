package com.hgsoft.modules.querymanage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.Date;
/**
 * 停车场流水查询 Entity
 * @author Andy
 * @version 2021-04-16 22:34:19
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ETC交易流水表", description="停车场流水查询")
@TableName(resultMap = "BaseResultMap")
public class ParkEexit extends DataEntity<ParkEexit> implements Serializable {

    private static final long serialVersionUID = 1L;

    public ParkEexit() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }
	@TableField(exist = false)
	@ApiModelProperty(value = "运营方节点")
	private Integer nodeLevel;		// 运营方节点
	private String[] timeScope;
	private String searchId;
	private UserMerchantPermissionswrapper.UserMerchantParam userMerchantParam;

	@Length(min=1, max=20, message="系统编号长度必须介于 1 和 20 之间")
	@ApiModelProperty(value = "系统编号")
	private String sysId;		// 系统编号

	@Length(min=1, max=20, message="车牌号码长度必须介于 1 和 20 之间")
	@Excel(name="车牌号码", width = 25)
	@TableField("vehicle_plate")
	@ApiModelProperty(value = "车牌号码")
	private String vehiclePlate;		// 车牌号码

	@NotNull(message="车牌颜色不能为空")
	@TableField("vehicle_color")
	@ApiModelProperty(value = "车牌颜色")
	private Integer vehicleColor;		// 车牌颜色

	@Excel(name="车牌颜色", width = 25)
	private String vehicleColorName;

	@Length(min=1, max=20, message="ETC卡号长度必须介于 1 和 20 之间")
    @Excel(name="ETC卡号", width = 25)
    @TableField("etc_card_id")
	@ApiModelProperty(value = "ETC卡号")
	private String etcCardId;		// ETC卡号

	@NotNull(message="交易前金额不能为空")
    @TableField("pre_balance")
	@ApiModelProperty(value = "交易前金额")
	private String preBalance;		// 交易前金额

	@NotNull(message="交易后金额不能为空")
    @TableField("post_balance")
	@ApiModelProperty(value = "交易后金额")
	private String postBalance;		// 交易后金额

	@Length(min=1, max=20, message="PSAM脱机交易流水号长度必须介于 1 和 20 之间")
    @TableField("terminal_trans_no")
	@ApiModelProperty(value = "PSAM脱机交易流水号")
	private String terminalTransNo;		// PSAM脱机交易流水号

	@Length(min=1, max=8, message="IC卡交易序号长度必须介于 1 和 8 之间")
    @TableField("trans_no")
	@ApiModelProperty(value = "IC卡交易序号")
	private String transNo;		// IC卡交易序号

	@Length(min=1, max=8, message="tac 码长度必须介于 1 和 8 之间")
    @TableField("tac")
	@ApiModelProperty(value = "tac 码")
	private String tac;		// tac 码

	@Length(min=1, max=2, message="交易类型长度必须介于 1 和 2 之间")
    @TableField("trade_type")
	@ApiModelProperty(value = "交易类型")
	private String tradeType;		// 交易类型

	@Length(min=1, max=16, message="终端机编号长度必须介于 1 和 16 之间")
    @TableField("terminal_no")
	@ApiModelProperty(value = "终端机编号")
	private String terminalNo;		// 终端机编号

	@Length(min=1, max=40, message="交易流水号长度必须介于 1 和 40 之间")
    @Excel(name="交易流水号", width = 40)
    @TableField("transaction_id")
	@ApiModelProperty(value = "交易流水号")
	private String transactionId;		// 交易流水号

	@TableField(exist = false)
	@Excel(name="银行订单号", width = 25)
	@ApiModelProperty(value = "订单号")
	private String orderId;		// 订单号

	@TableField(exist = false)
	@ApiModelProperty(value = "交易模式")
	private Integer modelType;		// 交易模式
	@Excel(name="交易模式", width = 25)
	private String modelTypeName;

	@Excel(name="拓展方", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "拓展方")
	private String merchantGroupIdName;

	@Excel(name="运营方", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "运营方")
	private String merchantIdName;

	@Excel(name="场景", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "场景")
	private String siteIdName;

	@Length(min=1, max=60, message="一级运营商长度必须介于 1 和 60 之间")
    @TableField("merchant_group_id")
	@ApiModelProperty(value = "拓展方")
	private String merchantGroupId;		// 一级运营商

	@Length(min=0, max=60, message="二级运营商长度必须介于 0 和 60 之间")
    @TableField("merchant_id")
	@ApiModelProperty(value = "运营方")
	private String merchantId;		// 二级运营商

	@Length(min=1, max=60, message="三级运营商长度必须介于 1 和 60 之间")
    @TableField("site_id")
	@ApiModelProperty(value = "场景")
	private String siteId;		// 三级运营商

	@NotNull(message="入场时间不能为空")
    @Excel(name="入场时间", width = 25)
    @TableField("en_time")
	@ApiModelProperty(value = "入场时间")
	private String enTime;		// 入场时间

	@NotNull(message="出场时间不能为空")
    @Excel(name="出场时间", width = 25)
    @TableField("ex_time")
	@ApiModelProperty(value = "出场时间")
	private String exTime;		// 出场时间

	@Excel(name = "停放时长",width = 25)
	private String parkHours;

	@Length(min=1, max=11, message="实收金额长度必须介于 1 和 11 之间")
	@Excel(name="交易金额(元)", width = 25)
	@TableField("merchant_real_fee")
	@ApiModelProperty(value = "实收金额")
	private String merchantRealFee;		// 实收金额

	@NotNull(message="交易时间不能为空")
    @Excel(name="交易时间", width = 25)
    @TableField("trans_time")
	@ApiModelProperty(value = "交易时间")
	private String transTime;		// 交易时间

	@TableField(exist = false)
	@ApiModelProperty(value = "状态")
	private Integer status;		// 状态
	@Excel(name="状态", width = 25)
	private String statusName;

	@NotNull(message="车型不能为空")
    @TableField("vehicle_type")
	@ApiModelProperty(value = "车型")
	private Integer vehicleType;		// 车型

	@Length(min=1, max=11, message="应收金额长度必须介于 1 和 11 之间")
    @TableField("merchant_pay_fee")
	@ApiModelProperty(value = "应收金额")
	private String merchantPayFee;		// 应收金额

	@Length(min=1, max=11, message="优惠金额长度必须介于 1 和 11 之间")
    @TableField("merchant_discount_fee")
	@ApiModelProperty(value = "优惠金额")
	private String merchantDiscountFee;		// 优惠金额

	@TableField(exist = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date beginTransTime;		// 开始 交易时间

	@TableField(exist = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTransTime;		// 结束 交易时间

	private String equipmentId;//四级编号

	private String deductionOrderId; //订单号

	private String signBank;//签约银行

	private String msg;//备注
}