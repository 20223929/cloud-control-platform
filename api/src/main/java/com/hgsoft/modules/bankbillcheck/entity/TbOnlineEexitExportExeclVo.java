package com.hgsoft.modules.bankbillcheck.entity;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.modules.querymanage.enums.VehicleColor;
import com.hgsoft.modules.utils.ServiceTypeMapToNameUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 联机交易流水表导出的流水明细 Entity
 *
 * @author 何志豪
 * @version 2021-06-18 05:28:43
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbOnlineEexitExportExeclVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Length(min = 1, max = 40, message = "交易流水号长度必须介于 1 和 40 之间")
    @Excel(name = "交易流水号", width = 25)
    private String transactionId;        // 交易流水号

    private Integer serviceType;        // 服务类型
    private String merchantGroupId;        // 一级运营商
    private String merchantId;        // 二级运营商
    private String siteId;        // 三级运营商
    private Integer vehicleColor;        // 车牌颜色

    @Excel(name = "服务类型", width = 25)
    private String serviceTypeStr;        // 服务类型

    @Excel(name = "拓展方", width = 25)
    private String merchantGroupIdStr;        // 一级运营商

    @Excel(name = "运营方", width = 25)
    private String merchantIdStr;        // 二级运营商

    @Excel(name = "场景", width = 25)
    private String siteIdStr;        // 三级运营商

    @Length(min = 1, max = 60, message = "四级设备编号长度必须介于 1 和 60 之间")
    @Excel(name = "四级设备编号", width = 25)
    private String equipmentId;        // 四级设备编号

    @Length(min = 1, max = 60, message = "设备编号长度必须介于 1 和 60 之间")
    @Excel(name = "设备编号", width = 25)
    private String deviceId;        // 设备编号

    @Length(min = 1, max = 20, message = "车牌号码长度必须介于 1 和 20 之间")
    @Excel(name = "车牌号码", width = 25)
    private String vehiclePlate;        // 车牌号码

    @Excel(name = "车牌颜色", width = 25)
    private String vehicleColorStr;        // 车牌颜色

    @NotNull(message = "车型不能为空")
    @Excel(name = "车型", width = 25)
    private Integer vehicleType;        // 车型

    @NotNull(message = "入场时间不能为空")
    @Excel(name = "入场时间", width = 25, format = "yyyy-MM-dd HH:MM:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    private Date enTime;        // 入场时间

    @NotNull(message = "出场时间不能为空")
    @Excel(name = "出场时间", width = 25, format = "yyyy-MM-dd HH:MM:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    private Date exTime;        // 出场时间

    @NotNull(message = "交易时间不能为空")
    @Excel(name = "交易时间", width = 25, format = "yyyy-MM-dd HH:MM:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    private Date transTime;        // 交易时间

    @Length(min = 0, max = 20, message = "签约银行长度必须介于 0 和 20 之间")
    @Excel(name = "签约银行", width = 25)
    private String signBank;        // 签约银行

    @Length(min = 0, max = 4000, message = "具体应用字段长度必须介于 0 和 4000 之间")
    @Excel(name = "具体应用字段", width = 25)
    private String detail;        // 具体应用字段

    @Excel(name = "对账结果", width = 25)
    private Integer billResult;        // 对账结果

    @Excel(name = "对账时间", width = 25, format = "yyyy-MM-dd HH:MM:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    private Date billTime;        // 对账时间

    private Integer merchantPayFee;        // 应收金额，单位：分
    @Excel(name = "应收金额", width = 25)
    private String merchantPayFeeStr;

    private Integer merchantRealFee;        // 实收金额，单位：分；实收金额 = 应收金额 - 优惠金额
    @Excel(name = "实收金額", width = 25)
    private String merchantRealFeeStr;

    private Integer merchantDiscountFee;        // 优惠金额，单位：分
    @Excel(name = "优惠金额", width = 25)
    private String merchantDiscountFeeStr;

    private Double merchantSettlementFee;        // 商户结算金额，单位：分
    @Excel(name = "商户结算金额", width = 25)
    private String merchantSettlementFeeStr;        // 商户结算金额，单位：分

    private Double clearServiceFee;        // 清算服务费，单位：分
    @Excel(name = "清算服务费", width = 25)
    private String clearServiceFeeStr;        // 清算服务费，单位：分

    @NotNull(message = "清算服务费费率不能为空")
    @Excel(name = "清算服务费费率", width = 25)
    private Double clearServiceFeeRate;        // 清算服务费费率

    private Double bankServiceFee;        // 银行手续费，单位：分
    @Excel(name = "银行手续费", width = 25)
    private String bankServiceFeeStr;

    @NotNull(message = "银行手续费费率不能为空")
    @Excel(name = "银行手续费费率", width = 25)
    private Double bankServiceFeeRate;        // 银行手续费费率

    @Excel(name = "扣款交易流水号", width = 25)
    private String deductionOrderId;        // 扣款交易流水号

    private Integer deductionState;        // 扣款状态，1-扣款成功、2-扣款失败
    @Excel(name = "扣款状态", width = 25)
    private String deductionStateStr;        // 扣款状态，1-扣款成功、2-扣款失败

    @Excel(name = "扣款时间", width = 25, format = "yyyy-MM-dd HH:MM:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    private Date deductionTime;        // 扣款时间

    private Integer deductionFee;        // 扣款金额，单位：分
    @Excel(name = "扣款金额", width = 25)
    private String deductionFeeStr;        // 扣款金额，单位：分

    private Integer bankDiscountFee;        // 银行优惠金额，单位：分
    @Excel(name = "银行优惠金额", width = 25)
    private String bankDiscountFeeStr;        // 银行优惠金额，单位：分

    private Integer bankRealDeductionFee;        // 银行实扣金额，单位：分；银行实扣金额= 扣款金额 - 银行优惠金额
    @Excel(name = "银行实扣金额", width = 25)
    private String bankRealDeductionFeeStr;        // 银行实扣金额，单位：分；银行实扣金额= 扣款金额 - 银行优惠金额

    @Length(min = 0, max = 60, message = "银行订单号长度必须介于 0 和 60 之间")
    @Excel(name = "银行订单号", width = 25)
    private String bankDeductionOrderId;        // 银行订单号

    @NotNull(message = "创建时间不能为空")
    @Excel(name = "创建时间", width = 25, format = "yyyy-MM-dd HH:MM:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    private Date createTime;        // 创建时间

    @Excel(name = "更新时间", width = 25, format = "yyyy-MM-dd HH:MM:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;        // 更新时间

    private static final String movePointToLeft2(Integer fee) {
        if (fee == null)
            return "0";
        BigDecimal _temp = new BigDecimal(fee);
        return _temp.movePointLeft(2).toString();
    }

    public static TbOnlineEexitExportExeclVo transform(TbOnlineEexit eexit, Map<String, String> merchantInfo) {
        TbOnlineEexitExportExeclVo r = new TbOnlineEexitExportExeclVo();
        BeanUtil.copyProperties(eexit, r, false);
        //这里开始要对r的字段进行修饰
        r.setServiceTypeStr(ServiceTypeMapToNameUtil.getServiceTypeName(r.getServiceType()));
        r.setMerchantGroupIdStr(merchantInfo.get(r.getMerchantGroupId()));
        r.setMerchantIdStr(merchantInfo.get(r.getMerchantId()));
        r.setSiteIdStr(merchantInfo.get(r.getSiteId()));
        r.setVehicleColorStr(VehicleColor.getColorDescByColorNumber(r.getVehicleColor()));
        r.setMerchantPayFeeStr(movePointToLeft2(r.getMerchantPayFee()));
        r.setMerchantRealFeeStr(movePointToLeft2(r.getMerchantRealFee()));
        r.setMerchantDiscountFeeStr(movePointToLeft2(r.getMerchantDiscountFee()));
        r.setMerchantSettlementFeeStr(r.getMerchantSettlementFee().toString());
        r.setClearServiceFeeStr(r.getClearServiceFee().toString());
        r.setBankServiceFeeStr(r.getBankServiceFee().toString());
        Integer deductionState = r.getDeductionState();
        String deductionStateStr = null;
        switch (deductionState) {
            case 1:
                deductionStateStr = "扣款成功";
                break;
            case 2:
                deductionStateStr = "扣款失败";
                break;
            default:
                deductionStateStr = "扣款状态未知";
        }
        r.setDeductionStateStr(deductionStateStr);
        r.setDeductionFeeStr(movePointToLeft2(r.getDeductionFee()));
        r.setBankDiscountFeeStr(movePointToLeft2(r.getBankDiscountFee()));
        r.setBankRealDeductionFeeStr(movePointToLeft2(r.getBankRealDeductionFee()));
        return r;
    }
}