package com.littlebuddha.bobogou.modules.entity.data.utils;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

/**
 * @author ck
 * @date 2020/7/24 15:14
 * 商品规范中保质期工具类
 */
public class ShelfLife extends DataEntity<ShelfLife> {

    private String value;
    private String name;

    public ShelfLife() {
    }

    public ShelfLife(String id) {
        super(id);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
