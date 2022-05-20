package com.hgsoft;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication(exclude = { DruidDataSourceAutoConfigure.class })

public class ExpPlatformEtcApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(ExpPlatformEtcApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application ECIP is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "\n\t" +
                "swagger-ui: \thttp://" + ip + ":" + port + path + "/swagger-ui.html\n\t" +
                "Doc: \t\thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(ExpPlatformEtcApplication.class);
    }
}
