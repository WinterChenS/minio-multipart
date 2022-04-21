package com.winterchen.minio.base;

import com.google.common.collect.Multimap;
import io.minio.messages.Part;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author winterchen
 * @version 1.0
 * @date 2022/4/20 17:33
 * @description 创建分片上传需要的参数
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultipartUploadCreate {

    private String bucketName;
    private String region;
    private String objectName;
    private Multimap<String, String> headers;
    private Multimap<String, String> extraQueryParams;

    private String uploadId;

    private Integer maxParts;

    private Part[] parts;

    private Integer partNumberMarker;


}