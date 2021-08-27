package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

/**
 * 商品品牌规格实体类
 */
public class GoodsSpecification extends DataEntity<GoodsSpecification> {

    private String authorization = "";//批准号
    private String manufacturingEnterprise = "";//生产企业
    private String dosage = "";//用法用量
    private String adverseReaction = "";//不良反应
    private String storageCondition = "";//储存条件
    private String component = "";//成分
    private String companies = "";//持有企业
    private String taboo = "";//用药禁忌
    private String matterAttention = "";//注意事项
    private String dosageForm = "";//剂型
    private Integer accountId = 0;//操作人员ID

    public GoodsSpecification() {
    }

    public GoodsSpecification(String id) {
        super(id);
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

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getAdverseReaction() {
        return adverseReaction;
    }

    public void setAdverseReaction(String adverseReaction) {
        this.adverseReaction = adverseReaction;
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

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
