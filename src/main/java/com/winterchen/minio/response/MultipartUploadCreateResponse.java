package com.winterchen.minio.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author winterchen
 * @version 1.0
 * @date 2022/4/21 9:40
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("分片上传创建响应类")
public class MultipartUploadCreateResponse {

    @ApiModelProperty("上传编号")
    private String uploadId;

    @ApiModelProperty("分片信息")
    private List<UploadCreateItem> chunks;


    @Data
    public static class UploadCreateItem {

        @ApiModelProperty("分片编号")
        private Integer partNumber;

        @ApiModelProperty("上传地址")
        private String uploadUrl;

    }

}