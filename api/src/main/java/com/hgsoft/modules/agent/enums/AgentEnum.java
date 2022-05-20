package com.hgsoft.modules.agent.enums;

import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

/**
 * 代理商相关美剧
 *
 * @Date 2022-04-29
 */
public interface AgentEnum {

    int getCode();

    String getDesc();

    /**
     * 根据code获取desc
     *
     * @param agentEnums
     * @param code
     * @return
     */
    public static String getDescByCode(AgentEnum[] agentEnums, int code) {
        return Stream.of(agentEnums).filter(vo -> vo.getCode() == code).map(AgentEnum::getDesc).findFirst().orElse(null);
    }

    @RequiredArgsConstructor
    enum CertificateType implements AgentEnum {
        BUSINESS_LICENSE(1, "营业执照"),
        ORGANIZING_INSTITUTION(2, "组织机构");

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
