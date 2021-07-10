package com.littlebuddha.bobogou.modules.entity.basic;

import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 厂商实体类
 */
public class Factory extends DataEntity<Factory> {

    private Integer factoryType;//厂商类型，1=供货商，2=批发企业
    private Integer productType;//厂商产品类型，1=普通厂家，2=中药饮片，3=消毒产品
    private String factoryName;//厂商名称/批发企业名称
    private String linkman;//联系人
    private String phone;//联系人手机号
    private String idNumber;//身份证号
    private String cardFront;//身份证正面地址
    private String cardBack;//身份证背面地址
    private String address;//厂商/企业联系地址
    private String businessLicense;//营业执照复印件，图片地址
    private String annualReport;//上一年度报告，图片地址，多张使用英文逗号分隔
    private String businessPermit;//经营许可证复印件，图片地址，多张使用英文逗号分隔
    private String basicAccount;//基本户复印件，图片地址，多张使用英文逗号分隔
    private String billingInformation;//开票信息
    private String sampleInvoiceTicket;//增值税发票票样（供货商填），图片地址，批发企业不填
    private String qualityGuarantee;//质保协议（供货商填），图片地址，批发企业不填
    private String sealImpression;//印章模板（公章、法人章、财务专用章、合同专用章、发票专用章、质管章、收货专用章、出库专用章），图片地址
    private String powerAttorney;//销售委托书（供货商填）/采购委托书（药房填），图片地址
    private String invoiceCounterparts;//出货同行单样票（供货商填），图片地址，批发企业不填
    private String bailorCard;//委托人身份证复印件（医院填），图片地址，如果诊所药房是委托人采购也需要填，委托人采购或则销售必填
    private String mandataryCard;//被委托人身份证复印件，图片地址，委托人采购或则销售必填
    private String takeDeliveryBailment;//收货委托书（批发企业填），图片地址，委托人采购或则销售必填
    private String foodBusinessLicense;//食品经营许可证图片地址，保健食品传，
    private Integer isBailor;//是否委托人采购或则销售，0=否，1=是
    private String operatorId;//操作人ID

    public Factory() {
    }

    public Factory(String id) {
        super(id);
    }

    public Integer getFactoryType() {
        return factoryType;
    }

    public void setFactoryType(Integer factoryType) {
        this.factoryType = factoryType;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCardFront() {
        return cardFront;
    }

    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }

    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getAnnualReport() {
        return annualReport;
    }

    public void setAnnualReport(String annualReport) {
        this.annualReport = annualReport;
    }

    public String getBusinessPermit() {
        return businessPermit;
    }

    public void setBusinessPermit(String businessPermit) {
        this.businessPermit = businessPermit;
    }

    public String getBasicAccount() {
        return basicAccount;
    }

    public void setBasicAccount(String basicAccount) {
        this.basicAccount = basicAccount;
    }

    public String getBillingInformation() {
        return billingInformation;
    }

    public void setBillingInformation(String billingInformation) {
        this.billingInformation = billingInformation;
    }

    public String getSampleInvoiceTicket() {
        return sampleInvoiceTicket;
    }

    public void setSampleInvoiceTicket(String sampleInvoiceTicket) {
        this.sampleInvoiceTicket = sampleInvoiceTicket;
    }

    public String getQualityGuarantee() {
        return qualityGuarantee;
    }

    public void setQualityGuarantee(String qualityGuarantee) {
        this.qualityGuarantee = qualityGuarantee;
    }

    public String getSealImpression() {
        return sealImpression;
    }

    public void setSealImpression(String sealImpression) {
        this.sealImpression = sealImpression;
    }

    public String getPowerAttorney() {
        return powerAttorney;
    }

    public void setPowerAttorney(String powerAttorney) {
        this.powerAttorney = powerAttorney;
    }

    public String getInvoiceCounterparts() {
        return invoiceCounterparts;
    }

    public void setInvoiceCounterparts(String invoiceCounterparts) {
        this.invoiceCounterparts = invoiceCounterparts;
    }

    public String getBailorCard() {
        return bailorCard;
    }

    public void setBailorCard(String bailorCard) {
        this.bailorCard = bailorCard;
    }

    public String getMandataryCard() {
        return mandataryCard;
    }

    public void setMandataryCard(String mandataryCard) {
        this.mandataryCard = mandataryCard;
    }

    public String getTakeDeliveryBailment() {
        return takeDeliveryBailment;
    }

    public void setTakeDeliveryBailment(String takeDeliveryBailment) {
        this.takeDeliveryBailment = takeDeliveryBailment;
    }

    public String getFoodBusinessLicense() {
        return foodBusinessLicense;
    }

    public void setFoodBusinessLicense(String foodBusinessLicense) {
        this.foodBusinessLicense = foodBusinessLicense;
    }

    public Integer getIsBailor() {
        return isBailor;
    }

    public void setIsBailor(Integer isBailor) {
        this.isBailor = isBailor;
    }

    public String getOperatorId() {
        if (createBy != null && StringUtils.isNotBlank(createBy.getId())){
            this.operatorId = createBy.getId();
        }
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
