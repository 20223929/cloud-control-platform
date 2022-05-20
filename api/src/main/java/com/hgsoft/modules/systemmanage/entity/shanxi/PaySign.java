package com.hgsoft.modules.systemmanage.entity.shanxi;

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
/**
 * 二次签约名单管理 Entity
 * @author 吴鉴武
 * @version 2021-04-23 02:57:56
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="二次签约名单管理", description="二次签约名单管理")
@TableName(value = "pay_sign", resultMap = "BaseResultMap")
public class PaySign extends DataEntity<PaySign> implements Serializable {

    private static final long serialVersionUID = 1L;

    public PaySign() {
    	super();
		this.setIdType(IDTYPE_IDWORKER);
    }

    private String[] timeScope;// 签约时间范围

	@NotNull(message="系统编号不能为空")
    @TableId("sys_id")
	@ApiModelProperty(value = "系统编号")
	private Long sysId;		// 系统编号

	@Length(min=1, max=60, message="银行签约流水号长度必须介于 1 和 60 之间")
    @Excel(name="银行签约流水号", width = 25,orderNum = "1")
    @TableField("sign_agreement_no")
	@ApiModelProperty(value = "银行签约流水号")
	private String signAgreementNo;		// 银行签约流水号

	@Length(min=1, max=64, message="用户ID长度必须介于 1 和 64 之间")
    @TableField("user_id")
	@ApiModelProperty(value = "用户ID")
	private String userId;		// 用户ID

	@Length(min=1, max=64, message="微信用户ID长度必须介于 1 和 64 之间")
    @TableField("wechat_user_id")
	@ApiModelProperty(value = "微信用户ID")
	private String wechatUserId;		// 微信用户ID

	@Length(min=1, max=64, message="车牌号码长度必须介于 1 和 64 之间")
    @Excel(name="车牌号码", width = 25,orderNum = "3")
    @TableField("vehicle_number")
	@ApiModelProperty(value = "车牌号码")
	private String vehicleNumber;		// 车牌号码

	@NotNull(message="车牌颜色不能为空")
    @TableField("vehicle_color")
	@ApiModelProperty(value = "车牌颜色")
	private Integer vehicleColor;		// 车牌颜色
	@Excel(name="车牌颜色", width = 25,orderNum = "4")
	private String vehicleColorDesc;

	@NotNull(message="车型不能为空")
    @TableField("vehicle_class")
	@ApiModelProperty(value = "车型")
	private Integer vehicleClass;		// 车型
	@Excel(name="车型", width = 25,orderNum = "5")
	private String vehicleClassDesc;

	@Length(min=1, max=64, message="ETC卡号长度必须介于 1 和 64 之间")
    @Excel(name="ETC卡号", width = 25,orderNum = "6")
    @TableField("etc_card_id")
	@ApiModelProperty(value = "ETC卡号")
	private String etcCardId;		// ETC卡号

	@Length(min=1, max=64, message="OBU合同序列号长度必须介于 1 和 64 之间")
    @Excel(name="OBU合同序列号", width = 25,orderNum = "7")
    @TableField("obu_id")
	@ApiModelProperty(value = "OBU合同序列号")
	private String obuId;		// OBU合同序列号

	@Length(min=1, max=64, message="发行机构长度必须介于 1 和 64 之间")
    @TableField("issuer_bank_name")
	@ApiModelProperty(value = "发行机构")
	private String issuerBankName;		// 发行机构

	@Length(min=1, max=64, message="发卡行名称长度必须介于 1 和 64 之间")
    @TableField("issuer_card_bank_name")
	@ApiModelProperty(value = "发卡行名称")
	private String issuerCardBankName;		// 发卡行名称

	@Length(min=1, max=64, message="用户名称长度必须介于 1 和 64 之间")
    @Excel(name="用户名称", width = 25,orderNum = "9")
    @TableField("user_name")
	@ApiModelProperty(value = "用户名称")
	private String userName;		// 用户名称

	@Length(min=1, max=64, message="用户身份证长度必须介于 1 和 64 之间")
    @Excel(name="用户身份证", width = 25,orderNum = "10")
    @TableField("id_no")
	@ApiModelProperty(value = "用户身份证")
	private String idNo;		// 用户身份证

	@Length(min=1, max=64, message="发行手机号长度必须介于 1 和 64 之间")
    @Excel(name="发行手机号", width = 25,orderNum = "11")
    @TableField("issuer_phone")
	@ApiModelProperty(value = "发行手机号")
	private String issuerPhone;		// 发行手机号

	@Length(min=1, max=64, message="绑定手机号长度必须介于 1 和 64 之间")
    @Excel(name="绑定手机号", width = 25,orderNum = "12")
    @TableField("bind_phone")
	@ApiModelProperty(value = "绑定手机号")
	private String bindPhone;		// 绑定手机号

	@NotNull(message="状态不能为空")
    @TableField("status")
	@ApiModelProperty(value = "状态")
	private Integer status;		// 状态
	@Excel(name="状态", width = 25,orderNum = "14")
	private String statusDesc;

	@NotNull(message="签约结果不能为空")
    @TableField("sign_state")
	@ApiModelProperty(value = "签约结果")
	private Integer signState;		// 签约结果
	@Excel(name="签约结果", width = 25,orderNum = "13")
	private String signStateDesc;

	@NotNull(message="工行卡标识不能为空")
    @TableField("bank_card_type")
	@ApiModelProperty(value = "工行卡标识")
	private Integer bankCardType;		// 工行卡标识
	@Excel(name="签约卡标识", width = 25,orderNum = "8")
	private String bankCardTypeDesc;

	@Length(min=1, max=50, message="签约机构长度必须介于 1 和 50 之间")
    @Excel(name="签约机构", width = 25,orderNum = "2")
    @TableField("bank_name")
	@ApiModelProperty(value = "签约机构")
	private String bankName;		// 签约机构

    @Excel(name="签约时间", width = 25,orderNum = "0")
    @TableField("sign_time")
	@ApiModelProperty(value = "签约时间")
	private String signTime;		// 签约时间

	@TableField(exist = false)
	private String beginSignTime;		// 开始 签约时间

	@TableField(exist = false)
	private String endSignTime;		// 结束 签约时间
}