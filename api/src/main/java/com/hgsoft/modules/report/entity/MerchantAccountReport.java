package com.hgsoft.modules.report.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.ecip.framework.core.entity.DataEntity;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商户应收金额报表实体类
 * Created by 吴鉴武 on 2021/4/16 10:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantAccountReport extends DataEntity<MerchantAccountReport> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 交易日期范围(查询使用)
     */
    private String[] timeScope;
    private Integer nodeLevel;
    private Integer value;

    /**
     * 一级运营商
     */
    private String merchantGroupId;
    @Excel(name = "拓展方",width = 25)
    private String merchantGroupName;

    /**
     * 二级运营商
     */
    private String merchantId;
    @Excel(name = "运营方",width = 25)
    private String merchantName;
    /**
     * 三级运营商
     */
    private String siteId;
    @Excel(name = "场景",width = 25)
    private String siteName;
    /**
     * 交易日期
     */
    @Excel(name = "交易日期",width = 25)
    private String tradeDay;
    /**
     * 已结算流水数
     */
    @Excel(name = "已结算流水数",width = 25)
    private Long settlementCount;
    /**
     * 已结算商户应收总金额
     */
    @Excel(name = "已结算商户应收总金额(元)",width = 25,exportConvert = true)
    private BigDecimal settlementMerchantTotalFee;

    public BigDecimal convertgetSettlementMerchantTotalFee(){
        return this.settlementMerchantTotalFee.divide(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 未结算流水数
     */
    @Excel(name = "未结算流水数",width = 25)
    private Long unsettlementCount;
    /**
     * 未结算商户应收总金额
     */
    @Excel(name = "未结算商户应收总金额(元)",width = 25,exportConvert = true)
    private BigDecimal unsettlementMerchantTotalFee;

    public BigDecimal convertgetUnsettlementMerchantTotalFee(){
        return this.unsettlementMerchantTotalFee.divide(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 联网中心总流水数
     */
    @Excel(name = "联网中心总流水数",width = 25)
    private Long etcTotalCount;
    /**
     * 商户应收联网中心总金额
     */
    @Excel(name = "商户应收联网中心总金额(元)",width = 25,exportConvert = true)
    private BigDecimal etcMerchantTotalFee;

    public BigDecimal convertgetEtcMerchantTotalFee(){
        return this.etcMerchantTotalFee.divide(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
    }
    /**
     *银联总流水数
     */
    @Excel(name = "银联总流水数",width = 25)
    private Long bankTotalCount;
    /**
     * 商户应收银联总金额
     */
    @Excel(name = "商户应收银联总金额(元)",width = 25,exportConvert = true)
    private BigDecimal bankMerchantTotalFee;
    public BigDecimal convertgetBankMerchantTotalFee(){
        return this.bankMerchantTotalFee.divide(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 银联退费流水数
     */
    @Excel(name = "银联退费流水数",width = 25)
    private Long bankRefundCount;
    /**
     * 商户应扣银联总退费金额
     */
    @Excel(name = "商户应扣银联总退费金额(元)",width = 25,exportConvert = true)
    private BigDecimal bankMerchantRefundTotalFee;
    public BigDecimal convertgetBankMerchantRefundTotalFee(){
        return this.bankMerchantRefundTotalFee.divide(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 总流水数
     */
    @Excel(name = "总流水数",width = 25)
    private Long totalCount;
    /**
     * 商户应收总金额
     */
    @Excel(name = "商户应收总金额(元)",width = 25,exportConvert = true)
    private BigDecimal merchantTotalFee;
    public BigDecimal convertgetMerchantTotalFee(){
        return this.merchantTotalFee.divide(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 根据商户信息控制用户权限
     */
    @TableField(exist = false)
    private UserMerchantPermissionswrapper.UserMerchantParam userMerchantParam;
}
