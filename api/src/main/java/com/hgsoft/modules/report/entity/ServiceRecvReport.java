package com.hgsoft.modules.report.entity;

import cn.hutool.core.util.StrUtil;
import com.hgsoft.modules.enums.SettlementIntervalEnum;
import com.hgsoft.modules.enums.TransferStatusEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import com.hgsoft.ecip.framework.core.entity.DataEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.jeecgframework.poi.excel.annotation.Excel;


import java.io.Serializable;

/**
 * 服务方应收报表 Entity
 *
 * @author
 * @version 2022-04-28 05:44:26
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "资金结算信息表", description = "服务方应收报表")
@TableName(value = "tb_settlement_report", resultMap = "BaseResultMap")
public class ServiceRecvReport extends DataEntity<ServiceRecvReport> implements Serializable {

    private static final long serialVersionUID = 1L;

    public ServiceRecvReport() {
        super();
        this.setIdType(IDTYPE_IDWORKER);
    }

    @Length(min = 1, max = 11, message = "系统主键长度必须介于 1 和 11 之间")
    @TableId("sys_id")
    @ApiModelProperty(value = "系统主键")
    private String sysId;        // 系统主键

    @Length(min = 1, max = 60, message = "运营方编码长度必须介于 1 和 60 之间")
    @TableField("merchant_id")
    @ApiModelProperty(value = "运营方编码")
    private String merchantId;        // 运营方编码

    @Length(min = 0, max = 150, message = "运营方名称长度必须介于 0 和 150 之间")
    @Excel(name = "运营方", width = 25)
    @TableField("merchant_name")
    @ApiModelProperty(value = "运营方名称")
    private String merchantName;        // 运营方名称

    @Length(min = 1, max = 60, message = "服务方编码长度必须介于 1 和 60 之间")
    @TableField("site_id")
    @ApiModelProperty(value = "服务方编码")
    private String siteId;        // 服务方编码

    @Length(min = 0, max = 150, message = "服务方名称长度必须介于 0 和 150 之间")
    @Excel(name = "服务方", width = 25)
    @TableField("site_name")
    @ApiModelProperty(value = "服务方名称")
    private String siteName;        // 服务方名称

    @Length(min = 1, max = 10, message = "交易日期长度必须介于 1 和 10 之间")
    @Excel(name = "交易日期", width = 25)
    @TableField("trx_date")
    @ApiModelProperty(value = "交易日期")
    private String trxDate;        // 交易日期

//    @Excel(name = "结算周期", width = 25)
    @TableField("toll_settlement_interval")
    @ApiModelProperty(value = "通行费结算周期，1-T+1；2-T+4；3-月结")
    private Integer tollSettlementInterval;        // 通行费结算周期，1-T+1；2-T+4；3-月结

    @Excel(name = "结算周期", width = 25)
    @TableField(exist = false)
    @ApiModelProperty(value = "通行费结算周期，1-T+1；2-T+4；3-月结")
    private String tollSettlementIntervalTxt;        // 通行费结算周期，1-T+1；2-T+4；3-月结

    @NotNull(message = "通行费结算日期不能为空")
    @Excel(name = "通行费结算日期", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("toll_settlement_date")
    @ApiModelProperty(value = "通行费结算日期")
    private Date tollSettlementDate;        // 通行费结算日期

    @Length(min = 1, max = 11, message = "应结算交易总笔数长度必须介于 1 和 11 之间")
    @Excel(name = "交易笔数", width = 25, groupName = "应结算交易总量")
    @TableField("total_settlement_cnt")
    @ApiModelProperty(value = "应结算交易总笔数")
    private Long totalSettlementCnt;        // 应结算交易总笔数

    @Length(min = 1, max = 11, message = "应结算交易总金额，单位分长度必须介于 1 和 11 之间")
    @Excel(name = "交易金额（元）", width = 25, groupName = "应结算交易总量")
    @TableField("total_settlement_amt")
    @ApiModelProperty(value = "应结算交易总金额，单位分")
    private BigDecimal totalSettlementAmt;        // 应结算交易总金额，单位分

    @Length(min = 1, max = 11, message = "抵扣交易总笔数长度必须介于 1 和 11 之间")
    @Excel(name = "交易笔数", width = 25, groupName = "预支抵扣交易总量")
    @TableField("reject_settlement_cnt")
    @ApiModelProperty(value = "抵扣交易总笔数")
    private Long rejectSettlementCnt;        // 抵扣交易总笔数

    @Length(min = 1, max = 11, message = "抵扣交易总金额，单位分长度必须介于 1 和 11 之间")
    @Excel(name = "交易金额（元）", width = 25, groupName = "预支抵扣交易总量")
    @TableField("reject_settlement_amt")
    @ApiModelProperty(value = "抵扣交易总金额，单位分")
    private BigDecimal rejectSettlementAmt;        // 抵扣交易总金额，单位分

    @Length(min = 1, max = 11, message = "实际交易总金额，单位分长度必须介于 1 和 11 之间")
    @Excel(name = "应结算总金额（元）", width = 25)
    @TableField("actual_settlement_amt")
    @ApiModelProperty(value = "实际交易总金额，单位分")
    private BigDecimal actualSettlementAmt;        // 实际交易总金额，单位分

    @NotNull(message = "服务费费率不能为空")
    @Excel(name = "服务费率", width = 25)
    @TableField("service_rate")
    @ApiModelProperty(value = "服务费费率")
    private String serviceRate;        // 服务费费率

    @NotNull(message = "服务类型，2-停车场；3-加油站；4-服务器；5-市政拓展；6-充电桩不能为空")
    @TableField("service_type")
    @ApiModelProperty(value = "服务类型，2-停车场；3-加油站；4-服务器；5-市政拓展；6-充电桩")
    private Integer serviceType;        // 服务类型，2-停车场；3-加油站；4-服务器；5-市政拓展；6-充电桩

    @NotNull(message = "服务费，actual_settlement_amt * service_rate，单位分不能为空")
    @Excel(name = "服务费（元）", width = 25)
    @TableField("service_amt")
    @ApiModelProperty(value = "服务费，actual_settlement_amt * service_rate，单位元")
    private BigDecimal serviceAmt;        // 服务费，actual_settlement_amt * service_rate，单位元

    @TableField("service_settlement_interval")
    @ApiModelProperty(value = "服务费结算周期，1-T+1；2-T+4；3-月结")
    private Integer serviceSettlementInterval;        // 服务费结算周期，1-T+1；2-T+4；3-月结

    @Excel(name = "服务费结算模式", width = 25)
    @TableField(exist = false)
    @ApiModelProperty(value = "服务费结算周期，1-T+1；2-T+4；3-月结")
    private String serviceSettlementIntervalTxt;        // 服务费结算周期，1-T+1；2-T+4；3-月结

    @Length(min = 1, max = 11, message = "转账金额，单位分长度必须介于 1 和 11 之间")
    @Excel(name = "转账金额（元）", width = 25)
    @TableField("bank_transfer_amt")
    @ApiModelProperty(value = "转账金额，单位元")
    private BigDecimal bankTransferAmt;        // 转账金额，单位分

    @TableField("bank_transfer_sta")
    @ApiModelProperty(value = "转账状态")
    private Integer bankTransferSta;        // 转账状态

    @Excel(name = "状态", width = 25)
    @TableField(exist = false)
    @ApiModelProperty(value = "转账状态")
    private String bankTransferStaTxt;        // 转账状态

    @Excel(name = "转账时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("bank_transfer_time")
    @ApiModelProperty(value = "转账时间")
    private Date bankTransferTime;        // 转账时间

    @NotNull(message = "一级编码")
    @TableField("merchant_group_id")
    @ApiModelProperty(value = "一级编码")
    private String merchantGroupId;        //一级编码


    /**
     * 查询条件
     */
    @TableField(exist = false)
    private String beginTrxDate;        // 开始 交易日期

    @TableField(exist = false)
    private String endTrxDate;        // 结束 交易日期

    private String settlementIntervalTxt;        // 结算周期

    private String[] timeScope;//时间范围

    private String searchId;//节点Id

    private String nodeLevel;//节点

    public String getBankTransferStaTxt() {
        if (bankTransferSta == null) return null;
        return TransferStatusEnum.getTitleByValue(bankTransferSta);
    }

    public String getTollSettlementIntervalTxt() {
        if (tollSettlementInterval == null) return null;
        return SettlementIntervalEnum.getTitleByValue(tollSettlementInterval);
    }

    public String getServiceSettlementIntervalTxt() {
        if (serviceSettlementInterval == null) return null;
        return SettlementIntervalEnum.getTitleByValue(serviceSettlementInterval);
    }

    public String getServiceRate() {
        if (StrUtil.isEmpty(serviceRate)) return null;
        if (serviceRate.equals("-")) return "-";
        return new BigDecimal(serviceRate).multiply(BigDecimal.valueOf(1000)).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "‰";
    }
}