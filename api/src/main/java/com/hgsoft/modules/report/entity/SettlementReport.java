package com.hgsoft.modules.report.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

/**
 * 资金结算信息表
 * @TableName tb_settlement_report
 */


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "资金结算表", description = "资金结算表")
@TableName(value = "tb_settlement_report", resultMap = "BaseResultMap")
public class SettlementReport extends DataEntity<SettlementReport> implements Serializable {

    private static final long serialVersionUID = 1L;

    public SettlementReport() {
        super();
        this.setIdType(IDTYPE_IDWORKER);
    }

    /**
     * 系统主键
     */
    @Length(min=1, max=11, message="系统主键长度必须介于 1 和 11 之间")
    @TableId("sys_id")
    @ApiModelProperty(value = "系统主键")
    private String sysId;

    /**
     * 运营方编码
     */
    @Length(min=1, max=60, message="运营方编码长度必须介于 1 和 60 之间")
    @TableField("merchant_id")
    @ApiModelProperty(value = "运营方编码")
    private String merchantId;

    /**
     * 运营方名称
     */
    @Length(min=0, max=150, message="运营方名称长度必须介于 0 和 150 之间")
    @Excel(name="运营方", width = 25)
    @TableField("merchant_name")
    @ApiModelProperty(value = "运营方名称")
    private String merchantName;

    /**
     * 服务方编码
     */
    @Length(min=1, max=60, message="服务方编码长度必须介于 1 和 60 之间")
    @TableField("site_id")
    @ApiModelProperty(value = "服务方编码")
    private String siteId;

    /**
     * 服务方名称
     */
    @Length(min=0, max=150, message="服务方名称长度必须介于 0 和 150 之间")
    @Excel(name="服务方", width = 25)
    @TableField("site_name")
    @ApiModelProperty(value = "服务方名称")
    private String siteName;

    /**
     * 交易日期
     */
    @Length(min=1, max=10, message="交易日期长度必须介于 1 和 10 之间")
    @Excel(name="交易日期", width = 25)
    @TableField("trx_date")
    @ApiModelProperty(value = "交易日期")
    private String trxDate;

    /**
     * 通行费结算周期，1-T+1；2-T+4；3-月结
     */
    @TableField("toll_settlement_interval")
    @ApiModelProperty(value = "通行费结算周期，1-T+1；2-T+4；3-月结")
    private Integer tollSettlementInterval;

    /**
     *  通行费结算周期
     */
    @Excel(name="结算周期", width = 25)
    @TableField(exist = false)
    private String tollSettlementIntervalTxt;

    /**
     * 通行费结算日期
     */
    @NotNull(message="通行费结算日期不能为空")
    @Excel(name="通行费结算日期", width = 25, format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("toll_settlement_date")
    @ApiModelProperty(value = "通行费结算日期")
    private Date tollSettlementDate;

    /**
     * 应结算交易总笔数
     */
    @Length(min=1, max=11, message="应结算交易总笔数长度必须介于 1 和 11 之间")
    @Excel(name="交易笔数", groupName = "应结算交易总量", width = 25)
    @TableField("total_settlement_cnt")
    @ApiModelProperty(value = "应结算交易总笔数")
    private Integer totalSettlementCnt;

    /**
     * 应结算交易总金额，单位分
     */
    @Length(min=1, max=11, message="应结算交易总金额，单位分长度必须介于 1 和 11 之间")
    @Excel(name="交易金额(元)", groupName = "应结算交易总量", width = 25)
    @TableField("total_settlement_amt")
    @ApiModelProperty(value = "应结算交易总金额，单位分")
    private BigDecimal totalSettlementAmt;

    /**
     * 抵扣交易总笔数
     */
    @Length(min=1, max=11, message="抵扣交易总笔数长度必须介于 1 和 11 之间")
    @Excel(name="退费笔数", groupName = "退费交易总量", width = 25)
    @TableField("reject_settlement_cnt")
    @ApiModelProperty(value = "抵扣交易总笔数")
    private Integer rejectSettlementCnt;

    /**
     * 抵扣交易总金额，单位分
     */
    @Length(min=1, max=11, message="抵扣交易总金额，单位分长度必须介于 1 和 11 之间")
    @Excel(name="退费金额(元)", groupName = "退费交易总量", width = 25)
    @TableField("reject_settlement_amt")
    @ApiModelProperty(value = "抵扣交易总金额，单位分")
    private BigDecimal rejectSettlementAmt;

    /**
     * 实际交易总金额，单位分
     */
    @Length(min=1, max=11, message="实际交易总金额，单位分长度必须介于 1 和 11 之间")
    @Excel(name="应结算总金额(元)", width = 25)
    @TableField("actual_settlement_amt")
    @ApiModelProperty(value = "实际交易总金额，单位分")
    private BigDecimal actualSettlementAmt;

    /**
     * 服务费费率
     */
    @NotNull(message="服务费费率不能为空")
    @TableField("service_rate")
    @ApiModelProperty(value = "服务费费率")
    private BigDecimal serviceRate;

    @Excel(name = "服务费费率", width = 25)
    private String serviceRateTxt;

    /**
     * 服务类型，2-停车场；3-加油站；4-服务器；5-市政拓展；6-充电桩
     */

    @NotNull(message="服务类型，2-停车场；3-加油站；4-服务器；5-市政拓展；6-充电桩不能为空")
    @TableField("service_type")
    @ApiModelProperty(value = "服务类型，2-停车场；3-加油站；4-服务器；5-市政拓展；6-充电桩")
    private Integer serviceType;

    /**
     * 服务费，actual_settlement_amt * service_rate，单位分
     */
    @NotNull(message="服务费，actual_settlement_amt * service_rate，单位分不能为空")
    @Excel(name="服务费(元)", width = 25)
    @TableField("service_amt")
    @ApiModelProperty(value = "服务费，actual_settlement_amt * service_rate，单位分")
    private BigDecimal serviceAmt;

    /**
     * 服务费结算周期，1-T+1；2-T+4；3-月结
     */
    @TableField("service_settlement_interval")
    @ApiModelProperty(value = "服务费结算周期，1-T+1；2-T+4；3-月结")
    private Integer serviceSettlementInterval;

    /**
     *  服务费结算模式
     */
    @Excel(name="结算周期", width = 25)
    @TableField(exist = false)
    private String serviceSettlementIntervalTxt;

    /**
     * 中心结算服务费率
     */
    @NotNull(message="中心结算服务费率不能为空")
    @TableField("center_service_rate")
    @ApiModelProperty(value = "中心结算服务费率")
    private BigDecimal centerServiceRate;

    /**
     * 中心服务费，单位元
     */
    @NotNull(message="中心服务费，单位元不能为空")
    @TableField("center_service_amt")
    @ApiModelProperty(value = "中心服务费，单位元")
    private BigDecimal centerServiceAmt;

    /**
     * 代理商佣金费率
     */
    @NotNull(message="代理商佣金费率不能为空")
    @TableField("agent_rate")
    @ApiModelProperty(value = "代理商佣金费率")
    private BigDecimal agentRate;

    /**
     * 代理商佣金，单位元
     */
    @NotNull(message="代理商佣金，单位元不能为空")
    @TableField("agent_amt")
    @ApiModelProperty(value = "代理商佣金，单位元")
    private BigDecimal agentAmt;

    /**
     * 粤通卡应结算交易总笔数
     */
    @Length(min=1, max=11, message="粤通卡应结算交易总笔数长度必须介于 1 和 11 之间")
    @TableField("total_gdetc_cnt")
    @ApiModelProperty(value = "粤通卡应结算交易总笔数")
    private Integer totalGdetcCnt;

    /**
     * 粤通卡应结算交易总金额，单位分
     */
    @Length(min=1, max=11, message="粤通卡应结算交易总金额，单位分长度必须介于 1 和 11 之间")
    @TableField("total_gdetc_amt")
    @ApiModelProperty(value = "粤通卡应结算交易总金额，单位分")
    private BigDecimal totalGdetcAmt;

    /**
     * 粤通卡抵扣交易总笔数
     */
    @Length(min=1, max=11, message="粤通卡抵扣交易总笔数长度必须介于 1 和 11 之间")
    @TableField("reject_gdetc_cnt")
    @ApiModelProperty(value = "粤通卡抵扣交易总笔数")
    private Integer rejectGdetcCnt;

    /**
     * 粤通卡抵扣交易总金额，单位分
     */
    @Length(min=1, max=11, message="粤通卡抵扣交易总金额，单位分长度必须介于 1 和 11 之间")
    @TableField("reject_gdetc_amt")
    @ApiModelProperty(value = "粤通卡抵扣交易总金额，单位分")
    private BigDecimal rejectGdetcAmt;

    /**
     * 粤通卡实际交易总金额，单位分
     */
    @Length(min=1, max=11, message="粤通卡实际交易总金额，单位分长度必须介于 1 和 11 之间")
    @TableField("actual_gdetc_amt")
    @ApiModelProperty(value = "粤通卡实际交易总金额，单位分")
    private BigDecimal actualGdetcAmt;

    /**
     * 联合电服收益，单位元
     */
    @NotNull(message="联合电服收益，单位元不能为空")
    @TableField("service_gdetc_amt")
    @ApiModelProperty(value = "联合电服收益，单位元")
    private BigDecimal serviceGdetcAmt;

    /**
     * 转账金额，单位分
     */
    @Length(min=1, max=11, message="转账金额，单位分长度必须介于 1 和 11 之间")
    @Excel(name="转账金额(元)", width = 25)
    @TableField("bank_transfer_amt")
    @ApiModelProperty(value = "转账金额，单位分")
    private BigDecimal bankTransferAmt;

    /**
     * 转账状态
     */
    @TableField("bank_transfer_sta")
    @ApiModelProperty(value = "转账状态")
    private Integer bankTransferSta;

    @Excel(name="状态", width = 25)
    @TableField(exist = false)
    private String bankTransferStaTxt;

    /**
     * 转账时间
     */
    @Excel(name="转账时间", width = 25, format = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @TableField("bank_transfer_time")
    @ApiModelProperty(value = "转账时间")
    private Date bankTransferTime;
    
    @NotNull(message = "一级编码")
    @TableField("merchant_group_id")
    @ApiModelProperty(value = "一级编码")
    private String merchantGroupId;

    /**
     * 创建时间
     */
    @NotNull(message="创建时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("createtime")
    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    /**
     * 查询条件
     */
    @TableField(exist = false)
    private String beginTrxDate;        // 开始 交易日期

    @TableField(exist = false)
    private String endTrxDate;

    /**
     * 时间范围
     */
    private String[] timeScope;

    private String searchId;//节点Id

    private String nodeLevel;//节点

}