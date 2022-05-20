package com.hgsoft.modules.complaintrefund.entity;

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
import java.math.BigDecimal;
import java.util.Date;
/**
 * 退费申请表 Entity
 * @author wjw
 * @version 2021-05-08 02:06:58
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="退费申请表", description="退费申请表")
@TableName(value = "tb_refund_apply", resultMap = "BaseResultMap")
public class RefundApply extends DataEntity<RefundApply> implements Serializable {

    private static final long serialVersionUID = 1L;

    public RefundApply() {
        super();
        this.setIdType(IDTYPE_IDWORKER);
    }

    @NotNull(message="系统编号不能为空")
    @Excel(name="系统编号", width = 25)
    @TableId("sys_id")
    @ApiModelProperty(value = "系统编号")
    private Long sysId;		// 系统编号

    @NotNull(message="退费申请渠道，1-前台系统确认生成、2-后台系统确认生成、3-业务员录入、4-小程序申请不能为空")
    @Excel(name="退费申请渠道，1-前台系统确认生成、2-后台系统确认生成、3-业务员录入、4-小程序申请", width = 25)
    @TableField("refund_apply_channel")
    @ApiModelProperty(value = "退费申请渠道，1-前台系统确认生成、2-后台系统确认生成、3-业务员录入、4-小程序申请")
    private Integer refundApplyChannel;		// 退费申请渠道，1-前台系统确认生成、2-后台系统确认生成、3-业务员录入、4-小程序申请

    @Length(min=1, max=50, message="退费申请单号长度必须介于 1 和 50 之间")
    @Excel(name="退费申请单号", width = 25)
    @TableField("refund_apply_no")
    @ApiModelProperty(value = "退费申请单号")
    private String refundApplyNo;		// 退费申请单号

    @Length(min=0, max=30, message="退费申请人长度必须介于 0 和 30 之间")
    @Excel(name="退费申请人", width = 25)
    @TableField("refund_apply_name")
    @ApiModelProperty(value = "退费申请人")
    private String refundApplyName;		// 退费申请人

    @Length(min=0, max=30, message="退费申请人联系方式长度必须介于 0 和 30 之间")
    @Excel(name="退费申请人联系方式", width = 25)
    @TableField("refund_apply_contact")
    @ApiModelProperty(value = "退费申请人联系方式")
    private String refundApplyContact;		// 退费申请人联系方式

    @Length(min=0, max=20, message="退费申请车牌号码长度必须介于 0 和 20 之间")
    @Excel(name="退费申请车牌号码", width = 25)
    @TableField("refund_appl_vehicle_plate")
    @ApiModelProperty(value = "退费申请车牌号码")
    private String refundApplVehiclePlate;		// 退费申请车牌号码

    @Excel(name="退费申请车牌颜色", width = 25)
    @TableField("refund_appy_vehicle_color")
    @ApiModelProperty(value = "退费申请车牌颜色")
    private Integer refundAppyVehicleColor;		// 退费申请车牌颜色

    @Length(min=0, max=60, message="三级运营商长度必须介于 0 和 60 之间")
    @Excel(name="三级运营商", width = 25)
    @TableField("site_id")
    @ApiModelProperty(value = "三级运营商")
    private String siteId;		// 三级运营商

    @Length(min=0, max=40, message="原始交易编号长度必须介于 0 和 40 之间")
    @Excel(name="原始交易编号", width = 25)
    @TableField("transaction_id")
    @ApiModelProperty(value = "原始交易编号")
    private String transactionId;		// 原始交易编号

    @Length(min=0, max=4000, message="退费申请原因长度必须介于 0 和 4000 之间")
    @Excel(name="退费申请原因", width = 25)
    @TableField("refund_apply_reason")
    @ApiModelProperty(value = "退费申请原因")
    private String refundApplyReason;		// 退费申请原因

    @NotNull(message="退费申请处理结果，0-待审核、1-审核通过、2-审核不通过不能为空")
    @Excel(name="退费申请处理结果，0-待审核、1-审核通过、2-审核不通过", width = 25)
    @TableField("refund_apply_handle_result")
    @ApiModelProperty(value = "退费申请处理结果，0-待审核、1-审核通过、2-审核不通过")
    private Integer refundApplyHandleResult;		// 退费申请处理结果，0-待审核、1-审核通过、2-审核不通过

    @Length(min=0, max=4000, message="退费申请处理说明长度必须介于 0 和 4000 之间")
    @Excel(name="退费申请处理说明", width = 25)
    @TableField("refund_apply_handle_msg")
    @ApiModelProperty(value = "退费申请处理说明")
    private String refundApplyHandleMsg;		// 退费申请处理说明

    @Excel(name="服务类型", width = 25)
    @TableField("service_type")
    @ApiModelProperty(value = "服务类型")
    private Integer serviceType;		// 服务类型

    @Excel(name="退费申请时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("apply_time")
    @ApiModelProperty(value = "退费申请时间")
    private Date applyTime;		// 退费申请时间

    private String applyTimeStr;

    @Excel(name="1-银联线上退费 2-商户线下退费 3-联网中心退费", width = 25)
    @TableField("refund_way_type")
    @ApiModelProperty(value = "1-银联线上退费 2-商户线下退费 3-联网中心退费")
    private Integer refundWayType;		// 1-银联线上退费 2-商户线下退费 3-联网中心退费

    @TableField("merchant_real_fee")
    private BigDecimal merchantRealFee;

    @TableField("merchant_pay_fee")
    private BigDecimal merchantPayFee;

    @TableField("merchant_discount_fee")
    private BigDecimal merchantDiscountFee;

    private String feedBackTradeTime;

    private Integer refundState;
}