package com.littlebuddha.bobogou.common.exception.serviceexception;

import com.littlebuddha.bobogou.common.exception.errorcode.IErrorCode;

public class CustomizeException extends RuntimeException {

    private String message;
    private Integer code;

    public CustomizeException(IErrorCode iErrorCode) {
        this.code = iErrorCode.getCode();
        this.message = iErrorCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
