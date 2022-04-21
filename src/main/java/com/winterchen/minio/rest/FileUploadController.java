package com.winterchen.minio.rest;

import com.winterchen.minio.request.CompleteMultipartUploadRequest;
import com.winterchen.minio.request.MultipartUploadCreateRequest;
import com.winterchen.minio.response.FileUploadResponse;
import com.winterchen.minio.response.MultipartUploadCreateResponse;
import com.winterchen.minio.service.FileUploadService;
import io.swagger.annotations.*;
import org.simpleframework.xml.Path;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author winterchen
 * @version 1.0
 * @date 2022/4/21 16:56
 **/
@Api(tags = "文件管理")
@RequestMapping("/file")
@RestController
public class FileUploadController {


    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "file",
            value = "文件对象",
            dataType = "MultipartFile",
            required = true,
            allowMultiple = true)})
    @ApiOperation("文件上传")
    @PostMapping("/upload/file")
    public FileUploadResponse uploadFile(
            @ApiParam(value = "file",required = true)
            @RequestParam(value = "file", required = true)
                    MultipartFile file,
            @RequestParam(name = "pass", required = false)
            @ApiParam(value = "pass",required = false)
                    String pass,
            @RequestParam(name = "expire", required = true)
            @ApiParam(value = "expire",required = false)
                    Integer expire,
            @RequestParam(name = "maxGetCount", required = true)
            @ApiParam(value = "maxGetCount",required = false)
                    Integer maxGetCount
    ) {
        return fileUploadService.upload(file);
    }




    @ApiOperation("创建分片上传")
    @PostMapping("/multipart/create")
    public MultipartUploadCreateResponse createMultipartUpload(
            @RequestBody
            @Validated
                    MultipartUploadCreateRequest multipartUploadCreateRequest
    ) {
        return fileUploadService.createMultipartUpload(multipartUploadCreateRequest);
    }

    @ApiOperation("合并分片")
    @PostMapping("/multipart/complete")
    public FileUploadResponse completeMultipartUpload(
            @RequestBody
            @Validated
                    CompleteMultipartUploadRequest uploadRequest
    ) {
        return fileUploadService.completeMultipartUpload(uploadRequest);
    }

    @ApiOperation("文件删除")
    @DeleteMapping("/{fileName}")
    public void remove(
            @PathVariable("fileName")
            String fileName
    ) {
        fileUploadService.remove(fileName);
    }
}