package com.hgsoft.modules.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 拓展平台配置类
 * Created by 吴鉴武 on 2022/1/4 15:40
 */
@ConfigurationProperties(prefix = "exp-platform")
@Component
@Data
public class ExpPlatformConfig {

    private String certPath;
}
