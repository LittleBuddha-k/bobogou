package com.littlebuddha.bobogou.common.response;

public enum CommentEnum {
    
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentEnum commentEnum : CommentEnum.values()) {
            if (commentEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
