package com.littlebuddha.bobogou.modules.entity.other;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

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

    private CustomerUser customerUser;//用户外键
    private Integer userId;//用户ID
    private String customerUserName;//用户名
    private String shopName;//店名
    private String imageUrl;//图片地址，多张使用英文逗号分隔
    private String remark;//描述

    public Sticker() {
    }

    public Sticker(String id) {
        super(id);
    }

    public CustomerUser getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(CustomerUser customerUser) {
        this.customerUser = customerUser;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCustomerUserName() {
        if (customerUser != null && StringUtils.isNotBlank(customerUser.getNickname())){
            this.customerUserName = customerUser.getNickname();
        }
        return customerUserName;
    }

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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
