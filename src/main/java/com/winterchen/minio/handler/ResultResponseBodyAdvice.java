package com.winterchen.minio.handler;



import com.google.gson.Gson;
import com.winterchen.minio.annotation.NoResultHolder;
import com.winterchen.minio.base.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author winterchen
 * @version 1.0
 * @date 2022/3/30 14:25
 * @description 统一处理返回结果集
 **/
@RestControllerAdvice(value = {"com.winterchen"})
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType) || StringHttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> converterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 处理空值
        if (o == null && StringHttpMessageConverter.class.isAssignableFrom(converterType)) {
            return Result.success();
        }

        if (methodParameter.hasMethodAnnotation(NoResultHolder.class)) {
            return o;
        }


        if (!(o instanceof Result)) {
            if (o instanceof String) {
                return new Gson().toJson(Result.success(o));
            }
            return Result.success(o);
        }
        return o;
    }


}