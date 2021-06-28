package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 药品实体类
 */
public class Goods extends DataEntity<Goods> {

    private String name;//商品名称

    private Banner banner;//轮播图外键
    private String images;//商品展示轮播图，最多六张，使用英文逗号分隔

    private GoodsBrand goodsBrand;//商品品牌规格外键
    private Integer brandId;//品牌分类ID

    private GoodsTag goodsTag;//商品标签外键
    private String tagId;//商品标签ID,多个标签使用英文逗号分隔

    private Double purchasingPrice;//进价，单位：分
    private Double originalCost;//原价，单位：分
    private Double sellingPrice;//普通会员价，单位：分
    private Double vipPrice;//会员价，单位：分
    private String specification;//规格
    private Integer amount;//数量
    private Integer usedAmount;//已用
    private Integer stockAmount;//库存
    private Integer salesVolume;//销量
    private String effect;//功效
    private Integer applauseRate;//好评率，单位：%，好评率大于90%为推荐商品
    private Integer healthBeans;//健康豆，该商品可获得的健康豆
    private Integer isMarket;//是否销售，0=在售，2停售
    private String accountId;//最后操作人ID

    private GoodsClassify goodsClassify;//商品分类外键

    private GoodsSpecification goodsSpecification;//商品规格外键

    private GoodsInfo goodsInfo;//商品详情外键

    public Goods() {
    }

    public Goods(String id) {
        super(id);
    }

    @ExcelField(title = "商品名称", align = 2, sort = 1)
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

    public GoodsBrand getGoodsBrand() {
        return goodsBrand;
    }

    public void setGoodsBrand(GoodsBrand goodsBrand) {
        this.goodsBrand = goodsBrand;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public GoodsTag getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(GoodsTag goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @ExcelField(title = "进价", align = 2, sort = 2)
    public Double getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(Double purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    @ExcelField(title = "原价", align = 2, sort = 3)
    public Double getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(Double originalCost) {
        this.originalCost = originalCost;
    }

    @ExcelField(title = "普通会员价", align = 2, sort = 4)
    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @ExcelField(title = "会员价", align = 2, sort = 5)
    public Double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }

    @ExcelField(title = "规格", align = 2, sort = 6)
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @ExcelField(title = "数量", align = 2, sort = 7)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(Integer usedAmount) {
        this.usedAmount = usedAmount;
    }

    public Integer getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(Integer stockAmount) {
        this.stockAmount = stockAmount;
    }

    @ExcelField(title = "销量", align = 2, sort = 8)
    public Integer getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    @ExcelField(title = "功效", align = 2, sort = 9)
    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    @ExcelField(title = "好评率", align = 2, sort = 10)
    public Integer getApplauseRate() {
        return applauseRate;
    }

    public void setApplauseRate(Integer applauseRate) {
        this.applauseRate = applauseRate;
    }

    @ExcelField(title = "健康豆", align = 2, sort = 11)
    public Integer getHealthBeans() {
        return healthBeans;
    }

    public void setHealthBeans(Integer healthBeans) {
        this.healthBeans = healthBeans;
    }

    @ExcelField(title = "是否在售", align = 2, sort = 12)
    public Integer getIsMarket() {
        return isMarket;
    }

    public void setIsMarket(Integer isMarket) {
        this.isMarket = isMarket;
    }

    public String getAccountId() {
        if (this.updateBy != null && StringUtils.isNotBlank(updateBy.getId())) {
            this.accountId = this.updateBy.getId();
        }
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public GoodsClassify getGoodsClassify() {
        return goodsClassify;
    }

    public void setGoodsClassify(GoodsClassify goodsClassify) {
        this.goodsClassify = goodsClassify;
    }

    public GoodsSpecification getGoodsSpecification() {
        return goodsSpecification;
    }

    public void setGoodsSpecification(GoodsSpecification goodsSpecification) {
        this.goodsSpecification = goodsSpecification;
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
}
