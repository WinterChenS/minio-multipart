package com.winterchen.minio.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author winterchen
 * @version 1.0
 * @date 2022/4/12 10:18
 * @description minio 配置文件
 **/
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    /**
     * 连接地址
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;

    private String bucketName;

    private String downloadUri;


    private String path;

}