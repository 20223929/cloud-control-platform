package com.hgsoft.modules.monitor.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DealStateEnum {
    NO_DEAL(0, "未处理"),
    HAS_DEAL(1, "已处理"),
    ;

    @Getter
    private final Integer value;
    @Getter
    private final String title;

    public static String getTitleByValue(Integer value) {
        if (value == null) return null;
        for (DealStateEnum dealStateEnum : DealStateEnum.values()) {
            if (dealStateEnum.value.equals(value)) return dealStateEnum.title;
        }
        return "未知";
    }
}
