package com.littlebuddha.bobogou.modules.entity.basic;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

/**
 *公司资质实体类
 */
public class Qualification extends DataEntity<Qualification> {

    private String name;//名称
    private String qualification;//资质图片

    public Qualification() {
    }

    public Qualification(String id) {
        super(id);
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
