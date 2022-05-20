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
@ApiModel(value = "最近12个月交易流量金额统计", description = "最近12个月交易流量金额统计")
public class RecentCount implements Serializable {
    private static final long serialVersionUID = 3754234372163163668L;

    private List<Vehicle> recent_vehicle;
    private List<Amount> recent_amount;

    @Data
    public static class Vehicle implements Serializable{
        private static final long serialVersionUID = -4770606076501150766L;
        private String month;
        private Long count;
    }

    @Data
    public static class Amount implements Serializable {
        private static final long serialVersionUID = -3908919873167903836L;
        private String month;
        private Long amount;
    }

}
