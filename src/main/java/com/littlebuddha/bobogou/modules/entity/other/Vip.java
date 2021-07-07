package com.littlebuddha.bobogou.modules.entity.other;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: LittleBuddha-k
 * \* Date: 2021-06-22
 * \* Time: 下午 02:43
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * 聊天实体类
 */
public class Vip extends DataEntity<Vip> {

    private String name;//名称
    private Integer time;//时间，使用类型的单位
    private Integer type;//类型，1=药房VIP过期天数（单位：天），2=诊所VIP过期天数（单位：天），3=医院VIP过期天数（单位：天）

    public Vip() {
    }

    public Vip(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
