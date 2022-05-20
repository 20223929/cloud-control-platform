package com.hgsoft.modules.settlement.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
/**
 * 银行预结算明细 Entity
 * @author 何志豪
 * @version 2021-05-08 05:37:06
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="银行预结算明细", description="银行预结算明细")
@TableName(value = "tb_bank_settlement_detail", resultMap = "BaseResultMap")
public class TbBankSettlementDetail extends DataEntity<TbBankSettlementDetail> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TbBankSettlementDetail() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	@Length(min=1, max=64, message="流水类型长度必须介于 1 和 64 之间")
    @Excel(name="流水类型", width = 25)
    @TableField("eexit_type")
	@ApiModelProperty(value = "流水类型")
	private String eexitType;		// 流水类型

	@Length(min=1, max=64, message="银行交易日期长度必须介于 1 和 64 之间")
    @Excel(name="银行交易时间", width = 25)
    @TableField("bank_trans_date")
	@ApiModelProperty(value = "银行交易日期")
	private String bankTransDate;		// 银行交易日期

	@Length(min=1, max=64, message="流水号长度必须介于 1 和 64 之间")
    @Excel(name="交易流水号", width = 25)
    @TableId("transaction_id")
	@ApiModelProperty(value = "流水号")
	private String transactionId;		// 流水号

	@Length(min=1, max=64, message="银行交易检索号长度必须介于 1 和 64 之间")
    @Excel(name="银行交易检索号", width = 25)
    @TableField("deduction_order_id")
	@ApiModelProperty(value = "银行交易检索号")
	private String deductionOrderId;		// 银行交易检索号

	@Length(min=1, max=64, message="车牌号码长度必须介于 1 和 64 之间")
    @Excel(name="车牌号码", width = 25)
    @TableField("vehicle_plate")
	@ApiModelProperty(value = "车牌号码")
	private String vehiclePlate;		// 车牌号码

	@NotNull(message="车牌颜色不能为空")
//    @Excel(name="车牌颜色", width = 25)
    @TableField("vehicle_color")
	@ApiModelProperty(value = "车牌颜色")
	private Integer vehicleColor;		// 车牌颜色

	@Length(min=1, max=64, message="拓展方长度必须介于 1 和 64 之间")
    @TableField("merchant_group_id")
	@ApiModelProperty(value = "拓展方")
	private String merchantGroupId;		// 拓展方

	@Excel(name="拓展方", width = 25)
	private String merchantGroupName;

	@Length(min=1, max=64, message="运营方长度必须介于 1 和 64 之间")
    @TableField("merchant_id")
	@ApiModelProperty(value = "运营方")
	private String merchantId;		// 运营方

	@Excel(name="运营方", width = 25)
	private String merchantName;

	@Length(min=1, max=64, message="场景长度必须介于 1 和 64 之间")
    @TableField("site_id")
	@ApiModelProperty(value = "场景")
	private String siteId;		// 场景

	@Excel(name="场景", width = 25)
	private String siteName;

	@NotNull(message="服务类型不能为空")
    @TableField("service_type")
	@ApiModelProperty(value = "服务类型")
	private Integer serviceType;		// 服务类型

	@Excel(name="服务类型", width = 25)
	private String serviceTypeDesc;

	@NotNull(message="客户实付金额不能为空")
    @Excel(name="客户实付金额(元)", width = 25)
    @TableField("merchant_real_fee")
	@ApiModelProperty(value = "客户实付金额")
	private BigDecimal merchantRealFee;		// 客户实付金额

	@NotNull(message="优惠金额不能为空")
    @Excel(name="优惠金额(元)", width = 25)
    @TableField("merchant_discount_fee")
	@ApiModelProperty(value = "优惠金额")
	private BigDecimal merchantDiscountFee;		// 优惠金额

}