package com.littlebuddha.bobogou.common.exception.errorcode;

public enum CustomizeErrorCode implements IErrorCode {

    SUCCESS(200, "操作成功"),
    NO_SUCH_DATA(201, "没有此数据"),
    NO_LOGIN(202, "当前用户未登录"),
    UNKNOWN_ERROR(203, "未知错误"),
    SYS_ERROR(204, "系统错误请稍后"),
    TYPE_WRONG(205, "当前评论的类型不匹配或者不存在"),
    COMMENT_NOT_EXIST(206, "当前评论不存在"),
    QUESTION_NOT_EXIST(207, "当前问题不存在"),
    COMMENT_NOT_EMPTY(208, "回复内容不能为空"),
    UPLOAD_ERROR(500, "上传出错"),
    DOWNLOAD_ERROR(500, "下载出错"),
    FILE_EMPTY(500, "文件为空"),
    FILE_NOT_EXIST(500, "文件不存在");

    private String message;

    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
