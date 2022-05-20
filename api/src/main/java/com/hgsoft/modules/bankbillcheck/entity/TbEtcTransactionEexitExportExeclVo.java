package com.hgsoft.modules.bankbillcheck.entity;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.modules.querymanage.enums.VehicleColor;
import com.hgsoft.modules.utils.ServiceTypeMapToNameUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * ETC交易流水表 Entity
 * @author 何志豪
 * @version 2021-06-20 23:10:29
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbEtcTransactionEexitExportExeclVo implements Serializable {

    private static final long serialVersionUID = 1L;

	@NotNull(message="系统编号不能为空")
    @Excel(name="系统编号", width = 25)
	private Long sysId;		// 系统编号

	private Integer sendClearFlag;		// 发送清分标识，0-待发送、1-已发送
	@Excel(name="发送清分标识", width = 25)
	private String sendClearFlagStr;

	private Integer clearCheckResult;		// 清分校验结果，1-正常、2-异常
	@Excel(name="清分校验结果", width = 25)
	private String clearCheckResultStr;

	@Length(min=0, max=4000, message="清分校验结果描述长度必须介于 0 和 4000 之间")
    @Excel(name="清分校验结果描述", width = 25)
	private String clearCheckResultMsg;		// 清分校验结果描述

	@Length(min=0, max=32, message="修复人长度必须介于 0 和 32 之间")
    @Excel(name="修复人", width = 25)
	private String repairMan;		// 修复人

    @Excel(name="修复时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date repairTime;		// 修复时间

    @Excel(name="修复次数", width = 25)
	private Integer repairCount;		// 修复次数

	private Integer repairState;		// 修复状态，0-待修复、1-修复完成、2-放弃修复
	@Excel(name="修复状态", width = 25)
	private String repairStateStr;

	private Integer clearResult;		// 清分结果，0-正常记账、1-争议支付、2-争议拒付
	@Excel(name="清分结果", width = 25)
	private String clearResultStr;

	@Length(min=0, max=10, message="清分日，格式：yyyy-MM-dd长度必须介于 0 和 10 之间")
    @Excel(name="清分日", width = 25)
	private String clearDate;		// 清分日，格式：yyyy-MM-dd

    @Excel(name="清分结果接收时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date clearResultRecvTime;		// 清分结果接收时间

	@Length(min=0, max=50, message="结算确认人长度必须介于 0 和 50 之间")
    @Excel(name="结算确认人", width = 25)
	private String settlementConfirmMan;		// 结算确认人

    @Excel(name="结算确认时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date settlementConfirmTime;		// 结算确认时间

	private Integer settlementConfirmState;		// 结算确认状态，1-待确认、2-已确认
	@Excel(name="结算确认状态", width = 25)
	private String settlementConfirmStateStr;

	@Length(min=1, max=20, message="ETC卡号长度必须介于 1 和 20 之间")
    @Excel(name="ETC卡号", width = 25)
	private String etcCardId;		// ETC卡号

	@NotNull(message="交易前金额不能为空")
    @Excel(name="交易前金额(分)", width = 25)
	private Long preBalance;		// 交易前金额

	@NotNull(message="交易后金额不能为空")
    @Excel(name="交易后金额(分)", width = 25)
	private Long postBalance;		// 交易后金额

	@Length(min=1, max=20, message="PSAM脱机交易流水号长度必须介于 1 和 20 之间")
    @Excel(name="PSAM脱机交易流水号", width = 25)
	private String terminalTransNo;		// PSAM脱机交易流水号

	@NotNull(message="IC卡交易序号不能为空")
    @Excel(name="IC卡交易序号", width = 25)
	private Long transNo;		// IC卡交易序号

	@Length(min=1, max=8, message="tac 码长度必须介于 1 和 8 之间")
    @Excel(name="TAC码", width = 25)
	private String tac;		// tac 码

	private String tradeType;		// 交易类型，06-传统交易、09-复合交易
	@Excel(name="交易类型", width = 25)
	private String tradeTypeStr;

	@Length(min=1, max=12, message="终端机编号长度必须介于 1 和 12 之间")
    @Excel(name="终端机编号", width = 25)
	private String terminalNo;		// 终端机编号

	@Length(min=1, max=2, message="算法标识长度必须介于 1 和 2 之间")
    @Excel(name="算法标识", width = 25)
	private String algorithmIdentify;		// 算法标识

	@Length(min=0, max=4000, message="具体应用字段长度必须介于 0 和 4000 之间")
    @Excel(name="具体应用字段", width = 25)
	private String detail;		// 具体应用字段

	@NotNull(message="tac码产生来源，1-脱机、2-联机不能为空")
    @Excel(name="tac码产生来源", width = 25)
	private Integer tacProduceSource;		// tac码产生来源，1-脱机、2-联机

	@Length(min=1, max=40, message="交易流水号长度必须介于 1 和 40 之间")
    @Excel(name="交易流水号", width = 25)
	private String transactionId;		// 交易流水号

	private String merchantGroupId;		// 一级运营商

	private String merchantId;		// 二级运营商

	private String siteId;		// 三级运营商

    @Excel(name="四级设备编号", width = 25)
	private String equipmentId;		// 四级设备编号

	@NotNull(message="入场时间不能为空")
    @Excel(name="入场时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date enTime;		// 入场时间

	@NotNull(message="出场时间不能为空")
    @Excel(name="出场时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date exTime;		// 出场时间

	@NotNull(message="交易时间不能为空")
    @Excel(name="交易时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date transTime;		// 交易时间

	@Length(min=1, max=20, message="车牌号码长度必须介于 1 和 20 之间")
    @Excel(name="车牌号码", width = 25)
	private String vehiclePlate;		// 车牌号码

	private Integer vehicleColor;		// 车牌颜色
	@NotNull(message="车型不能为空")
    @Excel(name="车型", width = 25)
	private Integer vehicleType;		// 车型

	private Integer merchantPayFee;		// 应收金额
	private Integer merchantRealFee;	// 实收金额
	private Integer merchantDiscountFee;// 优惠金额

	@NotNull(message="创建时间不能为空")
    @Excel(name="创建时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date createTime;		// 创建时间

	@NotNull(message="服务类型不能为空")
	private Integer serviceType;		// 服务类型

	@NotNull(message="商户结算金额不能为空")
    @Excel(name="商户结算金额", width = 25)
	private Double merchantSettlementFee;		// 商户结算金额

	@NotNull(message="清算服务费不能为空")
    @Excel(name="清算服务费", width = 25)
	private Double clearServiceFee;		// 清算服务费

	@NotNull(message="清算服务费费率不能为空")
    @Excel(name="清算服务费费率", width = 25)
	private Double clearServiceFeeRate;		// 清算服务费费率

	@NotNull(message="银行手续费不能为空")
    @Excel(name="银行手续费", width = 25)
	private Double bankServiceFee;		// 银行手续费

	@NotNull(message="银行手续费费率不能为空")
    @Excel(name="银行手续费费率", width = 25)
	private Double bankServiceFeeRate;		// 银行手续费费率

    @Excel(name="更新时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date updateTime;		// 更新时间

	@Length(min=0, max=60, message="设备编号长度必须介于 0 和 60 之间")
    @Excel(name="设备编号", width = 25)
	private String deviceId;		// 设备编号

	private static final String movePointToLeft2(Integer fee) {
		if (fee == null)
			return "0";
		BigDecimal _temp = new BigDecimal(fee);
		return _temp.movePointLeft(2).toString();
	}

	@Excel(name="服务类型", width = 25)
	private String serviceTypeStr;

	@Excel(name="一级运营商", width = 25)
	private String merchantGroupIdStr;

	@Excel(name="二级运营商", width = 25)
	private String merchantIdStr;

	@Excel(name="三级运营商", width = 25)
	private String siteIdStr;

	@Excel(name="车牌颜色", width = 25)
	private String vehicleColorStr;

	@Excel(name="应收金额", width = 25)
	private String merchantPayFeeStr;

	@Excel(name="实收金额", width = 25)
	private String merchantRealFeeStr;

	@Excel(name="优惠金额", width = 25)
	private String merchantDiscountFeeStr;

	public void innerTransform( Map<String, String> merchantInfo){
		//这里开始要对r的字段进行修饰
		TbEtcTransactionEexitExportExeclVo r = this;
		r.setServiceTypeStr(ServiceTypeMapToNameUtil.getServiceTypeName(r.getServiceType()));
		r.setMerchantGroupIdStr(merchantInfo.get(r.getMerchantGroupId()));
		r.setMerchantIdStr(merchantInfo.get(r.getMerchantId()));
		r.setSiteIdStr(merchantInfo.get(r.getSiteId()));
		r.setVehicleColorStr(VehicleColor.getColorDescByColorNumber(r.getVehicleColor()));
		r.setMerchantPayFeeStr(movePointToLeft2(r.getMerchantPayFee()));
		r.setMerchantRealFeeStr(movePointToLeft2(r.getMerchantRealFee()));
		r.setMerchantDiscountFeeStr(movePointToLeft2(r.getMerchantDiscountFee()));
		Integer sendClearFlag = r.getSendClearFlag();
		if(sendClearFlag!=null){
			switch (sendClearFlag){
				case 0:r.setSendClearFlagStr("未发送清分");break;
				case 1:r.setSendClearFlagStr("已发送清分");break;
			}
		}
		Integer clearCheckResult = r.getClearCheckResult();
		if(clearCheckResult!=null){
			switch (clearCheckResult){
				case 1:r.setClearCheckResultStr("清分校验正常");break;
				case 2:r.setClearCheckResultStr("清分校验异常");break;
			}
		}
		Integer repairState = r.getRepairState();
		if(repairState!=null){
			switch (repairState){
				case 0:r.setRepairStateStr("未修复");break;
				case 1:r.setRepairStateStr("已修复");break;
				case 2:r.setRepairStateStr("放弃修复");break;
			}
		}
		Integer clearResult = r.getClearResult();
		if(clearResult!=null){
			switch (clearResult){
				case 0:r.setClearCheckResultStr("正常支付");
				case 1:r.setClearCheckResultStr("争议支付");
				case 2:r.setClearCheckResultStr("争议拒付");
			}
		}
		Integer settlementConfirmState = r.getSettlementConfirmState();
		if(settlementConfirmState!=null){
			switch (settlementConfirmState){
				case 1:r.setSettlementConfirmStateStr("未确认");break;
				case 2:r.setSettlementConfirmStateStr("已确认");break;
			}
		}
		String tradeType = r.getTradeType();
		if(tradeType!=null){
			switch (tradeType){
				case "06":r.setTradeTypeStr("传统交易");break;
				case "09":r.setTradeTypeStr("复合交易");break;
			}
		}
	}

	public static TbEtcTransactionEexitExportExeclVo transform(TbEtcTransactionEexit eexit, Map<String, String> merchantInfo) {
		TbEtcTransactionEexitExportExeclVo r = new TbEtcTransactionEexitExportExeclVo();
		BeanUtil.copyProperties(eexit, r, false);
		//这里开始要对r的字段进行修饰
		r.setServiceTypeStr(ServiceTypeMapToNameUtil.getServiceTypeName(r.getServiceType()));
		r.setMerchantGroupIdStr(merchantInfo.get(r.getMerchantGroupId()));
		r.setMerchantIdStr(merchantInfo.get(r.getMerchantId()));
		r.setSiteIdStr(merchantInfo.get(r.getSiteId()));
		r.setVehicleColorStr(VehicleColor.getColorDescByColorNumber(r.getVehicleColor()));
		r.setMerchantPayFeeStr(movePointToLeft2(r.getMerchantPayFee()));
		r.setMerchantRealFeeStr(movePointToLeft2(r.getMerchantRealFee()));
		r.setMerchantDiscountFeeStr(movePointToLeft2(r.getMerchantDiscountFee()));
		Integer sendClearFlag = r.getSendClearFlag();
		if(sendClearFlag!=null){
			switch (sendClearFlag){
				case 0:r.setSendClearFlagStr("未发送清分");break;
				case 1:r.setSendClearFlagStr("已发送清分");break;
			}
		}
		Integer clearCheckResult = r.getClearCheckResult();
		if(clearCheckResult!=null){
			switch (clearCheckResult){
				case 1:r.setClearCheckResultStr("清分校验正常");break;
				case 2:r.setClearCheckResultStr("清分校验异常");break;
			}
		}
		Integer repairState = r.getRepairState();
		if(repairState!=null){
			switch (repairState){
				case 0:r.setRepairStateStr("未修复");break;
				case 1:r.setRepairStateStr("已修复");break;
				case 2:r.setRepairStateStr("放弃修复");break;
			}
		}
		Integer clearResult = r.getClearResult();
		if(clearResult!=null){
			switch (clearResult){
				case 0:r.setClearCheckResultStr("正常支付");
				case 1:r.setClearCheckResultStr("争议支付");
				case 2:r.setClearCheckResultStr("争议拒付");
			}
		}
		Integer settlementConfirmState = r.getSettlementConfirmState();
		if(settlementConfirmState!=null){
			switch (settlementConfirmState){
				case 1:r.setSettlementConfirmStateStr("未确认");break;
				case 2:r.setSettlementConfirmStateStr("已确认");break;
			}
		}
		String tradeType = r.getTradeType();
		if(tradeType!=null){
			switch (tradeType){
				case "06":r.setTradeTypeStr("传统交易");break;
				case "09":r.setTradeTypeStr("复合交易");break;
			}
		}
		return r;
	}
}