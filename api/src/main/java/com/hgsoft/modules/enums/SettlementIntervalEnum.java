package com.hgsoft.modules.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SettlementIntervalEnum {
    T_1(1, "T+1"),
    T_4(2, "T+4"),
    MONTH(3, "月结"),
    ;

    private final Integer value;
    private final String title;

    public Integer getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public static String getTitleByValue(Integer value) {
        if (value == null) return null;
        for (SettlementIntervalEnum intervalEnum : SettlementIntervalEnum.values()) {
            if (intervalEnum.value.equals(value)) return intervalEnum.title;
        }
        return "未知";
    }
}
