package com.hgsoft.modules.monitor.enums;

import com.hgsoft.modules.querymanage.enums.RefundTypeEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AlarmLevelEnum {
    NORMAL(1, "一般"),
    SERIOUS(2, "严重"),
    ;

    private final Integer value;
    private final String title;

    public static String getTitleByValue(Integer value) {
        if (value == null) return null;
        for (AlarmLevelEnum alarmLevelEnum : AlarmLevelEnum.values()) {
            if (alarmLevelEnum.value.equals(value)) return alarmLevelEnum.title;
        }
        return "未知";
    }
}
