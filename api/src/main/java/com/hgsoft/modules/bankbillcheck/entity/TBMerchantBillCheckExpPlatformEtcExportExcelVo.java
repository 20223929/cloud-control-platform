package com.hgsoft.modules.bankbillcheck.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.modules.utils.ServiceTypeMapToNameUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 用于商户对账信息用于导出与页面展示
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TBMerchantBillCheckExpPlatformEtcExportExcelVo extends TBMerchantBillCheckExpPlatformEtc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "拓展方", width = 25, orderNum = "1")
    private String merchantGroupIdStr;

    @Excel(name = "运营方", width = 25, orderNum = "2")
    private String merchantIdStr;

    @Excel(name = "场景", width = 25, orderNum = "3")
    private String siteIdStr;

    @Excel(name = "本系统流水应收总金额(元)", width = 25, orderNum = "5")
    private String systemTotalAmountStr;

    @Excel(name = "对账接口总金额(元)", width = 25, orderNum = "7")
    private String otherTotalAmountStr;

    @Excel(name = "确认后实收总金额(元)", width = 25, orderNum = "13")
    private String totalAmountStr;

    @Excel(name = "流水差异数", width = 25, orderNum = "8")
    private Long diffTotalCount;

    @Excel(name = "流水差异总金额(元)", width = 25, orderNum = "9")
    private String diffTotalAmountStr;

    @Excel(name = "核对时间", width = 25, orderNum = "14")
    private String confirmTimeStr;

    @Excel(name = "核对状态", width = 25, orderNum = "11")
    private String confirmStateStr;

    @Excel(name = "对账模式", width = 25, orderNum = "10")
    private String confirmType;

    @Excel(name = "服务类型", width = 25, orderNum = "15")
    private String serviceTypeStr;

    private TBMerchantBillCheckExpPlatformEtcExportExcelVo() {
    }

    private static final String movePointToLeft2(Long fee) {
        if (fee == null)
            return null;
        BigDecimal _temp = new BigDecimal(fee);
        return _temp.movePointLeft(2).toString();
    }

    public static TBMerchantBillCheckExpPlatformEtcExportExcelVo getInstance(TBMerchantBillCheckExpPlatformEtc e,
                                                                             Map<String, String> merchantInfo) {
        TBMerchantBillCheckExpPlatformEtcExportExcelVo r = new TBMerchantBillCheckExpPlatformEtcExportExcelVo();
        BeanUtil.copyProperties(e, r);
        r.setMerchantGroupIdStr(merchantInfo.get(r.getMerchantGroupId()));
        r.setMerchantIdStr(merchantInfo.get(r.getMerchantId()));
        r.setSiteIdStr(merchantInfo.get(r.getSiteId()));
        r.setTotalAmountStr(movePointToLeft2(r.getTotalAmount()));
        r.setSystemTotalAmountStr(movePointToLeft2(r.getSystemTotalAmount()));
        r.setOtherTotalAmountStr(movePointToLeft2(r.getOtherTotalAmount()));
        Long otherTotalCount = r.getOtherTotalCount();
        Long systemTotalCount = r.getSystemTotalCount();
        Long systemTotalAmount = r.getSystemTotalAmount();
        Long otherTotalAmount = r.getOtherTotalAmount();

        if (r.getConfirmTypeFlag() > 0) {
            r.setDiffTotalCount(systemTotalCount - otherTotalCount);
            r.setDiffTotalAmountStr(movePointToLeft2(systemTotalAmount - otherTotalAmount));
            r.setConfirmType("自动对账");
        } else {
            r.setOtherTotalAmountStr(null);
            r.setOtherTotalCount(null);
            r.setConfirmType("人工对账");
        }
        Integer confirmState = r.getConfirmState();
        if (r.getConfirmTypeFlag() > 0 && r.getDiffTotalCount() == 0 && Double.valueOf(r.getDiffTotalAmountStr()) == 0) {
            r.setConfirmStateStr("已确认");
            r.setTotalAmount(r.getSystemTotalAmount());
            r.setTotalCount(r.getSystemTotalCount());
            r.setTotalAmountStr(r.getSystemTotalAmountStr());
            r.setConfirmState(2);
        } else if (confirmState == null) {
            r.setConfirmState(0);
            r.setConfirmStateStr("待核对");
            r.setTotalAmount(null);
            r.setTotalCount(null);
            r.setTotalAmountStr(null);
        } else {
            switch (confirmState) {
                case 1:
                    r.setConfirmStateStr("确认异常");
                    break;
                case 2:
                    r.setConfirmStateStr("已确认");
                    break;
                default:
                    r.setConfirmStateStr("待核对");
            }
        }
        Integer serviceType = r.getServiceType();
        if (serviceType != null)
            r.setServiceTypeStr(ServiceTypeMapToNameUtil.getServiceTypeName(serviceType));
        if (r.getConfirmTime() != null) {
            r.setConfirmTimeStr(DateUtil.format(r.getConfirmTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        return r;
    }

    /**
     * 用于得到总计或合计的对象
     *
     * @param e
     * @return
     */
    public static TBMerchantBillCheckExpPlatformEtcExportExcelVo getSumTotalInstance(TBMerchantBillCheckExpPlatformEtc e, String MerchantGroupName) {
        TBMerchantBillCheckExpPlatformEtcExportExcelVo r = new TBMerchantBillCheckExpPlatformEtcExportExcelVo();
        BeanUtil.copyProperties(e, r);
        r.setTotalAmountStr(movePointToLeft2(r.getTotalAmount()));
        r.setSystemTotalAmountStr(movePointToLeft2(r.getSystemTotalAmount()));
        r.setOtherTotalAmountStr(movePointToLeft2(r.getOtherTotalAmount()));
        if (r.getSystemTotalCount() == null) {
            r.setSystemTotalCount(0L);
        }
        if (r.getOtherTotalCount() == null) {
            r.setOtherTotalCount(0L);
        }
        r.setMerchantGroupIdStr(MerchantGroupName);
        r.setTotalCount(null);
        r.setTotalAmount(null);
        r.setTotalAmountStr(null);
        return r;
    }
}
