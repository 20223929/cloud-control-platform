package com.hgsoft.modules.querymanage.enums;

import lombok.RequiredArgsConstructor;

/**
 * 停车场流水状态枚举
 * Created by 吴鉴武 on 2021/6/1 14:52
 */
@RequiredArgsConstructor
public enum  ParkEexitStatusEnum {
    WAIT_ACCOUNT(1,"待记账"),
    WAIT_CLEAR(2,"待清分"),
    CLEAR_CHECK_EXCEPTION(3,"清分校验异常"),
    WAIT_SETTLE(4,"待结算"),
    SETTLE(5,"已结算"),
    EXP_PLATFORM_CHECK_EXCEPTION(6,"拓展平台校验异常"),
    ONLINE_DEDUCTION_SUCCESS(7,"联机交易成功")
    ;

    private final int value;
    private final String title;

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static String getTitleByValue(Integer value){
        if (value == null) return null;
        for (ParkEexitStatusEnum parkEexitStatusEnum : ParkEexitStatusEnum.values()) {
            if(parkEexitStatusEnum.value == value.intValue()) return parkEexitStatusEnum.title;
        }
        return "未知状态";
    }
}
