package com.hgsoft.modules.bankbillcheck.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.Date;
/**
 * ETC交易流水表 Entity
 * @author 何志豪
 * @version 2021-06-20 23:10:29
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ETC交易流水表", description="ETC交易流水表")
@TableName(value = "tb_etc_transaction_eexit", resultMap = "BaseResultMap")
public class TbEtcTransactionEexit extends DataEntity<TbEtcTransactionEexit> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TbEtcTransactionEexit() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	@NotNull(message="系统编号不能为空")
    @Excel(name="系统编号", width = 25)
    @TableId("sys_id")
	@ApiModelProperty(value = "系统编号")
	private Long sysId;		// 系统编号

	@NotNull(message="发送清分标识，0-待发送、1-已发送不能为空")
    @Excel(name="发送清分标识，0-待发送、1-已发送", width = 25)
    @TableField("send_clear_flag")
	@ApiModelProperty(value = "发送清分标识，0-待发送、1-已发送")
	private Integer sendClearFlag;		// 发送清分标识，0-待发送、1-已发送

    @Excel(name="清分校验结果，1-正常、2-异常", width = 25)
    @TableField("clear_check_result")
	@ApiModelProperty(value = "清分校验结果，1-正常、2-异常")
	private Integer clearCheckResult;		// 清分校验结果，1-正常、2-异常

	@Length(min=0, max=4000, message="清分校验结果描述长度必须介于 0 和 4000 之间")
    @Excel(name="清分校验结果描述", width = 25)
    @TableField("clear_check_result_msg")
	@ApiModelProperty(value = "清分校验结果描述")
	private String clearCheckResultMsg;		// 清分校验结果描述

	@Length(min=0, max=32, message="修复人长度必须介于 0 和 32 之间")
    @Excel(name="修复人", width = 25)
    @TableField("repair_man")
	@ApiModelProperty(value = "修复人")
	private String repairMan;		// 修复人

    @Excel(name="修复时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("repair_time")
	@ApiModelProperty(value = "修复时间")
	private Date repairTime;		// 修复时间

    @Excel(name="修复次数", width = 25)
    @TableField("repair_count")
	@ApiModelProperty(value = "修复次数")
	private Integer repairCount;		// 修复次数

    @Excel(name="修复状态，0-待修复、1-修复完成、2-放弃修复", width = 25)
    @TableField("repair_state")
	@ApiModelProperty(value = "修复状态，0-待修复、1-修复完成、2-放弃修复")
	private Integer repairState;		// 修复状态，0-待修复、1-修复完成、2-放弃修复

    @Excel(name="清分结果，0-正常记账、1-争议支付、2-争议拒付", width = 25)
    @TableField("clear_result")
	@ApiModelProperty(value = "清分结果，0-正常记账、1-争议支付、2-争议拒付")
	private Integer clearResult;		// 清分结果，0-正常记账、1-争议支付、2-争议拒付

	@Length(min=0, max=10, message="清分日，格式：yyyy-MM-dd长度必须介于 0 和 10 之间")
    @Excel(name="清分日，格式：yyyy-MM-dd", width = 25)
    @TableField("clear_date")
	@ApiModelProperty(value = "清分日，格式：yyyy-MM-dd")
	private String clearDate;		// 清分日，格式：yyyy-MM-dd

    @Excel(name="清分结果接收时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("clear_result_recv_time")
	@ApiModelProperty(value = "清分结果接收时间")
	private Date clearResultRecvTime;		// 清分结果接收时间

	@Length(min=0, max=50, message="结算确认人长度必须介于 0 和 50 之间")
    @Excel(name="结算确认人", width = 25)
    @TableField("settlement_confirm_man")
	@ApiModelProperty(value = "结算确认人")
	private String settlementConfirmMan;		// 结算确认人

    @Excel(name="结算确认时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("settlement_confirm_time")
	@ApiModelProperty(value = "结算确认时间")
	private Date settlementConfirmTime;		// 结算确认时间

	@NotNull(message="结算确认状态，1-待确认、2-已确认不能为空")
    @Excel(name="结算确认状态，1-待确认、2-已确认", width = 25)
    @TableField("settlement_confirm_state")
	@ApiModelProperty(value = "结算确认状态，1-待确认、2-已确认")
	private Integer settlementConfirmState;		// 结算确认状态，1-待确认、2-已确认

	@Length(min=1, max=20, message="ETC卡号长度必须介于 1 和 20 之间")
    @Excel(name="ETC卡号", width = 25)
    @TableField("etc_card_id")
	@ApiModelProperty(value = "ETC卡号")
	private String etcCardId;		// ETC卡号

	@NotNull(message="交易前金额不能为空")
    @Excel(name="交易前金额", width = 25)
    @TableField("pre_balance")
	@ApiModelProperty(value = "交易前金额")
	private Long preBalance;		// 交易前金额

	@NotNull(message="交易后金额不能为空")
    @Excel(name="交易后金额", width = 25)
    @TableField("post_balance")
	@ApiModelProperty(value = "交易后金额")
	private Long postBalance;		// 交易后金额

	@Length(min=1, max=20, message="PSAM脱机交易流水号长度必须介于 1 和 20 之间")
    @Excel(name="PSAM脱机交易流水号", width = 25)
    @TableField("terminal_trans_no")
	@ApiModelProperty(value = "PSAM脱机交易流水号")
	private String terminalTransNo;		// PSAM脱机交易流水号

	@NotNull(message="IC卡交易序号不能为空")
    @Excel(name="IC卡交易序号", width = 25)
    @TableField("trans_no")
	@ApiModelProperty(value = "IC卡交易序号")
	private Long transNo;		// IC卡交易序号

	@Length(min=1, max=8, message="tac 码长度必须介于 1 和 8 之间")
    @Excel(name="tac 码", width = 25)
    @TableField("tac")
	@ApiModelProperty(value = "tac 码")
	private String tac;		// tac 码

	@Length(min=1, max=2, message="交易类型，06-传统交易、09-复合交易长度必须介于 1 和 2 之间")
    @Excel(name="交易类型，06-传统交易、09-复合交易", width = 25)
    @TableField("trade_type")
	@ApiModelProperty(value = "交易类型，06-传统交易、09-复合交易")
	private String tradeType;		// 交易类型，06-传统交易、09-复合交易

	@Length(min=1, max=12, message="终端机编号长度必须介于 1 和 12 之间")
    @Excel(name="终端机编号", width = 25)
    @TableField("terminal_no")
	@ApiModelProperty(value = "终端机编号")
	private String terminalNo;		// 终端机编号

	@Length(min=1, max=2, message="算法标识长度必须介于 1 和 2 之间")
    @Excel(name="算法标识", width = 25)
    @TableField("algorithm_identify")
	@ApiModelProperty(value = "算法标识")
	private String algorithmIdentify;		// 算法标识

	@Length(min=0, max=4000, message="具体应用字段长度必须介于 0 和 4000 之间")
    @Excel(name="具体应用字段", width = 25)
    @TableField("detail")
	@ApiModelProperty(value = "具体应用字段")
	private String detail;		// 具体应用字段

	@NotNull(message="tac码产生来源，1-脱机、2-联机不能为空")
    @Excel(name="tac码产生来源，1-脱机、2-联机", width = 25)
    @TableField("tac_produce_source")
	@ApiModelProperty(value = "tac码产生来源，1-脱机、2-联机")
	private Integer tacProduceSource;		// tac码产生来源，1-脱机、2-联机

	@Length(min=1, max=40, message="交易流水号长度必须介于 1 和 40 之间")
    @Excel(name="交易流水号", width = 25)
    @TableField("transaction_id")
	@ApiModelProperty(value = "交易流水号")
	private String transactionId;		// 交易流水号

	@Length(min=1, max=60, message="一级运营商长度必须介于 1 和 60 之间")
    @Excel(name="一级运营商", width = 25)
    @TableField("merchant_group_id")
	@ApiModelProperty(value = "一级运营商")
	private String merchantGroupId;		// 一级运营商

	@Length(min=1, max=60, message="二级运营商长度必须介于 1 和 60 之间")
    @Excel(name="二级运营商", width = 25)
    @TableField("merchant_id")
	@ApiModelProperty(value = "二级运营商")
	private String merchantId;		// 二级运营商

	@Length(min=1, max=60, message="三级运营商长度必须介于 1 和 60 之间")
    @Excel(name="三级运营商", width = 25)
    @TableField("site_id")
	@ApiModelProperty(value = "三级运营商")
	private String siteId;		// 三级运营商

	@Length(min=1, max=60, message="四级设备编号长度必须介于 1 和 60 之间")
    @Excel(name="四级设备编号", width = 25)
    @TableField("equipment_id")
	@ApiModelProperty(value = "四级设备编号")
	private String equipmentId;		// 四级设备编号

	@NotNull(message="入场时间不能为空")
    @Excel(name="入场时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("en_time")
	@ApiModelProperty(value = "入场时间")
	private Date enTime;		// 入场时间

	@NotNull(message="出场时间不能为空")
    @Excel(name="出场时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("ex_time")
	@ApiModelProperty(value = "出场时间")
	private Date exTime;		// 出场时间

	@NotNull(message="交易时间不能为空")
    @Excel(name="交易时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("trans_time")
	@ApiModelProperty(value = "交易时间")
	private Date transTime;		// 交易时间

	@Length(min=1, max=20, message="车牌号码长度必须介于 1 和 20 之间")
    @Excel(name="车牌号码", width = 25)
    @TableField("vehicle_plate")
	@ApiModelProperty(value = "车牌号码")
	private String vehiclePlate;		// 车牌号码

	@NotNull(message="车牌颜色不能为空")
    @Excel(name="车牌颜色", width = 25)
    @TableField("vehicle_color")
	@ApiModelProperty(value = "车牌颜色")
	private Integer vehicleColor;		// 车牌颜色

	@NotNull(message="车型不能为空")
    @Excel(name="车型", width = 25)
    @TableField("vehicle_type")
	@ApiModelProperty(value = "车型")
	private Integer vehicleType;		// 车型

	@Length(min=1, max=11, message="应收金额长度必须介于 1 和 11 之间")
    @Excel(name="应收金额", width = 25)
    @TableField("merchant_pay_fee")
	@ApiModelProperty(value = "应收金额")
	private String merchantPayFee;		// 应收金额

	@Length(min=1, max=11, message="实收金额长度必须介于 1 和 11 之间")
    @Excel(name="实收金额", width = 25)
    @TableField("merchant_real_fee")
	@ApiModelProperty(value = "实收金额")
	private String merchantRealFee;		// 实收金额

	@Length(min=1, max=11, message="优惠金额长度必须介于 1 和 11 之间")
    @Excel(name="优惠金额", width = 25)
    @TableField("merchant_discount_fee")
	@ApiModelProperty(value = "优惠金额")
	private String merchantDiscountFee;		// 优惠金额

	@NotNull(message="创建时间不能为空")
    @Excel(name="创建时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("create_time")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;		// 创建时间

	@NotNull(message="服务类型不能为空")
    @Excel(name="服务类型", width = 25)
    @TableField("service_type")
	@ApiModelProperty(value = "服务类型")
	private Integer serviceType;		// 服务类型

	@NotNull(message="商户结算金额不能为空")
    @Excel(name="商户结算金额", width = 25)
    @TableField("merchant_settlement_fee")
	@ApiModelProperty(value = "商户结算金额")
	private Double merchantSettlementFee;		// 商户结算金额

	@NotNull(message="清算服务费不能为空")
    @Excel(name="清算服务费", width = 25)
    @TableField("clear_service_fee")
	@ApiModelProperty(value = "清算服务费")
	private Double clearServiceFee;		// 清算服务费

	@NotNull(message="清算服务费费率不能为空")
    @Excel(name="清算服务费费率", width = 25)
    @TableField("clear_service_fee_rate")
	@ApiModelProperty(value = "清算服务费费率")
	private Double clearServiceFeeRate;		// 清算服务费费率

	@NotNull(message="银行手续费不能为空")
    @Excel(name="银行手续费", width = 25)
    @TableField("bank_service_fee")
	@ApiModelProperty(value = "银行手续费")
	private Double bankServiceFee;		// 银行手续费

	@NotNull(message="银行手续费费率不能为空")
    @Excel(name="银行手续费费率", width = 25)
    @TableField("bank_service_fee_rate")
	@ApiModelProperty(value = "银行手续费费率")
	private Double bankServiceFeeRate;		// 银行手续费费率

    @Excel(name="更新时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("update_time")
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;		// 更新时间

	@Length(min=0, max=60, message="设备编号长度必须介于 0 和 60 之间")
    @Excel(name="设备编号", width = 25)
    @TableField("device_id")
	@ApiModelProperty(value = "设备编号")
	private String deviceId;		// 设备编号

    @Excel(name="ETC交易流水文件处理记录表 tb_etc_eexit_deal_recordc的 id", width = 25)
    @TableField("eexit_file_deal_record_id")
	@ApiModelProperty(value = "ETC交易流水文件处理记录表 tb_etc_eexit_deal_recordc的 id")
	private Long eexitFileDealRecordId;		// ETC交易流水文件处理记录表 tb_etc_eexit_deal_recordc的 id

}