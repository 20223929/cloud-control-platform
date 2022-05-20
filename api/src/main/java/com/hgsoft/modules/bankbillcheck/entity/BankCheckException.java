package com.hgsoft.modules.bankbillcheck.entity;

import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.modules.merchantcommon.UserMerchantPermissionswrapper;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 异常流水实体类
 * Created by 吴鉴武 on 2021/7/19 14:52
 */
@Data
public class BankCheckException {
    @Excel(name = "车牌号码",width = 25)
    private String vehicleNumber;
    private Integer vehicleColor;
    @Excel(name = "车牌颜色",width = 25)
    private String vehicleColorName;
    private String cardId;
    private String signBankNumber;
    @Excel(name = "交易流水号",width = 25)
    private String transactionId;
    @Excel(name = "订单号",width = 25)
    private String orderId;
    @Excel(name = "交易模式",width = 25)
    private String tradeModeName;
    private String merchantGroupId;
    @Excel(name = "拓展方",width = 25)
    private String merchantGroupName;
    private String merchantId;
    @Excel(name = "运营方",width = 25)
    private String merchantName;
    private String siteId;
    @Excel(name = "场景",width = 25)
    private String siteName;
    @Excel(name = "入场时间",width = 25)
    private String enTime;
    @Excel(name = "出场时间",width = 25)
    private String exTime;
    @Excel(name = "停放时间",width = 25)
    private String parkTime;
    @Excel(name = "交易金额(元)",width = 25)
    private BigDecimal fee;
    @Excel(name = "交易时间",width = 25)
    private String tradeTime;
    @Excel(name = "状态",width = 25)
    private String statusName;
    private String[] timeScope;
}
