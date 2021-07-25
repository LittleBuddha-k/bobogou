package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 商品品牌规格实体类
 */
public class GoodsSpecification extends DataEntity<GoodsSpecification> {

    private String commonName = "";//通用名称
    private String authorization = "";//批准号
    private String manufacturingEnterprise = "";//生产企业
    private String specification = "";//产品规格
    private String dosage = "";//用法用量
    private String attending = "";//使用症/功能主治
    private String adverseReaction = "";//不良反应
    private Integer prescriptionType = 0;//药品分类，0=处方药，1=（非处方药）中成药，2=（非处方药）化学药剂
    private String storageCondition = "";//储存条件
    private String component = "";//成分
    private Integer validity = 0;//有效期
    private String productName = "";//产品名称
    private String companies = "";//持有企业
    private String taboo = "";//用药禁忌
    private String matterAttention = "";//注意事项
    private String applicationType = "";//适用类型
    private String type = "";//类别，比如：中药、西药等
    private String fitPeople = "";//适用人群
    private String dosageForm = "";//剂型
    private String indications = "";//适用症状
    private String directions = "";//使用方法
    private String takingCycle = "";//服用周期
    private Integer accountId = 0;//操作人员ID

    public GoodsSpecification() {
    }

    public GoodsSpecification(String id) {
        super(id);
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getManufacturingEnterprise() {
        return manufacturingEnterprise;
    }

    public void setManufacturingEnterprise(String manufacturingEnterprise) {
        this.manufacturingEnterprise = manufacturingEnterprise;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getAttending() {
        return attending;
    }

    public void setAttending(String attending) {
        this.attending = attending;
    }

    public String getAdverseReaction() {
        return adverseReaction;
    }

    public void setAdverseReaction(String adverseReaction) {
        this.adverseReaction = adverseReaction;
    }

    public Integer getPrescriptionType() {
        return prescriptionType;
    }

    public void setPrescriptionType(Integer prescriptionType) {
        this.prescriptionType = prescriptionType;
    }

    public String getStorageCondition() {
        return storageCondition;
    }

    public void setStorageCondition(String storageCondition) {
        this.storageCondition = storageCondition;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanies() {
        return companies;
    }

    public void setCompanies(String companies) {
        this.companies = companies;
    }

    public String getTaboo() {
        return taboo;
    }

    public void setTaboo(String taboo) {
        this.taboo = taboo;
    }

    public String getMatterAttention() {
        return matterAttention;
    }

    public void setMatterAttention(String matterAttention) {
        this.matterAttention = matterAttention;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFitPeople() {
        return fitPeople;
    }

    public void setFitPeople(String fitPeople) {
        this.fitPeople = fitPeople;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getTakingCycle() {
        return takingCycle;
    }

    public void setTakingCycle(String takingCycle) {
        this.takingCycle = takingCycle;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
