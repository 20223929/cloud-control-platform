package com.hgsoft.modules.merchant.enums;

import lombok.RequiredArgsConstructor;

/**
 * 服务类型
 *
 * @author zhanpu
 * @date 2021/4/14
 */
@RequiredArgsConstructor
public enum ServiceTypeEnum{

    PARKING_LOT(2, "停车场"),
    GAS_STATION(3, "加油站"),
    SERVICE_AREA(4, "服务区"),
    MUNICIPAL(5, "市政拓展"),
    CHARGING_PILE(6, "充电桩");

    private final Integer value;
    private final String title;


    public Integer getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static String getTitleByValue(Integer value){
        if(value == null) return null;
        for (ServiceTypeEnum serviceTypeEnum : ServiceTypeEnum.values()) {
            if(serviceTypeEnum.value.equals(value)) return serviceTypeEnum.title;
        }
        return "未知";
    }

    public static void main(String[] args) {
        System.out.println(ServiceTypeEnum.values());
    }
}
