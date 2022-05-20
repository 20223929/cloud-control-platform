package com.hgsoft.modules.config;

import com.zhanpu.japi.sdk.JapiClientProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付网关接口配置
 * Created by 吴鉴武 on 2021/12/8 15:35
 */
@Data
@ConfigurationProperties(prefix = "pay-gateway.other")
@Component
public class PayGatewayConfig{

    //退费url
    private String refundUri;

    //查询退费结果url
    private String queryRefundResultUri;
}
