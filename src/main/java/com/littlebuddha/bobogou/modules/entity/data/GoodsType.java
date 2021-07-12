package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.system.Operator;

/**
 * 商品其他分类实体类
 */
public class GoodsType extends DataEntity<GoodsType> {

    private String name;//分类名称
    private Integer type;//1=精选分类，2=黄金单品分类

    public GoodsType() {
    }

    public GoodsType(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
