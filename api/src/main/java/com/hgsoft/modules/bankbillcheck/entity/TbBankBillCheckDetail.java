package com.hgsoft.modules.bankbillcheck.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
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
 * 银行对账明细表 Entity
 * @author 何志豪
 * @version 2021-04-19 03:07:10
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="银行对账明细表", description="银行对账明细表")
@TableName(value = "tb_bank_bill_check_detail", resultMap = "BaseResultMap")
public class TbBankBillCheckDetail extends DataEntity<TbBankBillCheckDetail> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TbBankBillCheckDetail() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	@Length(min=1, max=10, message="对账交易日期长度必须介于 1 和 10 之间")
    @Excel(name="对账交易日期", width = 25)
    @TableId("trans_date")
	@ApiModelProperty(value = "对账交易日期")
	private String transDate;		// 对账交易日期

	@Length(min=1, max=60, message="一级运营商长度必须介于 1 和 60 之间")
    @Excel(name="一级运营商", width = 25)
    @TableId("merchant_group_id")
	@ApiModelProperty(value = "一级运营商")
	private String merchantGroupId;		// 一级运营商

	@Length(min=1, max=60, message="二级运营商长度必须介于 1 和 60 之间")
    @Excel(name="二级运营商", width = 25)
    @TableId("merchant_id")
	@ApiModelProperty(value = "二级运营商")
	private String merchantId;		// 二级运营商

	@Length(min=1, max=60, message="三级运营商长度必须介于 1 和 60 之间")
    @Excel(name="三级运营商", width = 25)
    @TableId("site_id")
	@ApiModelProperty(value = "三级运营商")
	private String siteId;		// 三级运营商

	@Length(min=1, max=60, message="四级设备编号长度必须介于 1 和 60 之间")
    @Excel(name="四级设备编号", width = 25)
    @TableId("equipment_id")
	@ApiModelProperty(value = "四级设备编号")
	private String equipmentId;		// 四级设备编号

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

	@Length(min=1, max=10, message="本系统流水总数长度必须介于 1 和 10 之间")
    @Excel(name="本系统流水总数", width = 25)
    @TableField("platform_total_count")
	@ApiModelProperty(value = "本系统流水总数")
	private String platformTotalCount;		// 本系统流水总数

	@Length(min=1, max=10, message="本系统流水总金额（分）长度必须介于 1 和 10 之间")
    @Excel(name="本系统流水总金额(分）", width = 25)
    @TableField("platform_total_amount")
	@ApiModelProperty(value = "本系统流水总金额(分）")
	private String platformTotalAmount;		// 本系统流水总金额(分）

	@Length(min=1, max=10, message="银行流水总数长度必须介于 1 和 10 之间")
    @Excel(name="银行流水总数", width = 25)
    @TableField("bank_total_count")
	@ApiModelProperty(value = "银行流水总数")
	private String bankTotalCount;		// 银行流水总数

	@Length(min=1, max=10, message="银行流水总金额（分）长度必须介于 1 和 10 之间")
    @Excel(name="银行流水总金额(分)", width = 25)
    @TableField("bank_total_amount")
	@ApiModelProperty(value = "银行流水总金额(分)")
	private String bankTotalAmount;		// 银行流水总金额(分)

	@Length(min=1, max=10, message="流水总数差额长度必须介于 1 和 10 之间")
    @Excel(name="流水总数差额", width = 25)
    @TableField("diff_total_count")
	@ApiModelProperty(value = "流水总数差额")
	private String diffTotalCount;		// 流水总数差额

	@Length(min=1, max=10, message="流水总金额差额（分）长度必须介于 1 和 10 之间")
    @Excel(name="流水总金额差额(分)", width = 25)
    @TableField("diff_total_amount")
	@ApiModelProperty(value = "流水总金额差额(分)")
	private String diffTotalAmount;		// 流水总金额差额(分)

	@NotNull(message="创建时间不能为空")
    @Excel(name="创建时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("create_time")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;		// 创建时间

}