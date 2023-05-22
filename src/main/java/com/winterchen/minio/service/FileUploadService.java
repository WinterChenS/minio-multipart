package com.winterchen.minio.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;

import com.winterchen.minio.base.MultipartUploadCreate;
import com.winterchen.minio.base.ResultCode;
import com.winterchen.minio.exception.BusinessException;
import com.winterchen.minio.request.CompleteMultipartUploadRequest;
import com.winterchen.minio.request.MultipartUploadCreateRequest;
import com.winterchen.minio.response.FileUploadResponse;
import com.winterchen.minio.response.MultipartUploadCreateResponse;
import com.winterchen.minio.utils.MinioHelper;
import io.minio.CreateMultipartUploadResponse;
import io.minio.ListPartsResponse;
import io.minio.ObjectWriteResponse;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.ServerException;
import io.minio.messages.Part;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.FileInfo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.Query;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author winterchen
 * @version 1.0
 * @date 2022/4/12 14:24
 * @description 文件上传服务
 **/
@Slf4j
@Service
public class FileUploadService {



    private final MinioHelper minioHelper;

    public FileUploadService(MinioHelper minioHelper) {
        this.minioHelper = minioHelper;
    }


    /**
     * 普通上传
     * @param file
     * @return
     */
    public FileUploadResponse upload(MultipartFile file) {
        Assert.notNull(file, "文件不能为空");
        log.info("start file upload");

        //文件上传
        try {
            return minioHelper.uploadFile(file);
        } catch (IOException e) {
            log.error("file upload error.", e);
            throw BusinessException.newBusinessException(ResultCode.FILE_IO_ERROR.getCode());
        } catch (ServerException e) {
            log.error("minio server error.", e);
            throw BusinessException.newBusinessException(ResultCode.MINIO_SERVER_ERROR.getCode());
        } catch (InsufficientDataException e) {
            log.error("insufficient data throw exception", e);
            throw BusinessException.newBusinessException(ResultCode.MINIO_INSUFFICIENT_DATA.getCode());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BusinessException.newBusinessException(ResultCode.KNOWN_ERROR.getCode());
        }


    }


    /**
     * 创建分片上传
     * @param createRequest
     * @return
     */
    public MultipartUploadCreateResponse createMultipartUpload(MultipartUploadCreateRequest createRequest) {
        log.info("创建分片上传开始, createRequest: [{}]", createRequest);
        MultipartUploadCreateResponse response = new MultipartUploadCreateResponse();
        response.setChunks(new LinkedList<>());
        final MultipartUploadCreate uploadCreate = MultipartUploadCreate.builder()
                .bucketName(minioHelper.minioProperties.getBucketName())
                .objectName(createRequest.getFileName())
                .build();
        final CreateMultipartUploadResponse uploadId = minioHelper.uploadId(uploadCreate);
        uploadCreate.setUploadId(uploadId.result().uploadId());
        response.setUploadId(uploadCreate.getUploadId());
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("uploadId", uploadId.result().uploadId());
        for (int i = 0; i <= createRequest.getChunkSize(); i++) {
            reqParams.put("partNumber", String.valueOf(i));
            String presignedObjectUrl = minioHelper.getPresignedObjectUrl(uploadCreate.getBucketName(), uploadCreate.getObjectName(), reqParams);
            if (StringUtils.isNotBlank(minioHelper.minioProperties.getPath())) {//如果线上环境配置了域名解析，可以进行替换
                presignedObjectUrl = presignedObjectUrl.replace(minioHelper.minioProperties.getEndpoint(), minioHelper.minioProperties.getPath());
            }
            MultipartUploadCreateResponse.UploadCreateItem item = new MultipartUploadCreateResponse.UploadCreateItem();
            item.setPartNumber(i);
            item.setUploadUrl(presignedObjectUrl);
            response.getChunks().add(item);
        }
        log.info("创建分片上传结束, createRequest: [{}]", createRequest);
        return response;
    }

    /**
     * 分片合并
     * @param uploadRequest
     */
    public FileUploadResponse completeMultipartUpload(CompleteMultipartUploadRequest uploadRequest) {
        log.info("文件合并开始, uploadRequest: [{}]", uploadRequest);
        try {
            final ListPartsResponse listMultipart = minioHelper.listMultipart(MultipartUploadCreate.builder()
                    .bucketName(minioHelper.minioProperties.getBucketName())
                    .objectName(uploadRequest.getFileName())
                    .maxParts(uploadRequest.getChunkSize() + 10)
                    .uploadId(uploadRequest.getUploadId())
                    .partNumberMarker(0)
                    .build());
            final ObjectWriteResponse objectWriteResponse = minioHelper.completeMultipartUpload(MultipartUploadCreate.builder()
                    .bucketName(minioHelper.minioProperties.getBucketName())
                    .uploadId(uploadRequest.getUploadId())
                    .objectName(uploadRequest.getFileName())
                    .parts(listMultipart.result().partList().toArray(new Part[]{}))
                    .build());

            return FileUploadResponse.builder()
                    .url(minioHelper.minioProperties.getDownloadUri() + "/" + minioHelper.minioProperties.getBucketName() + "/" + uploadRequest.getFileName())
                    .build();
        } catch (Exception e) {
            log.error("合并分片失败", e);
        }
        log.info("文件合并结束, uploadRequest: [{}]", uploadRequest);
        return null;
    }


    public void remove(String fileName) {
        if (StringUtils.isBlank(fileName)) return;
        log.info("删除文件开始, fileName: [{}]",fileName);
        try {
            minioHelper.removeFile(fileName);
        } catch (Exception e) {
            log.error("删除文件失败", e);
        }
        log.info("删除文件结束, fileName: [{}]",fileName);
    }
}