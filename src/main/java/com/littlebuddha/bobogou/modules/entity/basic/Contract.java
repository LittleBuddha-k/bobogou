package com.littlebuddha.bobogou.modules.entity.basic;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

/**
 *文件上传实体类
 */
public class Contract extends DataEntity<Contract> {

    private Integer type;//类型，1=药房合同，2=诊所合同，3=医院合同
    private String name = "";//合同内容，富文本
    private String address = "";//合同保存路径

    public Contract() {
    }

    public Contract(String id) {
        super(id);
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
