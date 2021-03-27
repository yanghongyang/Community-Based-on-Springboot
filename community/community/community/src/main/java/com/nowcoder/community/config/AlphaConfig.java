package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
// @Configuration 表示这是一个配置类
public class AlphaConfig {
    @Bean
    // @Bean 表示下面实现的方法将会返回一个Bean对象，装配到容器里
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
