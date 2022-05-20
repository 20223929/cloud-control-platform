package com.hgsoft.modules.querymanage.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RefundStateEnum {
    WAITING_REFUND(0, "待退费"),
    REFUNDING(1, "退费中"),
    SUCCESS_REFUND(2, "退费成功"),
    FAIL_REFUND(3, "退费失败"),
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
        for (RefundStateEnum refundStateEnum : RefundStateEnum.values()) {
            if (refundStateEnum.value.equals(value)) return refundStateEnum.title;
        }
        return "未知";
    }
}
