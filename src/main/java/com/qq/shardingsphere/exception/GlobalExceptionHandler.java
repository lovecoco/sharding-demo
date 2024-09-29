package com.qq.shardingsphere.exception;

import com.alibaba.fastjson2.JSON;
import com.qq.shardingsphere.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseDTO defaultErrorHandler(Exception e)
            throws Exception {
        // 当报错了，又不知道啥错的时候，把下面这行代码打开，就可以看到报错的堆信息了
        e.printStackTrace();
        log.error(e.getMessage());
        ResponseDTO result = new ResponseDTO();
        result.setCode("500");
        result.setMsg("稍后再试");
        log.info("异常错误{}", e.getMessage());
        return result;
    }

    /**
     * 接口错误统一处理
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    public ResponseDTO jsonErrorHandler(ApiException e) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(e.getCode() + "");
        result.setMsg(e.getMessage());
        log.info("API异常错误{}", JSON.toJSONString(result));
        return result;
    }
}
