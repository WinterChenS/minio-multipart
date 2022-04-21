package com.winterchen.minio.configuration;

import com.winterchen.minio.utils.PearlMinioClient;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author winterchen
 * @version 1.0
 * @date 2022/4/12 10:19
 * @description Minio 配置类
 **/
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfiguration {

    @Autowired
    private MinioProperties minioProperties;


    @Bean
    public PearlMinioClient minioClient() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
        return new PearlMinioClient(minioClient);
    }

}