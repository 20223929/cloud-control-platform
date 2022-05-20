package com.hgsoft.modules.bankbillcheck.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.Date;

/**
 * 银行对账核对表 Entity
 *
 * @author 何志豪
 * @version 2021-04-18 23:35:35
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "银行对账核对表", description = "银行对账核对表")
@TableName(value = "tb_bank_bill_check", resultMap = "BaseResultMap")
public class TbBankBillCheck extends DataEntity<TbBankBillCheck> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TbBankBillCheck() {
        super();
        this.setIdType(IDTYPE_IDWORKER);
    }

    @Length(min = 1, max = 10, message = "对账交易日期长度必须介于 1 和 10 之间")
    @Excel(name = "交易日期", width = 25)
    @TableId("trans_date")
    @ApiModelProperty(value = "对账交易日期")
    private String transDate;        // 对账交易日期

    @TableField(exist = false)
    @Excel(name = "拓展方", width = 25)
    @ApiModelProperty(value = "一级运营商名")
    private String merchantGroupIdName;        // 一级运营商

    @Excel(name = "运营方", width = 25)
    @TableField(exist = false)
    @ApiModelProperty(value = "二级运营商名")
    private String merchantIdName;        // 二级运营商

    @Excel(name = "场景", width = 25)
    @TableField(exist = false)
    @ApiModelProperty(value = "三级运营商名")
    private String siteIdName;        // 三级运营商

    @Excel(name = "服务类型名", width = 25)
    @TableField(exist = false)
    @ApiModelProperty(value = "服务类型名")
    private String serviceTypeName;        // 服务类型

    @Length(min = 1, max = 60, message = "一级运营商长度必须介于 1 和 60 之间")
    //@Excel(name = "一级运营商", width = 25)
    @TableId("merchant_group_id")
    @ApiModelProperty(value = "一级运营商")
    private String merchantGroupId;        // 一级运营商

    @Length(min = 1, max = 60, message = "二级运营商长度必须介于 1 和 60 之间")
    //@Excel(name = "二级运营商", width = 25)
    @TableId("merchant_id")
    @ApiModelProperty(value = "二级运营商")
    private String merchantId;        // 二级运营商

    @Length(min = 1, max = 60, message = "三级运营商长度必须介于 1 和 60 之间")
    //@Excel(name = "三级运营商", width = 25)
    @TableId("site_id")
    @ApiModelProperty(value = "三级运营商")
    private String siteId;        // 三级运营商

    @NotNull(message = "服务类型不能为空")
    //@Excel(name = "服务类型", dicCode = "service_type", width = 25)
    @TableField("service_type")
    @ApiModelProperty(value = "服务类型")
    private Integer serviceType;        // 服务类型

    @Length(min = 1, max = 10, message = "银行流水总数长度必须介于 1 和 10 之间")
    @Excel(name = "银行流水总数", width = 25)
    @TableField("bank_total_count")
    @ApiModelProperty(value = "银行流水总数")
    private String bankTotalCount;        // 银行流水总数

    @Length(min = 1, max = 10, message = "银行流水总金额（元）长度必须介于 1 和 10 之间")
    @Excel(name = "银行流水总金额(元)", width = 25)
    @TableField("bank_total_amount")
    @ApiModelProperty(value = "银行流水总金额(元)")
    private String bankTotalAmount;        // 银行流水总金额(元)

    @Length(min = 1, max = 10, message = "本系统流水总数长度必须介于 1 和 10 之间")
    @Excel(name = "本系统流水总数", width = 25)
    @TableField("platform_total_count")
    @ApiModelProperty(value = "本系统流水总数")
    private String platformTotalCount;        // 本系统流水总数

    @Length(min = 1, max = 10, message = "本系统流水总金额（元）长度必须介于 1 和 10 之间")
    @Excel(name = "本系统流水总金额(元）", width = 25)
    @TableField("platform_total_amount")
    @ApiModelProperty(value = "本系统流水总金额(元）")
    private String platformTotalAmount;        // 本系统流水总金额(元）

    @Length(min = 1, max = 10, message = "流水总数差额长度必须介于 1 和 10 之间")
    @Excel(name = "流水总数差额", width = 25)
    @TableField("diff_total_count")
    @ApiModelProperty(value = "流水总数差额")
    private String diffTotalCount;        // 流水总数差额

    @Length(min = 1, max = 10, message = "流水总金额差额（元）长度必须介于 1 和 10 之间")
    @Excel(name = "流水总金额差额(元)", width = 25)
    @TableField("diff_total_amount")
    @ApiModelProperty(value = "流水总金额差额(元)")
    private String diffTotalAmount;        // 流水总金额差额(元)

    @Length(min = 1, max = 10, message = "确认状态:0-未确认 1-已确认长度必须介于 1 和 10 之间")
    @TableField("confirm_state")
    @ApiModelProperty(value = "确认状态:0-未确认 1-已确认")
    private String confirmState;        // 确认状态:0-未确认 1-已确认

    @Length(min = 1, max = 10, message = "确认状态:0-未确认 1-已确认长度必须介于 1 和 10 之间")
    @Excel(name = "确认状态", width = 25)
    @TableField(exist = false)
    @ApiModelProperty(value = "确认状态:0-未确认 1-已确认")
    private String confirmStateName;        // 确认状态:0-未确认 1-已确认

    @Length(min = 1, max = 50, message = "确认人长度必须介于 1 和 50 之间")
    @Excel(name = "确认人", width = 25)
    @TableField("confirm_man")
    @ApiModelProperty(value = "确认人")
    private String confirmMan;        // 确认人

    @NotNull(message = "确认时间不能为空")
    @Excel(name = "确认时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("confirm_time")
    @ApiModelProperty(value = "确认时间")
    private Date confirmTime;        // 确认时间

    @Length(min = 1, max = 800, message = "备注长度必须介于 1 和 800 之间")
    @Excel(name = "备注", width = 25)
    @TableField("remark")
    @ApiModelProperty(value = "备注")
    private String remark;        // 备注

    @TableField(exist = false)
    private String beginTransDate;        // 开始 对账交易日期

    @TableField(exist = false)
    private String endTransDate;        // 结束 对账交易日期

    /**
     * 根据商户信息控制用户权限
     */
    @TableField(exist = false)
    private UserMerchantPermissionswrapper.UserMerchantParam userMerchantParam;
}