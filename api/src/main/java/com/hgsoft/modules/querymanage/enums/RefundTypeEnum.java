package com.hgsoft.modules.querymanage.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RefundTypeEnum {
    ALL_FEE(1, "全额退费"),
    PART_FEE(2, "部分退费"),
    ;

    private final Integer value;
    private final String title;

    public static String getTitleByValue(Integer value) {
        if (value == null) return null;
        for (RefundTypeEnum refundTypeEnum : RefundTypeEnum.values()) {
            if (refundTypeEnum.value.equals(value)) return refundTypeEnum.title;
        }
        return "未知";
    }
}
