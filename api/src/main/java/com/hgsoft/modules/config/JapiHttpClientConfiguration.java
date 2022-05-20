package com.hgsoft.modules.config;

import cn.hutool.extra.spring.EnableSpringUtil;
import com.zhanpu.japi.sdk.JapiClientProperties;
import com.zhanpu.japi.sdk.JapiHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * japiHttpClient configuration
 *
 * @author zhanpu
 * @date 2021/4/13
 */
@Slf4j
@EnableSpringUtil
@Configuration
public class JapiHttpClientConfiguration {
    /**
     * 注入{@link JapiHttpClient}配置信息{@link JapiClientProperties}
     *
     * @return {@link JapiClientProperties}
     */
    @Lazy
    @Bean
    @ConfigurationProperties(prefix = "pay-gateway")
    JapiClientProperties japiClientProperties() {
        return new JapiClientProperties();
    }

    /**
     * 注入{@link JapiHttpClient}
     *
     * @param japiClientProperties 配置信息{@link JapiClientProperties}
     * @return {@link JapiHttpClient}
     */
    @Bean
    public JapiHttpClient japiHttpClient(JapiClientProperties japiClientProperties) {
        return new JapiHttpClient(japiClientProperties);
    }
}
