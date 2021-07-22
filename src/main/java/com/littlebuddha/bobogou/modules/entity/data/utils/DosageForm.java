package com.littlebuddha.bobogou.modules.entity.data.utils;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.data.City;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ck
 * @date 2020/7/24 15:14
 * 商品规范中剂型工具类
 */
public class DosageForm extends DataEntity<DosageForm> {

    private String name;

    public DosageForm() {
    }

    public DosageForm(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
