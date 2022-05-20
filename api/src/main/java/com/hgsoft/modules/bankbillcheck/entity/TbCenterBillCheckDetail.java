package com.hgsoft.modules.bankbillcheck.entity;

import org.hibernate.validator.constraints.Length;

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
 * 联网中心对账核对明细表 Entity
 * @author 何志豪
 * @version 2021-04-21 02:34:34
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="联网中心对账核对明细表", description="联网中心对账核对明细表")
@TableName(value = "tb_center_bill_check_detail", resultMap = "BaseResultMap")
public class TbCenterBillCheckDetail extends DataEntity<TbCenterBillCheckDetail> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TbCenterBillCheckDetail() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	@Length(min=1, max=10, message="银行对账交易日期长度必须介于 1 和 10 之间")
    @Excel(name="银行对账交易日期", width = 25)
    @TableId("trans_date")
	@ApiModelProperty(value = "银行对账交易日期")
	private String transDate;		// 银行对账交易日期

	@Length(min=1, max=60, message="拓展方长度必须介于 1 和 60 之间")
    @Excel(name="拓展方", width = 25)
    @TableId("merchant_group_id")
	@ApiModelProperty(value = "拓展方")
	private String merchantGroupId;		// 拓展方

	@Length(min=1, max=60, message="运营方长度必须介于 1 和 60 之间")
    @Excel(name="运营方", width = 25)
    @TableId("merchant_id")
	@ApiModelProperty(value = "运营方")
	private String merchantId;		// 运营方

	@Length(min=1, max=60, message="场景长度必须介于 1 和 60 之间")
    @Excel(name="场景", width = 25)
    @TableId("site_id")
	@ApiModelProperty(value = "场景")
	private String siteId;		// 场景

	@Length(min=1, max=60, message="设备编号长度必须介于 1 和 60 之间")
    @Excel(name="设备编号", width = 25)
    @TableId("equipment_id")
	@ApiModelProperty(value = "设备编号")
	private String equipmentId;		// 设备编号

	@Length(min=1, max=11, message="用户确认流水数量长度必须介于 1 和 11 之间")
    @Excel(name="用户确认流水数量", width = 25)
    @TableField("confirm_total_count")
	@ApiModelProperty(value = "用户确认流水数量")
	private Long confirmTotalCount;		// 用户确认流水数量

	@Length(min=1, max=11, message="实际流水数量长度必须介于 1 和 11 之间")
	@Excel(name="实际流水数量", width = 25)
	@TableField("real_total_count")
	@ApiModelProperty(value = "实际流水数量")
	private Long realTotalCount;		// 实际确认流水数量

	@Length(min=1, max=11, message="补确认流水数量长度必须介于 1 和 11 之间")
	@Excel(name="补确认流水数量", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "补确认流水数量")
	private Long fillTotalCount;		// 实际确认流水数量

	@Length(min=1, max=11, message="补确认流水金额（分）长度必须介于 1 和 11 之间")
	@Excel(name="补确认流水金额(分)", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "补确认流水金额(分)")
	private Long fillTotalAmount;		// 实际确认流水金额(分)

	@Length(min=1, max=11, message="用户确认流水金额（分）长度必须介于 1 和 11 之间")
	@Excel(name="用户确认流水金额(分)", width = 25)
	@TableField("confirm_total_amount")
	@ApiModelProperty(value = "用户确认流水金额(分)")
	private Long confirmTotalAmount;		// 用户确认流水金额(分)

	@Length(min=1, max=11, message="实际流水金额（分）长度必须介于 1 和 11 之间")
	@Excel(name="实际流水金额(分)", width = 25)
	@TableField("real_total_amount")
	@ApiModelProperty(value = "实际流水金额(分)")
	private Long realTotalAmount;		// 实际确认流水金额(分)

	@Length(min=1, max=11, message="补确认流水金额（分）长度必须介于 1 和 11 之间")
	@Excel(name="补确认流水金额(分)", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "补确认流水金额(分)")
	private String fillTotalAmountStr;		// 实际确认流水金额(元)

	@Length(min=1, max=11, message="用户确认流水金额（分）长度必须介于 1 和 11 之间")
	@Excel(name="用户确认流水金额(分)", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "用户确认流水金额(分)")
	private String confirmTotalAmountStr;		// 用户确认流水金额(元)

	@Length(min=1, max=11, message="实际流水金额（分）长度必须介于 1 和 11 之间")
	@Excel(name="实际流水金额(分)", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "实际流水金额(分)")
	private String realTotalAmountStr;		// 实际确认流水金额(元)

	@TableField(exist = false)
	@Excel(name = "拓展方", width = 25)
	@ApiModelProperty(value = "一级运营商名")
	private String merchantGroupIdName;        // 一级运营商

	@Excel(name = "运营方", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "二级运营商名")
	private String merchantIdName;        // 二级运营商

	@Excel(name = "场景", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "三级运营商名")
	private String siteIdName;        // 三级运营商
}