package com.littlebuddha.bobogou.modules.entity.system.utils;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

/**
 * 图标实体类
 */
public class Icon extends DataEntity<Icon> {

    private String name;

    private String classCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}
