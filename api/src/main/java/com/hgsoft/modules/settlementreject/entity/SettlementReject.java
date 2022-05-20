package com.hgsoft.modules.settlementreject.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 预支抵扣详情表 Entity
 * @author 郑裕强
 * @version 2022-05-08 03:40:27
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="预支抵扣详情表", description="预支抵扣详情表表")
@TableName(value = "tb_settlement_reject", resultMap = "BaseResultMap")
public class SettlementReject extends DataEntity<SettlementReject> implements Serializable {

    private static final long serialVersionUID = 1L;

    public SettlementReject() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

	@Length(min=1, max=64, message="主键长度必须介于 1 和 64 之间")
    @TableId("sys_id")
	@ApiModelProperty(value = "主键")
	private String sysId;		// 主键

	@NotNull(message="资金结算信息表ID不能为空")
    @TableField("settlement_id")
	@ApiModelProperty(value = "资金结算信息表ID")
	private Integer settlementId;		// 资金结算信息表ID

	@NotNull(message="是否是粤通卡，0-否；1-是不能为空")
    @TableField("is_gdetc")
	@ApiModelProperty(value = "是否是粤通卡，0-否；1-是")
	private Integer isGdetc;		// 是否是粤通卡，0-否；1-是

	@NotNull(message="入场时间不能为空")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("en_time")
	@ApiModelProperty(value = "入场时间")
	private Date enTime;		// 入场时间

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("ex_time")
	@ApiModelProperty(value = "出场时间")
	private Date exTime;		// 出场时间

	@Length(min=1, max=64, message="服务方编码长度必须介于 1 和 64 之间")
	@TableField("site_id")
	@ApiModelProperty(value = "服务方编码")
	private String siteId;		// 服务方编码

	@Length(min=1, max=64, message="服务方名称长度必须介于 1 和 64 之间")
	@Excel(name="服务方", width = 25)
	@TableField("site_name")
	@ApiModelProperty(value = "服务方名称")
	private String siteName;		// 服务方名称

	@NotNull(message="交易时间不能为空")
    @Excel(name="交易时间", width = 25, format = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("trans_time")
	@ApiModelProperty(value = "交易时间")
	private Date transTime;		// 交易时间

	@Length(min=1, max=64, message="车牌号码长度必须介于 1 和 64 之间")
	@Excel(name="车牌号码", width = 25)
	@TableField("vehicle_plate")
	@ApiModelProperty(value = "车牌号码")
	private String vehiclePlate;		// 车牌号码

	@NotNull(message="车牌颜色不能为空")
	@TableField("vehicle_color")
	@ApiModelProperty(value = "车牌颜色")
	private Integer vehicleColor;		// 车牌颜色


	@Excel(name="车牌颜色", width = 25)
	private String vehicleColorTxt;		// 车牌颜色

	@Length(min=1, max=64, message="ETC卡号长度必须介于 1 和 64 之间")
	@Excel(name="ETC卡号", width = 25)
	@TableField("card_id")
	@ApiModelProperty(value = "ETC卡号")
	private String cardId;		// ETC卡号

	@Length(min=1, max=64, message="OBU合同系列号长度必须介于 1 和 64 之间")
	@Excel(name="OBU合同系列号", width = 25)
	@TableField("obu_id")
	@ApiModelProperty(value = "OBU合同系列号")
	private String obuId;		// OBU合同系列号

	@Length(min=1, max=64, message="交易流水号长度必须介于 1 和 64 之间")
    @Excel(name="交易流水号", width = 30)
    @TableField("transaction_id")
	@ApiModelProperty(value = "交易流水号")
	private String transactionId;		// 交易流水号

	@Length(min=1, max=64, message="运营方编码长度必须介于 1 和 64 之间")
    @TableField("merchant_id")
	@ApiModelProperty(value = "运营方编码")
	private String merchantId;		// 运营方编码

	@Length(min=1, max=64, message="运营方名称长度必须介于 1 和 64 之间")
    @TableField("merchant_name")
	@ApiModelProperty(value = "运营方名称")
	private String merchantName;		// 运营方名称

	@NotNull(message="车型不能为空")
    @TableField("vehicle_type")
	@ApiModelProperty(value = "车型")
	private Integer vehicleType;		// 车型

	@NotNull(message="交易金额不能为空")
    @Excel(name="交易金额(元)", width = 25)
    @TableField("fee")
	@ApiModelProperty(value = "交易金额")
	private BigDecimal fee;		// 交易金额


	@Excel(name="交易时间", width = 25)
	private String transDate;		// 交易时间

	@Length(min=1, max=255, message="流水状态，清分结果，多个用|分割，0-正常记账 1-TAC验证未通过 2-重复交易 3-用户状态变化 4-无效服务类型 5-逾期超过设定值 6-交易数据域错（暂不启用）7-超过最大交易限额 8-卡号不存在 9-卡状态不匹配 10-卡超过有效期 11-不允许的交易（暂不启用）12-卡片CSN不匹配（暂不启用） 13-测试交易 14-卡账不符（仅用于储值卡）（暂不启用） 15-无效卡类型 16-车道对时错误 17-ETC通行记录与过车图像未匹配 18-过车识别图像校核有误 19-路径拟合数据的支撑证据有误长度必须介于 1 和 255 之间")
    @TableField("trans_sta")
	@ApiModelProperty(value = "流水状态，清分结果，多个用|分割，0-正常记账 1-TAC验证未通过 2-重复交易 3-用户状态变化 4-无效服务类型 5-逾期超过设定值 6-交易数据域错（暂不启用）7-超过最大交易限额 8-卡号不存在 9-卡状态不匹配 10-卡超过有效期 11-不允许的交易（暂不启用）12-卡片CSN不匹配（暂不启用） 13-测试交易 14-卡账不符（仅用于储值卡）（暂不启用） 15-无效卡类型 16-车道对时错误 17-ETC通行记录与过车图像未匹配 18-过车识别图像校核有误 19-路径拟合数据的支撑证据有误")
	private String transSta;		// 流水状态，清分结果，多个用|分割，0-正常记账 1-TAC验证未通过 2-重复交易 3-用户状态变化 4-无效服务类型 5-逾期超过设定值 6-交易数据域错（暂不启用）7-超过最大交易限额 8-卡号不存在 9-卡状态不匹配 10-卡超过有效期 11-不允许的交易（暂不启用）12-卡片CSN不匹配（暂不启用） 13-测试交易 14-卡账不符（仅用于储值卡）（暂不启用） 15-无效卡类型 16-车道对时错误 17-ETC通行记录与过车图像未匹配 18-过车识别图像校核有误 19-路径拟合数据的支撑证据有误

	@Excel(name="流水状态", width = 25)
	private String transStaTxt;

	@NotNull(message="创建时间不能为空")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("createtime")
	@ApiModelProperty(value = "创建时间")
	private Date createtime;		// 创建时间

}