package com.littlebuddha.bobogou.modules.entity.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 药品实体类
 */
public class Goods extends DataEntity<Goods> {

    private String name;//商品名称
    private String commonName = "";//商品通用名称
    private String certificateImageWatermark = "";//药品资质证书图片地址，一张，带水印
    private String supplierBusinessLicense = "";//供货商经营许可证带水印
    private String certificateImage = "";//药品资质证书图片地址，一张，不带水印，不展示后期可能会用

    private Banner banner;//轮播图外键
    private String frontImages = "";//商品展示轮播图，最多六张，使用英文逗号分隔
    private String backImages = "";//商品展示轮播图，最多六张，使用英文逗号分隔
    private String bottomImages = "";//商品展示轮播图，最多六张，使用英文逗号分隔

    private Factory factory;//
    private Integer factoryId = 0;//厂商ID，关联厂商md_factory表
    private String factoryName;//厂商名称，关联厂商md_factory表
    private GoodsTag goodsTag;//商品标签外键
    private String tagId = "";//商品标签ID,多个标签使用英文逗号分隔，关联md_tag表
    private GoodsType goodsType;//其他分类外键
    private String goodsTypeId = "";//药品其它分类ID，多个使用英文逗号分隔，在开始和结尾也需要加英文逗号

    private String electronicCode = "";//电子监管码
    private String barCode;//条形码

    private String approvalPeriod = "";//药品批文有效期

    private Double purchasingPrice = 0.0;//进价，单位：分
    private Double originalCost = 0.0;//零售价，单位：分
    private Double sellingPrice = 0.0;//会员价，单位：分
    private Double vipPrice = 0.0;//集采价，单位：分
    private String specification = "";//规格
    private Double weight = 0.0;//重量，单位：g，在需要运费的时候需要通过重量来计算运费，同一订单会计算订单所有商品的重量，小数点向上取整计算重量，运费按kg计算
    private Integer amount = 0;//数量
    private Integer usedAmount = 0;//已分配
    private Integer stockAmount = 0;//库存数
    private Integer salesVolume = 0;//销量
    private String effect = "";//功效

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date producedTime;//生产日期
    private String expirationDate = "";//保质期（单位：月）
    private Double applauseRate = 0.000;//好评率，单位：%，好评率大于90%为推荐商品
    private Integer comments = 0;//评论数
    private Double healthBeans = 0.0;//健康豆，购买商品可获得的播播豆
    private Double integral = 0.0;//积分，购买该商品可以获得的积分
    private Integer isFreight = 0;//是否需要运费哦：0--不需要，1=需要
    private Double administrativeFee = 0.0;//类型商品管理费（商品实际价格的管理费用），单位：100%，最多三位小数，
    private Double provinceRatio = 0.0;//省代理(经理人)提成比例，单位：100%，最多三位小数
    private Double cityRatio = 0.0;//市代理(经理人)提成比例，单位：100%，最多三位小数
    private Double districtRatio = 0.0;//区代理(经纪人)提成比例，单位：100%，最多三位小数
    private Integer isMarket = 0;//是否销售，0=在售，2停售
    private String accountId = "";//最后操作人ID

    private GoodsClassify goodsClassify;//商品分类外键
    private String goodsClassifyLevelOneIds = "";//商品一级分类
    private String goodsClassifyLevelTwoIds = "";//商品二级分类
    private String goodsClassifyLevelThreeIds = "";//商品三级分类

    private GoodsSpecification goodsSpecification;//商品规格外键

    private GoodsInfo goodsInfo;//商品详情外键

    private GoodsNorm goodsNorm;//商品规范、关联新增

    private String actStatus = "0";//流程审核状态---0:默认未提交   1：已提交
    private String nextRole = "";//下一审核角色
    private String actType = "data_goods_act";//流程类型，用于记录审核历史记录

    private Date effectiveDate;//资质截止日期

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

    @ExcelField(title = "通用名称", align = 2, sort = 2)
    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getCertificateImageWatermark() {
        return certificateImageWatermark;
    }

    public void setCertificateImageWatermark(String certificateImageWatermark) {
        this.certificateImageWatermark = certificateImageWatermark;
    }

    public String getSupplierBusinessLicense() {
        return supplierBusinessLicense;
    }

    public void setSupplierBusinessLicense(String supplierBusinessLicense) {
        this.supplierBusinessLicense = supplierBusinessLicense;
    }

    public String getCertificateImage() {
        return certificateImage;
    }

    public void setCertificateImage(String certificateImage) {
        this.certificateImage = certificateImage;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public String getFrontImages() {
        return frontImages;
    }

    public void setFrontImages(String frontImages) {
        this.frontImages = frontImages;
    }

    public String getBackImages() {
        return backImages;
    }

    public void setBackImages(String backImages) {
        this.backImages = backImages;
    }

    public String getBottomImages() {
        return bottomImages;
    }

    public void setBottomImages(String bottomImages) {
        this.bottomImages = bottomImages;
    }

    public Factory getFactory() {
        return factory;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    @ExcelField(title = "厂商", align = 2, sort = 3)
    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
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

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    @ExcelField(title = "电子监管码", align = 2, sort = 4)
    public String getElectronicCode() {
        return electronicCode;
    }

    public void setElectronicCode(String electronicCode) {
        this.electronicCode = electronicCode;
    }

    @ExcelField(title = "条形码", align = 2, sort = 5)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @ExcelField(title = "药品批文有效期", align = 2, sort = 6)
    public String getApprovalPeriod() {
        return approvalPeriod;
    }

    public void setApprovalPeriod(String approvalPeriod) {
        this.approvalPeriod = approvalPeriod;
    }

    public Double getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(Double purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    public Double getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(Double originalCost) {
        this.originalCost = originalCost;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
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

    public Integer getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getProducedTime() {
        return producedTime;
    }

    public void setProducedTime(Date producedTime) {
        this.producedTime = producedTime;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Double getApplauseRate() {
        return applauseRate;
    }

    public void setApplauseRate(Double applauseRate) {
        this.applauseRate = applauseRate;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
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

    public Integer getIsFreight() {
        return isFreight;
    }

    public void setIsFreight(Integer isFreight) {
        this.isFreight = isFreight;
    }

    public Double getAdministrativeFee() {
        return administrativeFee;
    }

    public void setAdministrativeFee(Double administrativeFee) {
        this.administrativeFee = administrativeFee;
    }

    public Double getProvinceRatio() {
        return provinceRatio;
    }

    public void setProvinceRatio(Double provinceRatio) {
        this.provinceRatio = provinceRatio;
    }

    public Double getCityRatio() {
        return cityRatio;
    }

    public void setCityRatio(Double cityRatio) {
        this.cityRatio = cityRatio;
    }

    public Double getDistrictRatio() {
        return districtRatio;
    }

    public void setDistrictRatio(Double districtRatio) {
        this.districtRatio = districtRatio;
    }

    public Integer getIsMarket() {
        return isMarket;
    }

    public void setIsMarket(Integer isMarket) {
        this.isMarket = isMarket;
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

    public GoodsClassify getGoodsClassify() {
        return goodsClassify;
    }

    public void setGoodsClassify(GoodsClassify goodsClassify) {
        this.goodsClassify = goodsClassify;
    }

    public String getGoodsClassifyLevelOneIds() {
        return goodsClassifyLevelOneIds;
    }

    public void setGoodsClassifyLevelOneIds(String goodsClassifyLevelOneIds) {
        this.goodsClassifyLevelOneIds = goodsClassifyLevelOneIds;
    }

    public String getGoodsClassifyLevelTwoIds() {
        return goodsClassifyLevelTwoIds;
    }

    public void setGoodsClassifyLevelTwoIds(String goodsClassifyLevelTwoIds) {
        this.goodsClassifyLevelTwoIds = goodsClassifyLevelTwoIds;
    }

    public String getGoodsClassifyLevelThreeIds() {
        return goodsClassifyLevelThreeIds;
    }

    public void setGoodsClassifyLevelThreeIds(String goodsClassifyLevelThreeIds) {
        this.goodsClassifyLevelThreeIds = goodsClassifyLevelThreeIds;
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

    public GoodsNorm getGoodsNorm() {
        return goodsNorm;
    }

    public void setGoodsNorm(GoodsNorm goodsNorm) {
        this.goodsNorm = goodsNorm;
    }

    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    public String getNextRole() {
        return nextRole;
    }

    public void setNextRole(String nextRole) {
        this.nextRole = nextRole;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
