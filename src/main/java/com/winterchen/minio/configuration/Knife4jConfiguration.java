package com.winterchen.minio.configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author winterchen
 * @version 1.0
 * @date 2022/4/11 15:31
 * @description api文档配置
 **/
@EnableSwagger2
@EnableKnife4j
@Configuration
public class Knife4jConfiguration {


    /**
     * 是否允许swagger
     */
    @Value("${swagger.enable:true}")
    private Boolean enableSwagger;

    @Bean(value = "defaultApi2")
    public Docket createRestApi() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //.title("swagger-bootstrap-ui-demo RESTful APIs")
                        .description("file-upload")
                        .termsOfServiceUrl("http://www.winterchen.com/")
                        .contact(new Contact("winterchen","https:/www.winterchen.com", "i@winterchen.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("2.0版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.winterchen.minio.rest"))
                .paths(PathSelectors.any())
                .build().enable(enableSwagger);
        return docket;
    }


}