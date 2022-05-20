package com.hgsoft.modules.bankbillcheck.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "tb_merchant_bill_check_detail", resultMap = "BaseResultMap")
public class TbMerchantBillCheckDetail implements Serializable {


    @Excel(name = "交易流水号", width = 25, orderNum = "7")
    private String transactionId;//交易编号，退款时为退款编号
    private String merchantGroupId;//一级运营商
    private String merchantId;//二级运营商
    private String siteId;//三级运营商
    private String equipmentId;//四级设备编号
    private Long amount;//交易金额
    private Long discAmount;//优惠金额
    private Long paidAmount;//实付金额
    private Date transTime;//交易时间
    private Integer transType;//交易类型，1-交易、2-退款
    private Integer transModel;//交易模式 1-ETC交易 2-协议付
    private String remark;//备注
    private Date createTime;//创建时间

    @Excel(name = "车牌号码", width = 25, orderNum = "4")
    private String plateNum; //车牌
    private Integer plateColor; //车牌颜色

    @Excel(name = "ETC卡号", width = 25, orderNum = "6")
    private String etcCardId; //etc卡号

    @Excel(name = "银行订单号", width = 25, orderNum = "8")
    private String bankDeductionOrderId;//银行订单号

    private Date exTime;//出场时间
    private Date enTime;//入场时间

}