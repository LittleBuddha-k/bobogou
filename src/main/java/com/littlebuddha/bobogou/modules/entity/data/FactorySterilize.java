package com.littlebuddha.bobogou.modules.entity.data;

import com.littlebuddha.bobogou.common.utils.excel.ExcelField;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;

/**
 * 消毒产品实体类
 */
public class FactorySterilize extends DataEntity<FactorySterilize> {

    private Integer factoryId;//厂商ID，关联md_factory表
    private Integer factoryName;//厂商ID，关联md_factory表
    private Integer productType;//厂商产品类型，（与md_factory表中的字段相同），1=普通厂家，2=中药饮片，3=消毒产品
    private String safetyAssessmentReport;//安全评估报告地址，多张图片使用英文逗号分隔，消毒产品必填
    private String filingSafetyEvaluation;//安全评价备案，消毒产品必填
    private String companyStandard;//企业标准，消毒产品必填
    private String provincialInspectionReport;//省检报告（注意检验菌范围），消毒产品必填
    private String packagingRecord;//包装备案，消毒产品必填
    private String productionBusinessLicense;//生产厂家营业执照，消毒产品必填
    private String sanitaryLicenseDisinfectedProducts;//消毒产品卫生许可证，消毒产品必填
    private String drugManufacturingErtificate;//药品生产许可证，中药必填
    private String gmpErtificate;//药品GMP证书，中药必填
    private String qualitySystem;//质量体系调查表，中药必填
    private String qualifiedSupplier;//合格供货方档案表

    public FactorySterilize() {
    }

    public FactorySterilize(String id) {
        super(id);
    }

    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    public Integer getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(Integer factoryName) {
        this.factoryName = factoryName;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getSafetyAssessmentReport() {
        return safetyAssessmentReport;
    }

    public void setSafetyAssessmentReport(String safetyAssessmentReport) {
        this.safetyAssessmentReport = safetyAssessmentReport;
    }

    public String getFilingSafetyEvaluation() {
        return filingSafetyEvaluation;
    }

    public void setFilingSafetyEvaluation(String filingSafetyEvaluation) {
        this.filingSafetyEvaluation = filingSafetyEvaluation;
    }

    public String getCompanyStandard() {
        return companyStandard;
    }

    public void setCompanyStandard(String companyStandard) {
        this.companyStandard = companyStandard;
    }

    public String getProvincialInspectionReport() {
        return provincialInspectionReport;
    }

    public void setProvincialInspectionReport(String provincialInspectionReport) {
        this.provincialInspectionReport = provincialInspectionReport;
    }

    public String getPackagingRecord() {
        return packagingRecord;
    }

    public void setPackagingRecord(String packagingRecord) {
        this.packagingRecord = packagingRecord;
    }

    public String getProductionBusinessLicense() {
        return productionBusinessLicense;
    }

    public void setProductionBusinessLicense(String productionBusinessLicense) {
        this.productionBusinessLicense = productionBusinessLicense;
    }

    public String getSanitaryLicenseDisinfectedProducts() {
        return sanitaryLicenseDisinfectedProducts;
    }

    public void setSanitaryLicenseDisinfectedProducts(String sanitaryLicenseDisinfectedProducts) {
        this.sanitaryLicenseDisinfectedProducts = sanitaryLicenseDisinfectedProducts;
    }

    public String getDrugManufacturingErtificate() {
        return drugManufacturingErtificate;
    }

    public void setDrugManufacturingErtificate(String drugManufacturingErtificate) {
        this.drugManufacturingErtificate = drugManufacturingErtificate;
    }

    public String getGmpErtificate() {
        return gmpErtificate;
    }

    public void setGmpErtificate(String gmpErtificate) {
        this.gmpErtificate = gmpErtificate;
    }

    public String getQualitySystem() {
        return qualitySystem;
    }

    public void setQualitySystem(String qualitySystem) {
        this.qualitySystem = qualitySystem;
    }

    public String getQualifiedSupplier() {
        return qualifiedSupplier;
    }

    public void setQualifiedSupplier(String qualifiedSupplier) {
        this.qualifiedSupplier = qualifiedSupplier;
    }
}
