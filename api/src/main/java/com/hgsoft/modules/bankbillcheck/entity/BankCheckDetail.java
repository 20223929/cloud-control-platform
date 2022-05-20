package com.hgsoft.modules.bankbillcheck.entity;

import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 银行对账流水详情
 * Created by 吴鉴武 on 2021/7/19 17:07
 */
@Data
public class BankCheckDetail {
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
    @Excel(name = "车道号",width = 25)
    private String equipmentId;
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
    private String[] timeScope;
}
