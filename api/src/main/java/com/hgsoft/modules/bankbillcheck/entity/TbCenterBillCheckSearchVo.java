package com.hgsoft.modules.bankbillcheck.entity;

import com.hgsoft.ecip.framework.core.entity.DataEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class TbCenterBillCheckSearchVo extends DataEntity<TbCenterBillCheck> implements Serializable {
    private String beginTransDate; // 开始 交易日期
    private String endTransDate;  // 结束 交易日期
    private Integer confirmState;//核对状态
    private String merchantGroupId;
    private String merchantId;
    private String siteId;
    private String transDate;
    private String confirmMan;
    private String secondConfirmMan;
    private String remark;
    private String secondConfirmRemark;
}
