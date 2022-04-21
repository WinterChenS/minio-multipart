package com.winterchen.minio.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author winterchen
 * @version 1.0
 * @date 2022/4/21 9:44
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("创建分片请求类")
public class MultipartUploadCreateRequest {

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("分片数量")
    private Integer chunkSize;

}