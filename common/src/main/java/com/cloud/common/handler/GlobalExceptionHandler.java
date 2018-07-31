package com.cloud.common.handler;

import com.cloud.common.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by Junhe on 2018/7/31
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    /**
     * 全局异常.
     *
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public R exception(Exception e) {
        LOGGER.info("保存全局异常信息 ex={}", e.getMessage(), e);
        return new R<>(e);
    }
}
