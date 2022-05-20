package com.hgsoft.modules.bankbillcheck.entity;

import lombok.Data;

@Data
public class TbMerchantBillCheckDetailSearchVo {
    private String transactionId;//交易编号，退款时为退款编号
    private String merchantGroupId;//一级运营商
    private String merchantId;//二级运营商
    private String siteId;//三级运营商

    private String plateNum; //车牌
    private String etcCardId; //etc卡号
    private Integer transModel;//交易模式 1-ETC交易 2-协议付
    private String transDate;//交易日期
}
