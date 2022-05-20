package com.hgsoft.modules.monitor.enums;

import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

/**
 * 监控枚举类
 * Created by 吴鉴武 on 2021/6/2 13:57
 */
public interface MonitorEnum {
    String getValue();
    String getName();
    static String getNameByValue(MonitorEnum[] monitorEnum,String value){
        return Stream.of(monitorEnum).filter(vo->vo.getValue().equals(value)).map(vo->vo.getName()).findFirst().orElse(null);
    }

    /**
     * 车道软件状态枚举
     */
    @RequiredArgsConstructor
    enum SiteSoftState implements MonitorEnum{
        GO_TO_WORK("1","上班"),
        GO_OFF_WORK("0","下班");

        private final String value;
        private final String name;
        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    /**
     * 天线状态枚举
     */
    @RequiredArgsConstructor
    enum AntennaState implements MonitorEnum{
        NORMAL("0","异常"),
        EXCEPTION("1","正常");

        private final String value;
        private final String name;
        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    /**
     * 大额交易配置枚举
     */
    @RequiredArgsConstructor
    enum MaxTransFeeConfig implements MonitorEnum{
        NOT_CONFIGURE("0","未配置"),
        OPEN("1","已开启"),
        NOT_OPEN("2","未开启");

        private final String value;
        private final String name;
        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
