package com.hgsoft.modules.bankbillcheck.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "tb_merchant_bill_check_exp_platform_etc", resultMap = "BaseResultMap")
public class TBMerchantBillCheckExpPlatformEtc implements Serializable {

    /**
     * 以下基础表的字段
     */
    @Excel(name = "交易日期", width = 25, orderNum = "0")
    private String transDate;//交易日期
    private String merchantGroupId;//拓展方id
    private String merchantId;//运营方id
    private String siteId;//场景id

    @Excel(name = "确认后流水总数", width = 25, orderNum = "12")
    private Long totalCount;//确认后流水总数

    private Long totalAmount;//确认后流水总金额(分)

    @Excel(name = "备注", width = 25, orderNum = "15")
    private String remark;//备注

    private Date confirmTime;//核对时间
    private Integer confirmState;//0-待核对 1-异常确认 2-已确认 3-已核对

    /**
     * 还要有实时查询出来的字段
     */
    @Excel(name = "本系统总流水数", width = 25, orderNum = "4")
    private Long systemTotalCount; //本系统总流水数
    private Long systemTotalAmount;//本系统流水总金额(分）

    @Excel(name = "对账接口总流水数", width = 25, orderNum = "6")
    private Long otherTotalCount; //下游系统上传的总流水数
    private Long otherTotalAmount; //下游系统上传的总金额(分)

    @Excel(name = "确认人", width = 25, orderNum = "16")
    private String confirmMan; //确认人
    private Integer serviceType; //服务类型

    private Integer confirmTypeFlag;// 0-人工对账 1-自动对账

    private Integer refundCount;//退费流水数量

    private Integer eexitCount;//交易流水数量

    /**
     * 判断是否可以异常登记
     *
     * @return
     */
    public boolean canErrorRegister() {
        return confirmState == null || confirmState < 2;
    }

    /**
     * 判断是否可以确认登记
     *
     * @return
     */
    public boolean canConfirmRegister() {
        return confirmState == null || confirmState < 2;
    }

    public void triggerErrorRegister() {
        this.confirmState = 1;
        this.confirmTime = new Date();
    }

    public void triggerConfirmRegister() {
        this.confirmState = 2;
        this.confirmTime = new Date();
    }
}
