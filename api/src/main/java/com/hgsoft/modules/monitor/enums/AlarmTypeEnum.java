package com.hgsoft.modules.monitor.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AlarmTypeEnum {
    BLACK_ALARM(1, "黑名单异常"),
    ACCOUNT_ALARM(2, "对账异常"),
    CHARGE_ALARM(3, "收费异常"),
    SERVER_ALARM(4, "服务器异常"),
    NETWORK_ALARM(5, "网络异常"),
    APPLICATION_ALARM(6, "应用异常"),
    LANE_ALARM(7, "车道监控异常"),
    ;

    private final Integer value;
    private final String title;

    public static String getTitleByValue(Integer value) {
        if (value == null) return null;
        for (AlarmTypeEnum alarmTypeEnum : AlarmTypeEnum.values()) {
            if (alarmTypeEnum.value.equals(value)) return alarmTypeEnum.title;
        }
        return "未知";
    }
}
