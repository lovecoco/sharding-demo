package com.qq.shardingsphere.config;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aspect
@Slf4j
@Component
public class MyAspect {

    @Around("execution(* com.qq.shardingsphere.controller..*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录方法执行开始时间
        long begin = System.currentTimeMillis();
        // 获得request对象
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert sra != null;
        HttpServletRequest req = sra.getRequest();
        Object logId = req.getAttribute("logId");
        if(logId == null){
            logId = UUID.randomUUID().toString();
        }
        log.info("{}:请求接口地址: {}, 请求设备: {}",logId, req.getRequestURI(), req.getHeader("user-agent"));
        log.info("{}:类信息：{}, 方法名：{}", logId,joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());
        // 获得请求参数
        Map<String, Object> requestParams = getRequestParams(joinPoint);
        log.info("{}:接口请求参数：{}", logId,JSON.toJSONString(requestParams));
        // 执行服务层业务逻辑
        Object result = joinPoint.proceed();
        log.info("{}:接口请求响应：{}", logId, JSON.toJSONString(result));
        log.info("{}:接口执行响应时间：{} ms", logId,System.currentTimeMillis() - begin);
        return result;
    }

    /**
     * 获取入参
     *
     * @param joinPoint 切点
     * @return 请求参数
     */
    private Map<String, Object> getRequestParams(ProceedingJoinPoint joinPoint) {

        Map<String, Object> requestParams = new HashMap<>();
        //参数名
        String[] paramNames =
                ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = joinPoint.getArgs();

        for (int i = 0; i < paramNames.length; i++) {
            if ((!(paramValues[i] instanceof HttpServletRequest))
                    && (!(paramValues[i] instanceof HttpServletResponse))
                    && (!(paramValues[i] instanceof Errors))) {
                Object value = paramValues[i];

                //如果是文件对象
                if (value instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) value;
                    //获取文件名
                    value = file.getOriginalFilename();
                }
                requestParams.put(paramNames[i], value);
            }
        }
        return requestParams;
    }
}
