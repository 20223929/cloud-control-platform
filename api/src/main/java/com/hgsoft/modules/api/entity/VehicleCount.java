package com.hgsoft.modules.api.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "过车交易统计", description = "过车交易统计")
public class VehicleCount implements Serializable {
    private static final long serialVersionUID = 3956074365405493525L;

    /**
     * 全网车流量
     */
    private Long vehicle_total;
    /**
     * 加油站车流量占比
     */
    private String vehicle_gas_station_ratio;
    /**
     * 停车场车流量占比
     */
    private String vehicle_parking_lot_ratio;
    /**
     * 全网交易额
     */
    private Long amount_total;
    /**
     * 加油站交易额占比
     */
    private String amount_gas_station_ratio;
    /**
     * 停车场交易额占比
     */
    private String amount_parking_lot_ratio;


}
