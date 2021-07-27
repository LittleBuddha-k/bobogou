package com.littlebuddha.bobogou.common.response;

import com.littlebuddha.bobogou.common.exception.errorcode.CustomizeErrorCode;
import com.littlebuddha.bobogou.common.exception.serviceexception.CustomizeException;

public class ResponseResult<D> {

    private Integer code;
    private String message;
    private D data;

    public static ResponseResult errorOf(Integer code, String message){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static ResponseResult errorOf(CustomizeErrorCode customizeErrorCode){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(customizeErrorCode.getCode());
        responseResult.setMessage(customizeErrorCode.getMessage());
        return responseResult;
    }

    public static ResponseResult errorOf(CustomizeException ex){
        return errorOf(ex.getCode(),ex.getMessage());
    }

    public static ResponseResult okOf(){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(200);
        responseResult.setMessage("操作成功");
        return responseResult;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    /*一些构造方法*/

    public ResponseResult() {
    }

    public ResponseResult(Integer code, String message, D data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(String message, D data) {
        this.message = message;
        this.data = data;
    }

    public ResponseResult(Integer code, D data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
