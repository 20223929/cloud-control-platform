package com.hgsoft.modules.report.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.DataEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 清分对账信息表
 * @author 郑裕强
 * @date 2022-04-30 11:29:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "tb_clear_bill", resultMap = "BaseResultMap")
public class ClearBill extends DataEntity<ClearBill> implements Serializable {

    private static final long serialVersionUID = 1L;

    public ClearBill() {
        super();
        this.setIdType(IDTYPE_IDWORKER);
    }

    /**
     * 系统主键
     */
    @NotNull(message="主键不能为空")
    @TableId("sys_id")
    @ApiModelProperty(value = "主键")
    private String sysId;

    /**
     * 交易日期
     */
    @NotNull(message="交易日期不能为空")
    @Excel(name="交易日期", width = 25)
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("trx_date")
    @ApiModelProperty(value = "交易日期")
    private String trxDate;

    /**
     * 运营方编码
     */
    @Length(min=1, max=64, message="运营方编码长度必须介于 1 和 64 之间")
    @TableField("merchant_id")
    @ApiModelProperty(value = "运营方编码")
    private String merchantId;

    /**
     * 运营方名称
     */
    @Length(min=1, max=64, message="运营方名称长度必须介于 1 和 64 之间")
    @Excel(name="运营方名称", width = 25)
    @TableField("merchant_name")
    @ApiModelProperty(value = "运营方名称")
    private String merchantName;

    /**
     * 服务方编码
     */
    @Length(min=1, max=64, message="服务方编码长度必须介于 1 和 64 之间")
    @TableField("site_id")
    @ApiModelProperty(value = "服务方编码")
    private String siteId;

    /**
     * 服务方名称
     */
    @Length(min=1, max=64, message="服务方名称长度必须介于 1 和 64 之间")
    @Excel(name="服务方名称", width = 25)
    @TableField("site_name")
    @ApiModelProperty(value = "服务方名称")
    private String siteName;

    /**
     * 通行费结算周期，1-T+1；2-T+4；3-月结
     */
    @NotNull(message="结算周期不能为空")
    @TableField("toll_settlement_interval")
    @ApiModelProperty(value = "结算周期")
    private Integer tollSettlementInterval;

    @Excel(name="结算周期", width = 25)
    @TableField(exist = false)
    private String tollSettlementIntervalTxt;

    /**
     * 交易流水数
     */
    @NotNull(message="交易流水数不能为空")
    @Excel(name="交易流水数", width = 25)
    @TableField("total_trans_cnt")
    @ApiModelProperty(value = "交易流水数")
    private Integer totalTransCnt;

    /**
     * 交易流水金额，单位分
     */
    @NotNull(message="交易流水金额（元）不能为空")
    @Excel(name="交易流水金额(元)", width = 25)
    @TableField("total_trans_amt")
    @ApiModelProperty(value = "交易流水金额(元)")
    private BigDecimal totalTransAmt;

    /**
     * 结算流水数
     */
    @NotNull(message="结算流水数不能为空")
    @Excel(name="结算流水数", width = 25)
    @TableField("total_settlement_cnt")
    @ApiModelProperty(value = "结算流水数")
    private Integer totalSettlementCnt;

    /**
     * 结算金额，单位分
     */
    @NotNull(message="结算金额不能为空")
    @Excel(name="结算金额", width = 25)
    @TableField("total_settlement_amt")
    @ApiModelProperty(value = "结算金额")
    private BigDecimal totalSettlementAmt;

    /**
     * 已清分数
     */
    @NotNull(message="已清分数不能为空")
    @Excel(name="已清分数", width = 25)
    @TableField("total_clear_cnt")
    @ApiModelProperty(value = "已清分数")
    private Integer totalClearCnt;

    /**
     * 已清分总金额，单位分
     */
    @NotNull(message="已清分总金额（元）不能为空")
    @Excel(name="已清分总金额(元)", width = 25)
    @TableField("total_clear_amt")
    @ApiModelProperty(value = "已清分总金额(元)")
    private BigDecimal totalClearAmt;

    /**
     * 待清分数
     */
    @NotNull(message="待清分数不能为空")
    @Excel(name="待清分数", width = 25)
    @TableField("total_unclear_cnt")
    @ApiModelProperty(value = "待清分数")
    private Integer totalUnclearCnt;

    /**
     * 待清分总金额，单位分
     */

    @NotNull(message="待清分总金额（元）不能为空")
    @Excel(name="待清分总金额(元)", width = 25)
    @TableField("total_unclear_amt")
    @ApiModelProperty(value = "待清分总金额(元)")
    private BigDecimal totalUnclearAmt;

    /**
     * 记账异常流水数
     */
    @NotNull(message="记账异常流水数不能为空")
    @Excel(name="记账异常流水数", groupName = "清分异常", width = 25)
    @TableField("clear_exception_cnt")
    @ApiModelProperty(value = "记账异常流水数")
    private Integer clearExceptionCnt;

    /**
     * 记账异常总金额，单位分
     */
    @NotNull(message="记账异常总金额（元）不能为空")
    @Excel(name="记账异常总金额(元)", groupName = "清分异常", width = 25)
    @TableField("clear_exception_amt")
    @ApiModelProperty(value = "记账异常总金额(元)")
    private BigDecimal clearExceptionAmt;

    /**
     * 坏账流水数
     */
    @NotNull(message="坏账流水数不能为空")
    @Excel(name="坏账流水数", groupName = "清分异常", width = 25)
    @TableField("clear_bad_cnt")
    @ApiModelProperty(value = "坏账流水数")
    private Integer clearBadCnt;

    /**
     * 坏账总金额，单位分
     */
    @NotNull(message="坏账总金额（元）不能为空")
    @Excel(name="坏账总金额(元)", groupName = "清分异常", width = 25)
    @TableField("clear_bad_amt")
    @ApiModelProperty(value = "坏账总金额(元)")
    private BigDecimal clearBadAmt;

    /**
     * 创建时间
     */
    @NotNull(message="创建时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("createtime")
    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    @NotNull(message = "一级编码")
    @TableField("merchant_group_id")
    @ApiModelProperty(value = "一级编码")
    private String merchantGroupId;

    /**
     * 交易日期范围(查询使用)
     */
    private String[] timeScope;
    private String searchId;//节点Id
    private String nodeLevel;//节点
    private Integer value;

}