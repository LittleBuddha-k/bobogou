package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 药品实体类
 */
public class Medicine extends DataEntity<Medicine> {

    private String name;//商品名称

    private Banner banner;//轮播图外键
    private String images;//商品展示轮播图，最多六张，使用英文逗号分隔

    private CommodityBrand commodityBrand;//商品品牌规格外键
    private String brandId;//品牌分类ID

    private CommodityTag commodityTag;//商品标签外键
    private String tagId;//商品标签ID,多个标签使用英文逗号分隔

    private Integer purchasingPrice;//进价，单位：分
    private Integer originalCost;//原价，单位：分
    private Integer sellingPrice;//普通会员价，单位：分
    private Integer vipPrice;//会员价，单位：分
    private String specification;//规格
    private Integer amount;//数量
    private Integer salesVolume;//销量
    private String effect;//功效
    private String applauseRate;//好评率，单位：%，好评率大于90%为推荐商品
    private String healthBeans;//健康豆，该商品可获得的健康豆
    private String isMarket;//是否销售，0=在售，2停售
    private String accountId;//最后操作人ID

    @ExcelField(title = "商品名称",align=2,sort=1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public CommodityBrand getCommodityBrand() {
        return commodityBrand;
    }

    public void setCommodityBrand(CommodityBrand commodityBrand) {
        this.commodityBrand = commodityBrand;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public CommodityTag getCommodityTag() {
        return commodityTag;
    }

    public void setCommodityTag(CommodityTag commodityTag) {
        this.commodityTag = commodityTag;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @ExcelField(title = "进价",align=2,sort=2)
    public Integer getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(Integer purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    @ExcelField(title = "原价",align=2,sort=3)
    public Integer getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(Integer originalCost) {
        this.originalCost = originalCost;
    }

    @ExcelField(title = "普通会员价",align=2,sort=4)
    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @ExcelField(title = "会员价",align=2,sort=5)
    public Integer getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Integer vipPrice) {
        this.vipPrice = vipPrice;
    }

    @ExcelField(title = "规格",align=2,sort=6)
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @ExcelField(title = "数量",align=2,sort=7)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @ExcelField(title = "销量",align=2,sort=8)
    public Integer getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    @ExcelField(title = "功效",align=2,sort=9)
    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    @ExcelField(title = "好评率",align=2,sort=10)
    public String getApplauseRate() {
        return applauseRate;
    }

    public void setApplauseRate(String applauseRate) {
        this.applauseRate = applauseRate;
    }

    @ExcelField(title = "健康豆",align=2,sort=11)
    public String getHealthBeans() {
        return healthBeans;
    }

    public void setHealthBeans(String healthBeans) {
        this.healthBeans = healthBeans;
    }

    @ExcelField(title = "是否在售",align=2,sort=12)
    public String getIsMarket() {
        return isMarket;
    }

    public void setIsMarket(String isMarket) {
        this.isMarket = isMarket;
    }

    public String getAccountId() {
        if(this.updateBy != null && StringUtils.isNotBlank(updateBy.getId())){
            this.accountId = this.updateBy.getId();
        }
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
