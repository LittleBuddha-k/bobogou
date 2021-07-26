package com.littlebuddha.bobogou.modules.entity.common;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

/**
 *下拉选词典实体类
 */
public class DictData extends DataEntity<DictData> {

    private String type;//类型，分类
    private String name;//名称
    private String value;//值

    public DictData() {
    }

    public DictData(String id) {
        super(id);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
