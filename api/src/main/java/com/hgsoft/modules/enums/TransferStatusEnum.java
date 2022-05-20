package com.hgsoft.modules.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransferStatusEnum {
    WAIT_STA(0, "待转账"),
    HAS_STA(1, "已转账"),
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
        for (TransferStatusEnum staEnum : TransferStatusEnum.values()) {
            if (staEnum.value.equals(value)) return staEnum.title;
        }
        return "未知";
    }
}
