package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import org.apache.commons.lang3.StringUtils;

/**
 * 商品品牌规格实体类
 */
public class GoodsNorm extends DataEntity<GoodsNorm> {

    private Factory factory;
    private Integer factoryId = 0;//厂商ID
    private String factoryName = "";//厂商ID
    private Goods goods;//商品外键
    private Integer goodsId = 0;//商品ID
    private String goodsName = "";//商品ID
    private String sampleBox = "";//样盒图片地址
    private String outerPackingBox = "";//产品外包装盒，使用英文逗号分隔，按照近景图、正面图、底面图、有文字的侧面图先后顺序保存
    private String instructionBook = "";//说明书图片
    private String otherData = "";//其他获得药监批准的图文广告、学术资料图片，多张使用英文逗号分隔
    private String relatedPictures = "";//其他必要的，方便识别、了解产品的相关图片，多张使用英文逗号分隔
    private String instructions = "";//批件
    private String qualityStandard = "";//质量标准
    private String surveyReport = "";//省检验报告
    private String prices = "";//物价
    private String productionBusinessLicense = "";//生产企业营业执照地址
    private String productionCertificate = "";//生产企业生产许可证地址

    public GoodsNorm() {
    }

    public GoodsNorm(String id) {
        super(id);
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

    public String getFactoryName() {
        if (factory != null && StringUtils.isNotBlank(factory.getFactoryName())){
            this.factoryName = factory.getFactoryName();
        }
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSampleBox() {
        return sampleBox;
    }

    public void setSampleBox(String sampleBox) {
        this.sampleBox = sampleBox;
    }

    public String getOuterPackingBox() {
        return outerPackingBox;
    }

    public void setOuterPackingBox(String outerPackingBox) {
        this.outerPackingBox = outerPackingBox;
    }

    public String getInstructionBook() {
        return instructionBook;
    }

    public void setInstructionBook(String instructionBook) {
        this.instructionBook = instructionBook;
    }

    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }

    public String getRelatedPictures() {
        return relatedPictures;
    }

    public void setRelatedPictures(String relatedPictures) {
        this.relatedPictures = relatedPictures;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getQualityStandard() {
        return qualityStandard;
    }

    public void setQualityStandard(String qualityStandard) {
        this.qualityStandard = qualityStandard;
    }

    public String getSurveyReport() {
        return surveyReport;
    }

    public void setSurveyReport(String surveyReport) {
        this.surveyReport = surveyReport;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getProductionBusinessLicense() {
        return productionBusinessLicense;
    }

    public void setProductionBusinessLicense(String productionBusinessLicense) {
        this.productionBusinessLicense = productionBusinessLicense;
    }

    public String getProductionCertificate() {
        return productionCertificate;
    }

    public void setProductionCertificate(String productionCertificate) {
        this.productionCertificate = productionCertificate;
    }
}
