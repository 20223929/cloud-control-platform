package com.hgsoft.modules.settlement.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.DataEntity;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import com.hgsoft.modules.utils.ServiceTypeMapToNameUtil;
import com.hgsoft.modules.utils.ShowSelfRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 联网中心结算 Entity
 * @author 何志豪
 * @version 2021-05-08 04:55:35
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="联网中心结算", description="联网中心结算")
@TableName(value = "tb_center_settlement", resultMap = "BaseResultMap")
public class TbCenterSettlement extends DataEntity<TbCenterSettlement> implements Serializable, ShowSelfRole {

    private static final long serialVersionUID = 1L;

    public TbCenterSettlement() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	@Length(min=1, max=64, message="对账日长度必须介于 1 和 64 之间")
    @Excel(name="对账日", width = 25)
    @TableId("settlement_day")
	@ApiModelProperty(value = "对账日")
	private String settlementDay;		// 对账日

	/**
	 * 用于前端显示
	 **/
	@Excel(name = "拓展方", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "拓展方名称")
	private String merchantGroupIdName;        // 拓展方名称

	@Excel(name = "运营方", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "运营方名称")
	private String merchantIdName;        // 运营方名称

	@Excel(name = "场景", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "场景名称")
	private String siteIdName;        // 场景名称

	@Length(min=1, max=11, message="服务类型长度必须介于 1 和 11 之间")
    @TableField("service_type")
	@ApiModelProperty(value = "服务类型")
	private String serviceType;		// 服务类型

	@TableField(exist = false)
	@Excel(name="服务类型", width = 25)
	@ApiModelProperty(value = "服务类型名")
	private String serviceTypeStr;

	@Length(min=1, max=64, message="拓展方长度必须介于 1 和 64 之间")
    //@Excel(name="拓展方", width = 25)
    @TableId("merchant_group_id")
	@ApiModelProperty(value = "拓展方")
	private String merchantGroupId;		// 拓展方

	@Length(min=1, max=64, message="运营方长度必须介于 1 和 64 之间")
    //@Excel(name="运营方", width = 25)
    @TableId("merchant_id")
	@ApiModelProperty(value = "运营方")
	private String merchantId;		// 运营方

	@Length(min=1, max=64, message="场景长度必须介于 1 和 64 之间")
    //@Excel(name="场景", width = 25)
    @TableId("site_id")
	@ApiModelProperty(value = "场景")
	private String siteId;		// 场景

	//用户应付金额
	private Long payTotalAmount;

	@NotNull(message="客户应付总金额(单位元)不能为空")
	@Excel(name="客户应付总金额(单位元)", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "客户应付总金额(单位元)")
	private String payTotalAmountStr;		// 应付商户金额(单位元)

	private Long realTotalAmount;		// 应付商户金额(单位元)

	@NotNull(message="客户实付总金额（单位元）不能为空")
	@Excel(name="客户实付总金额(单位元)", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "客户实付总金额(单位元)")
	private String realTotalAmountStr;		// 应付商户金额(单位元)

	private BigDecimal merchantTotalAmount;		// 应付商户总金额

	@NotNull(message="应付商户总金额（单位元）不能为空")
	@Excel(name="应付商户总金额(单位元)", width = 25)
	@TableField(exist = false)
	@ApiModelProperty(value = "应付商户总金额(单位元)")
	private String merchantTotalAmountStr;		// 应付商户金额(单位元)

	@NotNull(message="清分服务费（单位元）不能为空")
    @TableField("clear_service_total_fee")
	@ApiModelProperty(value = "清分服务费(单位元）")
	private BigDecimal clearServiceTotalFee;		// 清分服务费(单位元）

	@NotNull(message="清分服务费（单位元）不能为空")
	@Excel(name="清分服务费(单位元）", width = 25)
	@TableField("clear_service_total_fee")
	@ApiModelProperty(value = "清分服务费(单位元）")
	private String clearServiceTotalFeeStr;		// 清分服务费(单位元）

	@NotNull(message="确认状态不能为空")
    //@Excel(name="确认状态", width = 25)
    @TableField("confirm_state")
	@ApiModelProperty(value = "确认状态")
	private Integer confirmState;		// 确认状态

	@Excel(name = "确认状态", width = 25)
	@ApiModelProperty(value = "确认状态")
	@TableField(exist = false)
	private String confirmStateStr;// 0-待确认 1-已确认 2-待补确认 3-已补确认

	@Length(min=1, max=64, message="确认人长度必须介于 1 和 64 之间")
    @Excel(name="确认人", width = 25)
    @TableField("confirm_man")
	@ApiModelProperty(value = "确认人")
	private String confirmMan;		// 确认人

	@NotNull(message="确认时间不能为空")
    @Excel(name="确认时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("confirm_time")
	@ApiModelProperty(value = "确认时间")
	private Date confirmTime;		// 确认时间

	@TableField(exist = false)
	private String beginClearDate;        // 开始 银行对账交易日期

	@TableField(exist = false)
	private String endClearDate;        // 结束 银行对账交易日期

	/**
	 * 根据商户信息控制用户权限
	 */
	@TableField(exist = false)
	private UserMerchantPermissionswrapper.UserMerchantParam userMerchantParam;

	private static final String[] confrimStateArray = new String[]{"填充","待确认", "已确认"};

	@Override
	public void triggerShowState(Map<String, String> merchantInfo) {
		String merchantGroupIdName = merchantInfo.get(getMerchantGroupId());
		String merchantIdName = merchantInfo.get(getMerchantId());
		String siteIdName = merchantInfo.get(getSiteId());
		setMerchantGroupIdName(merchantGroupIdName == null ? "未知拓展方" : merchantGroupIdName);
		setMerchantIdName(merchantIdName == null ? "未知运营方" : merchantIdName);
		setSiteIdName(siteIdName == null ? "未知场景" : siteIdName);

		setConfirmStateStr(confrimStateArray[getConfirmState()]);
		setServiceTypeStr(ServiceTypeMapToNameUtil.getServiceTypeName(Integer.valueOf(getServiceType())));

		setPayTotalAmountStr(movePointToLeft2(getPayTotalAmount()));

		BigDecimal _clearServiceTotalFee = getClearServiceTotalFee() == null ? BigDecimal.ZERO : getClearServiceTotalFee();
		setClearServiceTotalFeeStr(roundHalfUpScale2(_clearServiceTotalFee));

		Long realTotalAmount = getRealTotalAmount();
		setRealTotalAmountStr(movePointToLeft2(realTotalAmount));

		BigDecimal merchantTotalAmount = (new BigDecimal(realTotalAmount == null ? 0L : realTotalAmount)).setScale(6).subtract(_clearServiceTotalFee);
		setMerchantTotalAmount(merchantTotalAmount);
		setMerchantTotalAmountStr(roundHalfUpScale2(merchantTotalAmount));

		setPayTotalAmountStr(movePointToLeft2(getPayTotalAmount()));
	}

	/**
	 * 如果传入的列表为空，则返回null
	 * @param list
	 * @return
	 */
	public static TbCenterSettlement createSubTotal(List<TbCenterSettlement> list){
		if (list == null || list.isEmpty())
			return null;
		BigDecimal clearSum = BigDecimal.ZERO;
		BigDecimal merchantSum = BigDecimal.ZERO;
		Long paySum = 0L;
		Long realSum = 0L;
		for (TbCenterSettlement c : list) {
			BigDecimal clearServiceTotalFee = c.getClearServiceTotalFee();
			BigDecimal merchantTotalAmount = c.getMerchantTotalAmount();
			Long realTotalAmount = c.getRealTotalAmount();
			Long payTotalAmount = c.getPayTotalAmount();
			clearSum = clearSum.add(clearServiceTotalFee);
			merchantSum = merchantSum.add(merchantTotalAmount);
			paySum+=payTotalAmount;
			realSum+=realTotalAmount;
		}
		TbCenterSettlement subTotal = new TbCenterSettlement();
		subTotal.setMerchantGroupIdName("小计");
		subTotal.setClearServiceTotalFee(clearSum);
		subTotal.setClearServiceTotalFeeStr(subTotal.roundHalfUpScale2(clearSum));
		subTotal.setMerchantTotalAmount(merchantSum);
		subTotal.setMerchantTotalAmountStr(subTotal.roundHalfUpScale2(merchantSum));
		subTotal.setPayTotalAmount(paySum);
		subTotal.setPayTotalAmountStr(subTotal.movePointToLeft2(paySum));
		subTotal.setRealTotalAmount(realSum);
		subTotal.setRealTotalAmountStr(subTotal.movePointToLeft2(realSum));
		return subTotal;
	}
}