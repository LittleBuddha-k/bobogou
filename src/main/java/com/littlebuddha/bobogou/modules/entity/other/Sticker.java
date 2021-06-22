package com.littlebuddha.bobogou.modules.entity.other;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: LittleBuddha-k
 * \* Date: 2021-06-22
 * \* Time: 下午 02:43
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * 贴单实体类
 */
public class Sticker extends DataEntity<Sticker> {

    private Integer userId;//用户ID
    private String theHeir;//上传人
    private String imageUrl;//图片地址，多张使用英文逗号分隔
    private String remark;//描述

    public Sticker() {
    }

    public Sticker(String id) {
        super(id);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTheHeir() {
        return theHeir;
    }

    public void setTheHeir(String theHeir) {
        this.theHeir = theHeir;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
