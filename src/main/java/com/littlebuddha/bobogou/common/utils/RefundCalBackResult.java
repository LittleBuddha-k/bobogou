package com.littlebuddha.bobogou.common.utils;

/**
 * 调用退款接口的返回对象
 * User: LittleBuddha-k
 * Date: 2021-11-08
 */
public class RefundCalBackResult<T> {

        private Integer code;
        private T data;
        private String msg;
        private boolean success;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T isData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}