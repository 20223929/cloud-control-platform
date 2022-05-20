package com.hgsoft.modules.bankbillcheck.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.modules.querymanage.enums.VehicleColor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbMerchantBillCheckDetailExportExcelVo extends TbMerchantBillCheckDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "拓展方", width = 25, orderNum = "1")
    private String merchantGroupIdStr;

    @Excel(name = "运营方", width = 25, orderNum = "2")
    private String merchantIdStr;

    @Excel(name = "场景", width = 25, orderNum = "3")
    private String siteIdStr;

    @Excel(name = "车牌颜色", width = 25, orderNum = "5")
    private String plateColorStr;

    @Excel(name = "交易模式", width = 25, orderNum = "9")
    private String transModelStr;

    @Excel(name = "出场时间", width = 25, orderNum = "10")
    private String exTimeStr;

    @Excel(name = "入场时间", width = 25, orderNum = "11")
    private String enTimeStr;

    @Excel(name = "停放时长", width = 25, orderNum = "12")
    private String stopTimeStr;

    @Excel(name = "交易金额(元)", width = 25, orderNum = "13")
    private String amountStr;

    @Excel(name = "交易时间", width = 25, orderNum = "14")
    private String transTimeStr;

    private static final String movePointToLeft2(Long fee) {
        if (fee == null)
            return "0.00";
        BigDecimal _temp = new BigDecimal(fee);
        return _temp.movePointLeft(2).toString();
    }

    public static TbMerchantBillCheckDetailExportExcelVo getInstance(TbMerchantBillCheckDetail e,
                                                                             Map<String, String> merchantInfo){
        TbMerchantBillCheckDetailExportExcelVo r = new TbMerchantBillCheckDetailExportExcelVo();
        BeanUtil.copyProperties(e,r);
        r.setMerchantGroupIdStr(merchantInfo.get(r.getMerchantGroupId()));
        r.setMerchantIdStr(merchantInfo.get(r.getMerchantId()));
        r.setSiteIdStr(merchantInfo.get(r.getSiteId()));

        Date exTime = r.getExTime();
        if (exTime != null) {
            r.setExTimeStr(DateUtil.format(exTime, "yyyy-MM-dd HH:mm:ss"));
        }
        Date enTime = r.getEnTime();
        if (enTime != null) {
            r.setEnTimeStr(DateUtil.format(enTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if (r.getTransTime() != null) {
            r.setTransTimeStr(DateUtil.format(r.getTransTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        r.setAmountStr(movePointToLeft2(r.getAmount()));
        if(enTime !=null&& exTime !=null){
            long diffMiniSecond = exTime.getTime() - enTime.getTime();
            Double miniSecond = Double.valueOf(String.valueOf(diffMiniSecond));
            Long diffMinutes = (exTime.getTime() - enTime.getTime())/(1000 * 60);
            r.setStopTimeStr(StrUtil.format("{}小时{}分钟",diffMinutes / 60,diffMinutes % 60));
        }
        Integer transModel = r.getTransModel();
        if(transModel!=null){
            switch (transModel){
                case 1 : r.setTransModelStr("ETC交易");break;
                case 2 : r.setTransModelStr("协议付");break;
                default: r.setTransModelStr("未知交易模式");
            }
        }
        String colorDescByColorNumber = VehicleColor.getColorDescByColorNumber(r.getPlateColor());
        r.setPlateColorStr(colorDescByColorNumber);
        return r;
    }
}
