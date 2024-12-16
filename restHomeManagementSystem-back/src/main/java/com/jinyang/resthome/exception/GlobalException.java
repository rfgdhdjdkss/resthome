package com.jinyang.resthome.exception;

import com.jinyang.resthome.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @ClassName: GlobalException
 * @Author: jinyang
 * @Date: 2024/12/3 21:10
 * @Version: 1.0
 */
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result serviceException(ServiceException e){
        return Result.build(null,e.getCode(),e.getMessage());
    }
}
