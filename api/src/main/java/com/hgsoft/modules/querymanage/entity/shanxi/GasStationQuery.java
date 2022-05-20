package com.hgsoft.modules.querymanage.entity.shanxi;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.ecip.auto.poi.excel.annotation.ExcelEntity;
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
 * 加油站流水查询 Entity
 * @author 吴鉴武
 * @version 2021-04-26 01:51:24
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="加油站流水查询", description="加油站流水查询")
@TableName(value = "TB_ONLINE_EEXIT", resultMap = "BaseResultMap")
public class GasStationQuery extends DataEntity<GasStationQuery> implements Serializable {

    private static final long serialVersionUID = 1L;

    public GasStationQuery() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	/** 查询参数 */
	private String[] timeScope;
	private Integer nodeLevel;
	private String searchId;
	private UserMerchantPermissionswrapper.UserMerchantParam userMerchantParam;

	@Length(min=1, max=64, message="车牌号码长度必须介于 1 和 64 之间")
    @Excel(name="车牌号码", width = 25)
    @TableField("vehicle_number")
	@ApiModelProperty(value = "车牌号码")
	private String vehicleNumber;		// 车牌号码

	@NotNull(message="车牌颜色不能为空")
    @TableField("vehicle_color")
	@ApiModelProperty(value = "车牌颜色")
	private Integer vehicleColor;		// 车牌颜色

	@Excel(name="车牌颜色", width = 25)
	private String vehicleColorDesc;

	@Length(min=1, max=64, message="ETC卡号长度必须介于 1 和 64 之间")
    @Excel(name="ETC卡号", width = 25)
    @TableField("etc_card_id")
	@ApiModelProperty(value = "ETC卡号")
	private String etcCardId;		// ETC卡号


    @TableField("sign_bank")
	@ApiModelProperty(value = "签约银行")
	private String signBank;		// 签约银行

	@Length(min=1, max=64, message="交易流水号长度必须介于 1 和 64 之间")
    @Excel(name="交易流水号", width = 35)
    @TableField("transaction_id")
	@ApiModelProperty(value = "交易流水号")
	private String transactionId;		// 交易流水号

	@NotNull(message="签约介质类型不能为空")
//    @Excel(name="签约介质类型", width = 25)
    @TableField("sign_medium_type")
	@ApiModelProperty(value = "签约介质类型")
	private Integer signMediumType;		// 签约介质类型

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

	@TableField(exist = false)
	@ApiModelProperty(value = "交易模式")
	private Integer modelType;		// 交易模式
	@Excel(name="交易模式", width = 25)
	private String modelTypeName;

	/**
	 * 油枪号
	 */
	private String equipmentId;

	@ExcelEntity
	@ApiModelProperty(value = "油品详情信息")
	private GasStationDetailInfo detailInfo;

	@NotNull(message="交易金额不能为空")
    @Excel(name="交易金额(元)", width = 25)
    @TableField("trade_fee")
	@ApiModelProperty(value = "交易金额")
	private BigDecimal tradeFee;		// 交易金额

	@NotNull(message="交易时间不能为空")
	@Excel(name="交易时间", width = 25)
	@TableField("trade_time")
	@ApiModelProperty(value = "交易时间")
	private String tradeTime;

	@NotNull(message="状态不能为空")
    @TableField("status")
	@ApiModelProperty(value = "状态")
	private Integer status;		// 状态

	@Excel(name="状态", width = 25)
	private String statusName;
	
	private BigDecimal payFee; //应收金额
	private BigDecimal discountFee; // 优惠金额
	private String bankDeductionOrderId; //银行订单号
	private String deductionOrderId; //订单号号
	private Integer vehicleType;
	private String msg;

}