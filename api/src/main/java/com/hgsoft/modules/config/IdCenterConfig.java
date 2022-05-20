package com.hgsoft.modules.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by 吴鉴武 on 2022/1/10 17:45
 */
@ConfigurationProperties(prefix = "snowflake-id-center")
@Component
@Data
public class IdCenterConfig {
    private Long workId;
    private Long dataCenterId;
}
