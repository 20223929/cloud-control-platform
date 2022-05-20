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
 * 联网中心对账核对表 Entity
 *
 * @author 何志豪
 * @version 2021-04-21 02:30:17
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "联网中心对账核对表", description = "联网中心对账核对表")
@TableName(value = "tb_center_bill_check", resultMap = "BaseResultMap")
public class TbCenterBillCheck extends DataEntity<TbCenterBillCheck> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TbCenterBillCheck() {
        super();
        this.setIdType(IDTYPE_IDWORKER);
    }

    @Length(min = 1, max = 10, message = "银行对账交易日期长度必须介于 1 和 10 之间")
    @Excel(name = "银行对账交易日期", width = 25)
    @TableId("trans_date")
    @ApiModelProperty(value = "银行对账交易日期")
    private String transDate;        // 银行对账交易日期

    @Length(min = 1, max = 60, message = "拓展方长度必须介于 1 和 60 之间")
    @TableId("merchant_group_id")
    @ApiModelProperty(value = "拓展方")
    private String merchantGroupId;        // 拓展方

    @Length(min = 1, max = 60, message = "运营方长度必须介于 1 和 60 之间")
    @TableId("merchant_id")
    @ApiModelProperty(value = "运营方")
    private String merchantId;        // 运营方

    @Length(min = 1, max = 60, message = "场景长度必须介于 1 和 60 之间")
    @TableId("site_id")
    @ApiModelProperty(value = "场景")
    private String siteId;        // 场景

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

    @Excel(name = "用户确认流水数量", width = 25)
    @TableField("confirm_total_count")
    @ApiModelProperty(value = "用户确认流水数量")
    private Long confirmTotalCount;        // 用户确认流水数量

    @Excel(name = "联网中心流水数量", width = 25)
    @TableField(exist = false)
    @ApiModelProperty(value = "联网中心流水数量")
    private Long centerTotalCount;        // 联网中心流水数量

    @TableField("confirm_total_amount")
    @ApiModelProperty(value = "用户确认流水金额(分)")
    private Long confirmTotalAmount;        //用户确认流水金额(分)

    @TableField(exist = false)
    @ApiModelProperty(value = "联网中心流水金额(分)")
    private Long centerTotalAmount;        //联网中心流水金额(分)

    @Excel(name = "用户确认流水金额(元)", width = 25)
    @TableField(exist = false)
    @ApiModelProperty(value = "用户确认流水金额(元)")
    private String confirmTotalAmountStr;        //用户确认流水金额(元)

    @Excel(name = "联网中心流水金额(元)", width = 25)
    @TableField(exist = false)
    @ApiModelProperty(value = "联网中心流水金额(元)")
    private String centerTotalAmountStr;        //联网中心流水金额(元)

    @ApiModelProperty(value = "核对状态")
    @TableField(exist = false)
    private Integer confirmState;

    @Excel(name = "核对状态", width = 25)
    @ApiModelProperty(value = "核对状态名称")
    @TableField(exist = false)
    private String confirmStateStr;// 0-待确认 1-已确认 2-待补确认 3-已补确认

    @Length(min = 0, max = 50, message = "确认人长度必须介于 0 和 50 之间")
    @Excel(name = "确认人", width = 25)
    @TableField("confirm_man")
    @ApiModelProperty(value = "确认人")
    private String confirmMan;        // 确认人

    @NotNull(message = "确认时间不能为空")
    @Excel(name = "确认时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("confirm_time")
    @ApiModelProperty(value = "确认时间")
    private Date confirmTime;        // 确认时间

    @Length(min = 0, max = 800, message = "备注长度必须介于 0 和 800 之间")
    @Excel(name = "备注", width = 25)
    @TableField("remark")
    @ApiModelProperty(value = "备注")
    private String remark;        // 备注

    @Length(min = 0, max = 50, message = "补确认人长度必须介于 0 和 50 之间")
    @Excel(name = "补确认人", width = 25)
    @TableField("second_confirm_man")
    @ApiModelProperty(value = "补确认人")
    private String secondConfirmMan;        // 补确认人

    @NotNull(message = "补确认时间不能为空")
    @Excel(name = "补确认时间", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("second_confirm_time")
    @ApiModelProperty(value = "补确认时间")
    private Date secondConfirmTime;        // 补确认时间

    @Length(min = 0, max = 800, message = "补确认备注长度必须介于 0 和 800 之间")
    @Excel(name = "补确认备注", width = 25)
    @TableField("second_confirm_remark")
    @ApiModelProperty(value = "补确认备注")
    private String secondConfirmRemark;        // 补确认备注

}