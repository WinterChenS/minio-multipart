package com.winterchen.minio.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author winterchen
 * @version 1.0
 * @date 2022/4/13 16:32
 * @description 用户配置
 **/
@Slf4j
@Configuration
public class CustomerConfiguration implements WebMvcConfigurer {



    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 允许任何域名使用
        corsConfiguration.addAllowedOrigin("*");

        //允许任何头
        corsConfiguration.addAllowedHeader("*");

        //允许任何方法（post、get等）
        corsConfiguration.addAllowedMethod("*");

        source.registerCorsConfiguration("/**" , corsConfiguration);

        return new CorsFilter(source);
    }



}