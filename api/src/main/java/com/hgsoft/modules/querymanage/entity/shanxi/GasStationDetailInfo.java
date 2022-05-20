package com.hgsoft.modules.querymanage.entity.shanxi;

import com.hgsoft.ecip.auto.poi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 加油站详情信息
 * Created by 吴鉴武 on 2021/5/6 17:01
 */
@Data
public class GasStationDetailInfo {

    /**
     * 操作人ID
     */
    private String operatorId;
    /**
     * 加油量（毫升） 大于0
     */
    @Excel(name = "加油量(升)", width = 25, exportConvert = true)
    private BigDecimal fuelVolume;

    public BigDecimal convertgetFuelVolume() {
        return this.fuelVolume.setScale(3, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 油品类型
     */
    private String fuelType;
    /**
     * 油品单价（分/升） 大于0
     */
    private BigDecimal fuelPrice;
}
