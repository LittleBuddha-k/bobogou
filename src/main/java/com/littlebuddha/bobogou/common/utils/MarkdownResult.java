package com.littlebuddha.bobogou.common.utils;

/**
 * markdown上传图片后需要返回的数据格式
 */
public class MarkdownResult {

    private Integer success;//: 0 | 1,           // 0 表示上传失败，1 表示上传成功
    private String message;//: "提示的信息，上传成功或上传失败及错误信息等。",
    private String url;//: "图片地址"        // 上传成功时才返回

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
