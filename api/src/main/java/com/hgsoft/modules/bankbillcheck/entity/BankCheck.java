package com.hgsoft.modules.bankbillcheck.entity;

import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 新银行对账实体类
 * Created by 吴鉴武 on 2021/7/16 10:18
 */
@Data
public class BankCheck implements Serializable {

    private static final long serialVersionUID = -1898278548982069957L;

    @Excel(name = "交易日期",width = 25)
    private String tradeDay;
    private String merchantGroupId;
    @Excel(name = "拓展方",width = 25)
    private String merchantGroupName;
    private String merchantId;
    @Excel(name = "运营方",width = 25)
    private String merchantName;
    private String siteId;
    @Excel(name = "场景",width = 25)
    private String siteName;
    @Excel(name = "银行流水总数",width = 25)
    private Integer totalCheckCount;
    @Excel(name = "银行实扣总金额(元)",width = 25)
    private BigDecimal totalCheckAmount;
    @Excel(name = "本系统流水总数",width = 25)
    private Integer totalCount;
    @Excel(name = "本系统流水实收总金额(元)",width = 25)
    private BigDecimal totalAmount;
    @Excel(name = "流水总数差额",width = 25)
    private Integer differCount;
    @Excel(name = "流水实收总金额差额(元)",width = 25)
    private BigDecimal differAmount;
    private Integer checkMode;
    @Excel(name = "对账模式",width = 25)
    private String checkModeName;
    private Integer checkStatus;
    @Excel(name = "核对状态",width = 25)
    private String checkStatusName;
    @Excel(name = "确认后流水总数",width = 25)
    private Integer totalConfirmCount;
    @Excel(name = "确认后实收总金额(元)",width = 25)
    private BigDecimal totalConfirmAmount;
    @Excel(name = "备注",width = 25)
    private String remark;
    @Excel(name = "核对时间",width = 25)
    private String checkTime;
    private Integer serviceType;
    private UserMerchantPermissionswrapper.UserMerchantParam userMerchantParam;
    private String[] timeScope;
    private Integer nodeLevel;
}
