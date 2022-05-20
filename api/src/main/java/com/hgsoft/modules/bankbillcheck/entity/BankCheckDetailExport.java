package com.hgsoft.modules.bankbillcheck.entity;

import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 流水明细导出实体类
 * Created by 吴鉴武 on 2021/7/20 9:04
 */
@Data
public class BankCheckDetailExport {

    @Excel(name = "交易流水号",width = 25)
    private String transactionId;
    private Integer serviceType;
    @Excel(name = "服务类型",width = 25)
    private String serviceTypeName;
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
    @Excel(name = "车牌号码",width = 25)
    private String vehicleNumber;
    private Integer vehicleColor;
    @Excel(name = "车牌颜色",width = 25)
    private String vehicleColorName;
    private Integer vehicleType;
    @Excel(name = "车型",width = 25)
    private String vehicleTypeName;
    @Excel(name = "入场时间",width = 25)
    private String enTime;
    @Excel(name = "出场时间",width = 25)
    private String exTime;
    @Excel(name = "交易时间",width = 25)
    private String transTime;
    @Excel(name = "商户应收金额(元)",width = 25)
    private String merchantPayFee;
    @Excel(name = "商户实收金额(元)",width = 25)
    private String merchantRealFee;
    @Excel(name = "商户优惠金额(元)",width = 25)
    private String merchantDiscountFee;
    @Excel(name = "商户结算金额(元)",width = 25)
    private String merchantSettlementFee;
    @Excel(name = "银行订单号",width = 25)
    private String bankDeductionOrderId;
}
