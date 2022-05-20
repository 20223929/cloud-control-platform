package com.hgsoft.modules.settlement.entity;

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
 * 银行预结算 Entity
 * @author 何志豪
 * @version 2021-05-08 05:23:48
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="银行预结算", description="银行预结算")
@TableName(value = "tb_bank_settlement", resultMap = "BaseResultMap")
public class TbBankSettlement extends DataEntity<TbBankSettlement> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TbBankSettlement() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

    private String [] timeScope;
    private UserMerchantPermissionswrapper.UserMerchantParam userMerchantParam;
    private Integer nodeLevel;
    private String searchId;

	@Length(min=1, max=64, message="银行交易日期长度必须介于 1 和 64 之间")
    @Excel(name="银行交易日期", width = 25)
    @TableId("bank_trans_date")
	@ApiModelProperty(value = "银行交易日期")
	private String bankTransDate;		// 银行交易日期

	@Length(min=1, max=64, message="运营方长度必须介于 1 和 64 之间")
    @TableId("merchant_id")
	@ApiModelProperty(value = "运营方")
	private String merchantId;		// 运营方

	@Excel(name="运营方", width = 25)
	private String merchantName; // 运营方名称

	@NotNull(message="服务类型不能为空")
    @TableField("service_type")
	@ApiModelProperty(value = "服务类型")
	private Integer serviceType;		// 服务类型

	@Excel(name="服务类型", width = 25)
	private String serviceTypeDesc;

	@NotNull(message="应付商户总金额（元）不能为空")
    @Excel(name="应付商户总金额(元)", width = 25)
    @TableField("merchant_recv_fee")
	@ApiModelProperty(value = "应付商户总金额(元)")
	private BigDecimal merchantRecvFee;		// 应付商户总金额(元)

	@NotNull(message="支付流水总数不能为空")
    @Excel(name="支付流水总数", width = 25)
    @TableField("deduction_total_count")
	@ApiModelProperty(value = "支付流水总数")
	private Integer deductionTotalCount;		// 支付流水总数

//	@Excel(name="客户实付总金额（元）", width = 25)
//	private BigDecimal realFee;

	@NotNull(message="客户应付总金额（元）不能为空")
    @Excel(name="客户实付总金额（元）", width = 25)
    @TableField("deduction_total_fee")
	@ApiModelProperty(value = "客户应付总金额（元）")
	private BigDecimal deductionTotalFee;		// 客户应付总金额（元）

	@NotNull(message="银行优惠总金额（元）不能为空")
    @Excel(name="优惠总金额(元)", width = 25)
    @TableField("discount_total_fee")
	@ApiModelProperty(value = "银行优惠总金额（元）")
	private BigDecimal discountTotalFee;		// 银行优惠总金额（元）

	@Length(min=1, max=64, message="退费流水总数长度必须介于 1 和 64 之间")
    @Excel(name="退费流水总数", width = 25)
    @TableField("refund_total_count")
	@ApiModelProperty(value = "退费流水总数")
	private String refundTotalCount;		// 退费流水总数

	@NotNull(message="退费流水总金额不能为空")
    @Excel(name="退费流水总金额(元)", width = 25)
    @TableField("refund_total_fee")
	@ApiModelProperty(value = "退费流水总金额")
	private BigDecimal refundTotalFee;		// 退费流水总金额

	@NotNull(message="银行手续费（元）不能为空")
    @Excel(name="银行手续费(元)", width = 25)
    @TableField("bank_service_total_fee")
	@ApiModelProperty(value = "银行手续费(元)")
	private BigDecimal bankServiceTotalFee;		// 银行手续费(元)

	private Integer transactionType;//流水类型

	private String trxPlace;

}