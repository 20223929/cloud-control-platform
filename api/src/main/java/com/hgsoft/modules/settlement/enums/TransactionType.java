package com.hgsoft.modules.settlement.enums;

import lombok.Getter;

/**
 * 流水类型枚举
 * Created by 吴鉴武 on 2021/5/13 9:08
 */
public enum TransactionType {

    PAY(1,"支付"),
    REFUND(2,"退费")
    ;
    @Getter
    private Integer type;
    @Getter
    private String desc;

    TransactionType(Integer type,String desc){
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(Integer type){
        if(type == null) return null;
        for (TransactionType value : TransactionType.values()) {
            if(value.type.equals(type)) return value.desc;
        }
        return "未知流水类型";
    }
}
