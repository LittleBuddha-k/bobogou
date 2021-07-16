package com.littlebuddha.bobogou.modules.entity.basic;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

/**
 *文件上传实体类
 */
public class Contract extends DataEntity<Contract> {

    private String name;//文件名
    private String address;//文件地址

    public Contract() {
    }

    public Contract(String id) {
        super(id);
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
