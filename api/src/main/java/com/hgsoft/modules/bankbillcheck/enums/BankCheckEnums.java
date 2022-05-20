package com.hgsoft.modules.bankbillcheck.enums;

import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

/**
 * 银行对账枚举
 * Created by 吴鉴武 on 2021/7/16 13:56
 */
public interface BankCheckEnums {

    int getCode();
    String getDesc();

    static String getDescByCode(BankCheckEnums[] bankCheckEnums,int code){
        return Stream.of(bankCheckEnums).filter(vo->vo.getCode() == code).map(vo->vo.getDesc()).findFirst().orElse(null);
    }

    @RequiredArgsConstructor
    enum CheckMode implements BankCheckEnums{
        AUTO_CHECK(1,"自动对账"),
        MANUAL_CHECK(2,"人工对账");

        private final int code;
        private final String desc;
        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }
    }

    @RequiredArgsConstructor
    enum CheckStatus implements BankCheckEnums{
        WAIT_CHECK(1,"待核对"),
        CHECKED(2,"已核对"),
        CONFITM_EXCEPTION(3,"异常登记"),
        CONFIRMED(4,"已确认");

        private final int code;
        private final String desc;
        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }
    }
}
