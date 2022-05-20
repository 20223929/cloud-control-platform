package com.hgsoft.modules.report.entity.shanxi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.modules.utils.ShowSelfRole;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class EtcOffLineSettlement implements Serializable, ShowSelfRole {

    //查询条件
    private String merchantGroupId;//拓展方id
    private String merchantId;//运营方id
    private String siteId;//场景id
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginSearchDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endSearchDate;
    private Integer searchScope;//统计方式 1-按年 2-按月 3-按日 4-按范围

    //@Excel(name = "拓展方", width = 25)
    private String merchantGroupIdStr;

    @Excel(name = "运营方", width = 25)
    private String merchantIdStr;

    @Excel(name = "场景", width = 25)
    private String siteIdStr;

    @Excel(name = "交易日期", width = 25)
    private String transDate;

    @Excel(name = "交易流水数", width = 25)
    private Long totalCount;
    private Long totalAmount;
    @Excel(name = "交易金额(元)", width = 25)
    private String totalAmountStr;

    @Excel(name = "已扣款未结算流水数", width = 25)
    private Long deductionCount;
    private Long deductionAmount;
    @Excel(name = "已扣款未结算流水金额(元)", width = 25)
    private String deductionAmountStr;

    @Excel(name = "已扣款已结算流水数", width = 25)
    private Long settlementCount;
    private Long settlementAmount;
    @Excel(name = "已扣款已结算流水金额(元)", width = 25)
    private String settlementAmountStr;

    @Excel(name = "未扣款流水数", width = 25)
    private Long unDeductionCount;
    private Long unDeductionAmount;
    @Excel(name = "未扣款流水金额(元)", width = 25)
    private String unDeductionAmountStr;

    /**
     * 扣款流水数小计
     */
    private Long deductionAndSettlementSubCount;

    /**
     * 扣款流水金额小计
     */
    private String deductionAndSettlementSubAmountStr;

    @Override
    public void triggerShowState(Map<String, String> merchantInfo) {
        setMerchantGroupIdStr(merchantInfo.get(this.getMerchantGroupId()));
        setMerchantIdStr(merchantInfo.get(this.getMerchantId()));
        setSiteIdStr(merchantInfo.get(this.getSiteId()));
        setSettlementAmountStr(movePointToLeft2(getSettlementAmount()));
        setDeductionAmountStr(movePointToLeft2(getDeductionAmount()));
        setUnDeductionAmountStr(movePointToLeft2(getUnDeductionAmount()));
        setTotalAmountStr(movePointToLeft2(getTotalAmount()));
        setDeductionAndSettlementSubCount(getDeductionCount()+getSettlementCount());
        setDeductionAndSettlementSubAmountStr(movePointToLeft2(getSettlementAmount()+getDeductionAmount()));
    }
}
