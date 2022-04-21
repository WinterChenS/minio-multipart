package com.winterchen.minio.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author winterchen
 * @version 1.0
 * @date 2022/4/12 10:49
 * @description minio文件上传返回类
 **/
@Data
@Builder
public class FileUploadResponse {

    private String realName;

    private String uploadName;

    private String url;

    private long size;

    private String bucket;


}