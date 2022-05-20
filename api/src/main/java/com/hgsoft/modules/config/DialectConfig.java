package com.hgsoft.modules.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DialectConfig
 *
 * @author Administrator
 * @version 1.0
 * @date 2021/12/15 16:31
 * @desc
 **/
@Configuration
@Slf4j
public class DialectConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("postgresql");
        return paginationInterceptor;
    }
}
