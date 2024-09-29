package com.qq.shardingsphere.config;

import com.alibaba.fastjson2.JSON;
import com.qq.shardingsphere.dto.ResponseDTO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(value = "com.qq.shardingsphere.controller")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof ResponseDTO){
            return body;
        }

        if(body instanceof String){
            return JSON.toJSONString(ResponseDTO.success(body));
        }
        if("text/plain".equalsIgnoreCase(selectedContentType.toString()) && body == null){
            return JSON.toJSONString(ResponseDTO.success(null));
        }


        return ResponseDTO.success(body);
    }
}
