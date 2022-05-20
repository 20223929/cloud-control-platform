package com.hgsoft.modules.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by 吴鉴武 on 2021/4/27 16:24
 */
@ConfigurationProperties(prefix = "merchant")
@Component
@Data
public class MerchantConfig {
    private String bankMerchantGroupId;
    private String provinceId;
    private String merchantGroupSeq;
    private String merchantSeq;
    private String siteSeq;
    private String certFilePath;
    private String hashKeyPrefix;
    private String merchantCertHashKeySuffix;
    private String channelHashKeySuffix;
    private String siteChannelHashKeySuffix;
    private String certFileNameKeySuffix;
    private String algName;
    private Integer publicKeyLength;
}
