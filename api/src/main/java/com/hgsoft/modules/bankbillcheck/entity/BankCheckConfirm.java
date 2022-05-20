package com.hgsoft.modules.bankbillcheck.entity;

import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 对账确认实体类
 * Created by 吴鉴武 on 2021/7/20 14:50
 */
@Data
public class BankCheckConfirm {

    private String tradeDay;
    private String merchantGroupId;
    private String merchantGroupName;
    private String merchantId;
    private String merchantName;
    private String siteId;
    private String siteName;
    private Integer totalConfirmCount;
    private BigDecimal totalConfirmAmount;
    private String remark;
    private Date confirmTime;
    private String confirmMan;
    private Integer confirmState;
}
