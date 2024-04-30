package com.xiaodeng.exception;


import com.xiaodeng.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiaodeng
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        int code = -1;
        log.error("", e);
        return Result.error(code, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeException(RuntimeException e) {
        log.error("", e);
        int code = e.hashCode();
        return Result.error(code, e.getMessage());
    }



    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.error("", e);
        return Result.error(e.getCode(), e.getMessage());
    }

}
