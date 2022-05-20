package com.hgsoft.modules.bankbillcheck.entity;

import com.hgsoft.ecip.framework.core.entity.DataEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class TbBankBillCheckSearchVo extends DataEntity<TbBankBillCheckSearchVo> implements Serializable {
    private String beginTransDate; // 开始 交易日期
    private String endTransDate;  // 结束 交易日期
    private Integer confirmState;//核对状态
    private Integer serviceType; //消费类型
    private String merchantGroupId;
    private String merchantId;
    private String siteId;
    private String transDate;
    private String[] timeScope;
}
