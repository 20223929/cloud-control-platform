package com.hgsoft.modules.settlement.entity;

import com.hgsoft.ecip.framework.core.entity.DataEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class SettlementSearchVo extends DataEntity<SettlementSearchVo> implements Serializable {
    private String beginClearDate; // 开始 交易日期
    private String endClearDate;  // 结束 交易日期
    private Integer confirmState;//核对状态
    private String merchantGroupId;
    private String merchantId;
    private String siteId;
    private String transDate;
    private Integer serviceType;

    private String clearDate;
    private String vehiclePlate;
    private Integer vehicleColor;
    private String etcCardId;
    private String transactionId;
    private String[] timeScope;
}
