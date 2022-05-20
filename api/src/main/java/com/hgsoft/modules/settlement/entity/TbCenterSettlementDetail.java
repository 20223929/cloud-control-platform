package com.hgsoft.modules.settlement.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;

import com.hgsoft.ecip.framework.shiro.ShiroUser;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.ArrayList;


import java.io.Serializable;
/**
 * 联网中心结算明细 Entity
 * @author 何志豪
 * @version 2021-05-08 05:00:37
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="联网中心结算明细", description="联网中心结算明细")
@TableName(value = "tb_center_settlement_detail", resultMap = "BaseResultMap")
public class TbCenterSettlementDetail extends DataEntity<TbCenterSettlementDetail> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TbCenterSettlementDetail() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	@NotNull(message="服务类型不能为空")
    @Excel(name="服务类型", width = 25)
    @TableField("service_type")
	@ApiModelProperty(value = "服务类型")
	private Integer serviceType;		// 服务类型

	@Length(min=1, max=64, message="拓展方长度必须介于 1 和 64 之间")
    @Excel(name="拓展方", width = 25)
    @TableField("merchant_group_id")
	@ApiModelProperty(value = "拓展方")
	private String merchantGroupId;		// 拓展方

	@Length(min=1, max=64, message="运营方长度必须介于 1 和 64 之间")
    @Excel(name="运营方", width = 25)
    @TableField("merchant_id")
	@ApiModelProperty(value = "运营方")
	private String merchantId;		// 运营方

	@Length(min=1, max=64, message="场景长度必须介于 1 和 64 之间")
    @Excel(name="场景", width = 25)
    @TableField("site_id")
	@ApiModelProperty(value = "场景")
	private String siteId;		// 场景

	@Length(min=1, max=64, message="流水号长度必须介于 1 和 64 之间")
    @Excel(name="流水号", width = 25)
    @TableId("transaction_id")
	@ApiModelProperty(value = "流水号")
	private String transactionId;		// 流水号

	@Length(min=1, max=64, message="ETC卡号长度必须介于 1 和 64 之间")
    @Excel(name="ETC卡号", width = 25)
    @TableField("etc_card_id")
	@ApiModelProperty(value = "ETC卡号")
	private String etcCardId;		// ETC卡号

	@Length(min=1, max=64, message="车牌号码长度必须介于 1 和 64 之间")
    @Excel(name="车牌号码", width = 25)
    @TableField("vehicle_plate")
	@ApiModelProperty(value = "车牌号码")
	private String vehiclePlate;		// 车牌号码

	@NotNull(message="车牌颜色不能为空")
    @Excel(name="车牌颜色", width = 25)
    @TableField("vehicle_color")
	@ApiModelProperty(value = "车牌颜色")
	private Integer vehicleColor;		// 车牌颜色

	@NotNull(message="交易时间不能为空")
    @Excel(name="交易时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("trans_time")
	@ApiModelProperty(value = "交易时间")
	private Date transTime;		// 交易时间

	@NotNull(message="实收金额不能为空")
    @Excel(name="实收金额", width = 25)
    @TableField("merchant_real_fee")
	@ApiModelProperty(value = "实收金额")
	private BigDecimal merchantRealFee;		// 实收金额

	@NotNull(message="应收金额不能为空")
    @Excel(name="应收金额", width = 25)
    @TableField("merchant_pay_fee")
	@ApiModelProperty(value = "应收金额")
	private BigDecimal merchantPayFee;		// 应收金额

	@Length(min=1, max=64, message="清分日期长度必须介于 1 和 64 之间")
    @Excel(name="清分日期", width = 25)
    @TableField("clear_date")
	@ApiModelProperty(value = "清分日期")
	private String clearDate;		// 清分日期

}