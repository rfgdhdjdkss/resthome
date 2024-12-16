package com.jinyang.resthome.exception;

import lombok.Data;

/**
 * @Description:
 * @ClassName: ServiceException
 * @Author: jinyang
 * @Date: 2024/12/3 21:08
 * @Version: 1.0
 */
@Data
public class ServiceException extends RuntimeException{

    private final Integer code;
    public ServiceException(String message) {
        super(message);
        this.code = 500;
    }
    public ServiceException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
