package com.hgsoft.modules.querymanage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import com.hgsoft.modules.querymanage.enums.VehicleColor;
import com.hgsoft.modules.utils.ServiceTypeMapToNameUtil;
import com.hgsoft.modules.utils.ShowSelfRole;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@TableName(value = "tb_etc_vehicle_eexit", resultMap = "BaseResultMap")
public class TbEtcVehicleEexit implements Serializable, ShowSelfRole {
    private String merchantGroupId;//拓展方id
    private String merchantId;//运营方id
    private String siteId;//场景id
    private Long merchantRealFee;//计费金额分
    private Integer vehicleColor;//车牌颜色
    private Integer serviceType;
    private String[] timeScope;

    @Excel(name = "服务类型", width = 25)
    private String serviceTypeStr;

    @Excel(name = "车牌号码", width = 25)
    private String vehiclePlate;

    @Excel(name = "车牌颜色", width = 25)
    private String vehicleColorStr;

    @Excel(name = "ETC卡号", width = 25)
    private String etcCardId;

    @Excel(name = "计费金额(元)", width = 25)
    private String merchantRealFeeStr;

    @Excel(name = "拓展方", width = 25)
    private String merchantGroupIdStr;

    @Excel(name = "运营方", width = 25)
    private String merchantIdStr;

    @Excel(name = "场景", width = 25)
    private String siteIdStr;

    @Excel(name = "入场时间", width = 25,exportFormat = "yyyy-mm-dd HH:MM:ss")
    private Date enTime;

    @Excel(name = "出场时间", width = 25,exportFormat = "yyyy-mm-dd HH:MM:ss")
    private Date exTime;

    @Excel(name = "采集时间", width = 25,exportFormat = "yyyy-mm-dd HH:MM:ss")
    private Date updateTime;

    /**
     * 让自己变成能展示的状态
     */
    public void triggerShowState(Map<String, String> merchantInfo){
        this.setMerchantGroupIdStr(merchantInfo.get(this.getMerchantGroupId()));
        this.setMerchantIdStr(merchantInfo.get(this.getMerchantId()));
        this.setSiteIdStr(merchantInfo.get(this.getSiteId()));
        this.setServiceTypeStr(ServiceTypeMapToNameUtil.getServiceTypeName(this.getServiceType()));
        this.setVehicleColorStr(VehicleColor.getColorDescByColorNumber(this.getVehicleColor()));
        this.setMerchantRealFeeStr(movePointToLeft2(this.getMerchantRealFee()));
    }

    //查询条件
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginSearchDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endSearchDate;
}
