package com.hgsoft.modules.gdetc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hgsoft.ecip.framework.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 联合电服收益entity
 *
 * @author wjw
 * @version 2022-05-06
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "资金结算表", description = "资金结算表")
@TableName(value = "tb_settlement_report", resultMap = "BaseResultMap")
public class GdetcProfit extends DataEntity<GdetcProfit> implements Serializable {

    private static final long serialVersionUID = 1L;

    public GdetcProfit() {
        super();
        this.setIdType(IDTYPE_IDWORKER);
    }

    @TableId(value = "sys_id", type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    @ApiModelProperty(value = "系统编号")
    private Long sysId;        // 系统编号

    @TableField("merchant_group_id")
    @ApiModelProperty(value = "拓展方编号")
    private String merchantGroupId; //一级商户编号

    @TableField("merchant_id")
    @ApiModelProperty(value = "运营方编码")
    private String merchantId;        // 运营方编码

    @Excel(name = "运营方名称", width = 25)
    @TableField("merchant_name")
    @ApiModelProperty(value = "运营方名称")
    private String merchantName;        // 运营方名称

    @TableField("site_id")
    @ApiModelProperty(value = "服务方编码")
    private String siteId;        // 服务方编码

    @Excel(name = "服务方名称", width = 25)
    @TableField("site_name")
    @ApiModelProperty(value = "服务方名称")
    private String siteName;        // 服务方名称

    @Excel(name = "交易日期", width = 25)
    @TableField("trx_date")
    @ApiModelProperty(value = "交易日期")
    private String trxDate;        // 交易日期

    @TableField("toll_settlement_interval")
    @ApiModelProperty(value = "结算周期")
    private Integer tollSettlementInterval;

    @Excel(name = "结算周期", width = 25)
    private String tollSettlementIntervalFormat;

    @Excel(name = "通行费结算日期", width = 25, format = "yyyy-MM-dd")
    @TableField("toll_settlement_date")
    @ApiModelProperty(value = "通行费结算日期")
    private Date tollSettlementDate;        // 通行费结算日期

    @Excel(name = "交易笔数", width = 25,groupName = "粤通卡交易总量")
    @TableField("total_gdetc_cnt")
    @ApiModelProperty(value = "粤通卡应结算交易总笔数")
    private Integer totalGdetcCnt;        // 粤通卡应结算交易总笔数

    @Excel(name = "交易金额(元)", width = 25,groupName = "粤通卡交易总量")
    @TableField("total_gdetc_amt")
    @ApiModelProperty(value = "粤通卡应结算交易总金额")
    private BigDecimal totalGdetcAmt;        // 交易金额

    @Excel(name = "预支抵扣笔数", width = 25,groupName = "粤通卡预支抵扣交易总量")
    @TableField("reject_gdetc_cnt")
    @ApiModelProperty(value = "粤通卡抵扣交易总笔数")
    private Integer rejectGdetcCnt;        // 粤通卡抵扣交易总笔数

    @Excel(name = "预支抵扣金额(元)", width = 25,groupName = "粤通卡预支抵扣交易总量")
    @TableField("reject_gdetc_amt")
    @ApiModelProperty(value = "粤通卡抵扣交易总金额")
    private BigDecimal rejectGdetcAmt;        // 粤通卡抵扣交易总金额

    @Excel(name = "应结算总金额(元)", width = 25)
    @TableField("actual_gdetc_amt")
    @ApiModelProperty(value = "应结算总金额")
    private BigDecimal actualGdetcAmt;        // 应结算总金额

    @TableField("center_service_rate")
    @ApiModelProperty(value = "中心结算服务费率")
    private BigDecimal centerServiceRate;        // 中心结算服务费率

    @Excel(name = "中心结算服务费率", width = 25)
    private String centerServiceRateFormat;

    @Excel(name = "联合电服收益(元)", width = 25)
    @TableField("service_gdetc_amt")
    @ApiModelProperty(value = "联合电服收益")
    private BigDecimal serviceGdetcAmt;        // 联合电服收益
}