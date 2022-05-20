package com.hgsoft.modules.bankbillcheck.entity;

import lombok.Data;

import java.util.List;

@Data
public class TBMerchantBillCheckExpPlatformEtcSearchVo {
    private String beginTransDate; // 开始 交易日期
    private String endTransDate;  // 结束 交易日期
    private String confirmState;//前端传过来的核对状态列表字符串(例如：”0,1“)
    private List<Integer> confirmStateList;//核对状态列表
    private Integer serviceType; //消费类型
    private String merchantGroupId;
    private String merchantId;
    private String siteId;
    private String confirmType;//对账模式，人工对账，自动对账
    private String[] timeScope;
}
