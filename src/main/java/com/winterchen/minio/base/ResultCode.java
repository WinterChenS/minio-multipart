package com.winterchen.minio.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author winterchen
 * @version 1.0
 * @date 2022/4/11 15:40
 * @description 响应code
 **/
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(1, "成功"),
    PARAM_IS_INVALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETED(1004, "参数缺失"),
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_LOGIN_ERROR(2002, "账号不存在或者密码错误"),
    USER_IS_BLOCK(2003, "账号已被禁用"),
    USER_NOT_EXIST(2004, "用户不存在"),
    USER_HAS_EXISTED(2005, "用户已存在"),
    KNOWN_ERROR(3001, "未知异常，请联系管理员"),
    FILE_IO_ERROR(4001, "文件io异常"),
    MINIO_SERVER_ERROR(4002, "文件服务器异常"),
    MINIO_INSUFFICIENT_DATA(4003, "文件服务器资源不足"),
    ;

    private Integer code;

    private String message;


    public static ResultCode getByCode(Integer code) {
        for (ResultCode value : ResultCode.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

}