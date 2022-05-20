package com.hgsoft.modules.report.entity.shanxi;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 联网中心扣款报表 Entity
 * @author 吴鉴武
 * @version 2021-04-22 23:14:25
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="联网中心扣款报表", description="联网中心扣款报表")
@TableName(value = "tb_etc_pay_report", resultMap = "BaseResultMap")
public class EtcPayReport extends DataEntity<EtcPayReport> implements Serializable {

    private static final long serialVersionUID = 1L;

    public EtcPayReport() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	/** 查询参数 */
	private String[] timeScope;
	private Integer nodeLevel;
	private String searchId;
	private Integer value;
	private UserMerchantPermissionswrapper.UserMerchantParam userMerchantParam;

	@Length(min=1, max=64, message="拓展方编号长度必须介于 1 和 64 之间")
//    @Excel(name="拓展方编号", width = 25)
    @TableField("merchant_group_id")
	@ApiModelProperty(value = "拓展方编号")
	private String merchantGroupId;		// 拓展方编号

	@Length(min=1, max=64, message="拓展方长度必须介于 1 和 64 之间")
    @Excel(name="拓展方", width = 25)
    @TableField("merchant_group_name")
	@ApiModelProperty(value = "拓展方")
	private String merchantGroupName;		// 拓展方

	@Length(min=1, max=64, message="运营方编号长度必须介于 1 和 64 之间")
//    @Excel(name="运营方编号", width = 25)
    @TableField("merchant_id")
	@ApiModelProperty(value = "运营方编号")
	private String merchantId;		// 运营方编号

	@Length(min=1, max=64, message="运营方长度必须介于 1 和 64 之间")
    @Excel(name="运营方", width = 25)
    @TableField("merchantName")
	@ApiModelProperty(value = "运营方")
	private String merchantName;		// 运营方

	@Length(min=1, max=64, message="场景编号长度必须介于 1 和 64 之间")
//    @Excel(name="场景编号", width = 25)
    @TableField("site_id")
	@ApiModelProperty(value = "场景编号")
	private String siteId;		// 场景编号

	@Length(min=1, max=64, message="场景长度必须介于 1 和 64 之间")
    @Excel(name="场景", width = 25)
    @TableField("site_name")
	@ApiModelProperty(value = "场景")
	private String siteName;		// 场景

	@Length(min=1, max=64, message="交易日期长度必须介于 1 和 64 之间")
    @Excel(name="交易日期", width = 25)
    @TableField("trade_day")
	@ApiModelProperty(value = "交易日期")
	private String tradeDay;		// 交易日期

	@NotNull(message="交易流水数不能为空")
    @Excel(name="交易流水数", width = 25)
    @TableField("total_count")
	@ApiModelProperty(value = "交易流水数")
	private Long totalCount;		// 交易流水数

	@NotNull(message="交易金额不能为空")
    @Excel(name="交易金额", width = 25, exportConvert = true)
    @TableField("total_fee")
	@ApiModelProperty(value = "交易金额")
	private BigDecimal totalFee;		// 交易金额
	public BigDecimal convertgetTotalFee(){
		return this.totalFee.divide(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
	}

	@NotNull(message="已扣款流水数不能为空")
    @Excel(name="已扣款流水数", width = 25)
    @TableField("pay_count")
	@ApiModelProperty(value = "已扣款流水数")
	private Long payCount;		// 已扣款流水数

	@NotNull(message="已扣款金额不能为空")
    @Excel(name="已扣款金额", width = 25, exportConvert = true)
    @TableField("pay_fee")
	@ApiModelProperty(value = "已扣款金额")
	private BigDecimal payFee;		// 已扣款金额
	public BigDecimal convertgetPayFee(){
		return this.payFee.divide(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
	}

	@NotNull(message="未扣款流水数不能为空")
    @Excel(name="未扣款流水数", width = 25)
    @TableField("unpay_count")
	@ApiModelProperty(value = "未扣款流水数")
	private Long unpayCount;		// 未扣款流水数

	@NotNull(message="未扣款金额不能为空")
    @Excel(name="未扣款金额", width = 25, exportConvert = true)
    @TableField("unpay_fee")
	@ApiModelProperty(value = "未扣款金额")
	private BigDecimal unpayFee;		// 未扣款金额
	public BigDecimal convertgetUnpayFee(){
		return this.unpayFee.divide(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
	}

}