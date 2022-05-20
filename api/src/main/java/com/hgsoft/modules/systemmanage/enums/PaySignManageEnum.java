package com.hgsoft.modules.systemmanage.enums;

import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

/**
 * 签约信息管理枚举类
 * Created by 吴鉴武 on 2021/6/4 14:37
 */
public interface PaySignManageEnum {
    Integer getValue();
    String getDesc();
    static String getDescByValue(PaySignManageEnum[] array,Integer value){
        return Stream.of(array).filter(vo->vo.getValue().equals(value)).map(vo->vo.getDesc()).findFirst().orElse(null);
    }

    @RequiredArgsConstructor
    enum IcbcCardLogo implements PaySignManageEnum{
        ICBC(0,"工行卡"),
        OTHER(2,"银联他行卡")
        ;

        private final Integer value;
        private final String desc;
        @Override
        public Integer getValue() {
            return this.value;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }
    }

    @RequiredArgsConstructor
    enum ZhihuifuCardLogo implements PaySignManageEnum{
        ICBC(1,"工商银行"),
        PBOC(2,"中国银行"),
        CCB(3,"建设银行"),
        SPD(4,"浦发银行"),
        CITIC(5,"中信银行"),
        CMSB(6,"民生银行"),
        BOS(7,"上海银行"),
        BOB(8,"北京银行"),
        BOBEC(9,"渤海银行"),
        RCU(10,"广西农信"),
        ABC(11,"农业银行"),
        PAYH(12,"平安银行"),
        CMBC(13,"招商银行"),
        PSBC(14,"邮储银行");

        private final Integer value;
        private final String desc;
        @Override
        public Integer getValue() {
            return this.value;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }
    }

    @RequiredArgsConstructor
    enum SignState implements PaySignManageEnum{
        UN_SIGN(0,"未签约"),
        SIGN_SUCCESS(1,"协议正常"),
        SIGN_CANCEL(2,"协议注销"),
        SIGN_HANDLING(3,"协议签订中");

        private final Integer value;
        private final String desc;
        @Override
        public Integer getValue() {
            return this.value;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }
    }

    @RequiredArgsConstructor
    enum SignStateSC implements PaySignManageEnum{
        AUTH(1,"已授权"),
        UNAUTH(2,"未授权");

        private final Integer value;
        private final String desc;
        @Override
        public Integer getValue() {
            return this.value;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }
    }

    @RequiredArgsConstructor
    enum status implements PaySignManageEnum{
        OPEN(1,"开启"),
        CLOSE(2,"关闭");

        private final Integer value;
        private final String desc;
        @Override
        public Integer getValue() {
            return this.value;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }
    }

    @RequiredArgsConstructor
    enum VehicleType implements PaySignManageEnum{
        ONE(1,"一型客车"),
        TWO(2,"二型客车"),
        THREE(3,"三型客车"),
        FOUR(4,"四型客车"),
        ELEVENT(11,"一型货车"),
        TWELVE(12,"二型货车"),
        THIRTEEN(13,"三型货车"),
        FOURTEEN(14,"四型货车"),
        FIFTEEN(15,"五型货车"),
        SIXTEEN(16,"六型货车"),
        TWENTY_ONE(21,"一型专项作业车"),
        TWENTY_TWO(22,"二型专项作业车"),
        TWENTY_THREE(23,"三型专项作业车"),
        TWENTY_FOUR(24,"四型专项作业车"),
        TWENTY_FIVE(25,"五型专项作业车"),
        TWENTY_SIX(26,"六型专项作业车");

        private final Integer value;
        private final String desc;
        @Override
        public Integer getValue() {
            return this.value;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }
    }
}
