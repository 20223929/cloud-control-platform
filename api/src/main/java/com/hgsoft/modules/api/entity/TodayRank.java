package com.hgsoft.modules.api.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "今日交易额排行查询", description = "今日交易额排行查询")
public class TodayRank implements Serializable {
    private static final long serialVersionUID = 8749988444290920415L;

    /**
     * 加油站排行榜
     */
    private List<GasStation> gas_station_rank;
    /**
     * 加油站排行榜
     */
    private List<ParkLot> parking_lot_rank;

    @Data
    public static class GasStation implements Serializable {
        private static final long serialVersionUID = -1968591565035426336L;
        /**
         * 拓展场所名称
         */
        private String name;
        /**
         * 交易额
         */
        private Long amount;

    }

    @Data
    public static class ParkLot implements Serializable {
        private static final long serialVersionUID = -9135424330852531648L;
        /**
         * 拓展场所名称
         */
        private String name;
        /**
         * 交易额
         */
        private Long amount;
    }
}
