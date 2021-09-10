package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 商品兑换实体类
 */
public class GoodsExchange extends DataEntity<GoodsExchange> {

    private String name;//商品名称
    private String imageUrl;//图片地址
    private Double price;//价格，单位：分
    private Double healthBeans;//健康豆

    private Double integral;//积分

    private String specification;//规格
    private Double weight;//单个规格重量，单位：g
    private Integer amount;//数量
    private Double changeAmount;//兑换量
    private String content;//详情
    private Integer status;//状态，0=可兑换，1=不可兑换

    private String contentOriginal;//原数据

    private String accountId;//最好操作人ID
    private String updateByName;//更新人名字

    public GoodsExchange() {
    }

    public GoodsExchange(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getHealthBeans() {
        return healthBeans;
    }

    public void setHealthBeans(Double healthBeans) {
        this.healthBeans = healthBeans;
    }

    public Double getIntegral() {
        return integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContentOriginal() {
        return contentOriginal;
    }

    public void setContentOriginal(String contentOriginal) {
        this.contentOriginal = contentOriginal;
    }

    public String getAccountId() {
        if (updateBy != null && StringUtils.isNotBlank(updateBy.getId())){
            this.accountId = updateBy.getId();
        }
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUpdateByName() {
        if(updateBy != null && StringUtils.isNotBlank(updateBy.getNickname())){
            this.updateByName = updateBy.getNickname();
        }
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
    }
}
