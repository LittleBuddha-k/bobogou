package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.FactorySterilize;
import com.littlebuddha.bobogou.modules.entity.data.FactorySterilize;
import com.littlebuddha.bobogou.modules.mapper.data.FactorySterilizeMapper;
import com.littlebuddha.bobogou.modules.mapper.data.FactorySterilizeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FactorySterilizeService extends CrudService<FactorySterilize, FactorySterilizeMapper> {

    @Autowired
    private GlobalSetting globalSetting;

    @Autowired
    private FactorySterilizeMapper factorySterilizeMapper;

    @Override
    public FactorySterilize get(FactorySterilize entity) {
        FactorySterilize factorySterilize = factorySterilizeMapper.get(entity);
        if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getSafetyAssessmentReport())){
            String safetyAssessmentReport = factorySterilize.getSafetyAssessmentReport();
            String aa = "";
            String[] split = safetyAssessmentReport.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factorySterilize.setSafetyAssessmentReport(aa);
        }
        if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getFilingSafetyEvaluation())){
            String filingSafetyEvaluation = factorySterilize.getFilingSafetyEvaluation();
            String aa = "";
            String[] split = filingSafetyEvaluation.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factorySterilize.setFilingSafetyEvaluation(aa);
        }
        if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getCompanyStandard())){
            String companyStandard = factorySterilize.getCompanyStandard();
            String aa = "";
            String[] split = companyStandard.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factorySterilize.setCompanyStandard(aa);
        }
        if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getProvincialInspectionReport())){
            String provincialInspectionReport = factorySterilize.getProvincialInspectionReport();
            String aa = "";
            String[] split = provincialInspectionReport.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factorySterilize.setProvincialInspectionReport(aa);
        }
        if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getPackagingRecord())){
            String packagingRecord = factorySterilize.getPackagingRecord();
            String aa = "";
            String[] split = packagingRecord.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factorySterilize.setPackagingRecord(aa);
        }
        if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getProductionBusinessLicense())){
            String productionBusinessLicense = factorySterilize.getProductionBusinessLicense();
            String aa = "";
            String[] split = productionBusinessLicense.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factorySterilize.setProductionBusinessLicense(aa);
        }
        if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getSanitaryLicenseDisinfectedProducts())){
            String sanitaryLicenseDisinfectedProducts = factorySterilize.getSanitaryLicenseDisinfectedProducts();
            String aa = "";
            String[] split = sanitaryLicenseDisinfectedProducts.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factorySterilize.setSanitaryLicenseDisinfectedProducts(aa);
        }
        if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getDrugManufacturingErtificate())){
            String drugManufacturingErtificate = factorySterilize.getDrugManufacturingErtificate();
            String aa = "";
            String[] split = drugManufacturingErtificate.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factorySterilize.setDrugManufacturingErtificate(aa);
        }
        if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getGmpErtificate())){
            String gmpErtificate = factorySterilize.getGmpErtificate();
            String aa = "";
            String[] split = gmpErtificate.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factorySterilize.setGmpErtificate(aa);
        }
        if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getQualitySystem())){
            String qualitySystem = factorySterilize.getQualitySystem();
            String aa = "";
            String[] split = qualitySystem.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factorySterilize.setQualitySystem(aa);
        }
        if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getQualifiedSupplier())){
            String qualifiedSupplier = factorySterilize.getQualifiedSupplier();
            String aa = "";
            String[] split = qualifiedSupplier.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            factorySterilize.setQualifiedSupplier(aa);
        }
        return factorySterilize;
    }

    @Override
    public List<FactorySterilize> findList(FactorySterilize entity) {
        List<FactorySterilize> list = super.findList(entity);
        for (FactorySterilize factorySterilize : list) {
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getSafetyAssessmentReport())){
                String safetyAssessmentReport = factorySterilize.getSafetyAssessmentReport();
                String aa = "";
                String[] split = safetyAssessmentReport.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setSafetyAssessmentReport(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getFilingSafetyEvaluation())){
                String filingSafetyEvaluation = factorySterilize.getFilingSafetyEvaluation();
                String aa = "";
                String[] split = filingSafetyEvaluation.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setFilingSafetyEvaluation(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getCompanyStandard())){
                String companyStandard = factorySterilize.getCompanyStandard();
                String aa = "";
                String[] split = companyStandard.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setCompanyStandard(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getProvincialInspectionReport())){
                String provincialInspectionReport = factorySterilize.getProvincialInspectionReport();
                String aa = "";
                String[] split = provincialInspectionReport.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setProvincialInspectionReport(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getPackagingRecord())){
                String packagingRecord = factorySterilize.getPackagingRecord();
                String aa = "";
                String[] split = packagingRecord.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setPackagingRecord(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getProductionBusinessLicense())){
                String productionBusinessLicense = factorySterilize.getProductionBusinessLicense();
                String aa = "";
                String[] split = productionBusinessLicense.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setProductionBusinessLicense(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getSanitaryLicenseDisinfectedProducts())){
                String sanitaryLicenseDisinfectedProducts = factorySterilize.getSanitaryLicenseDisinfectedProducts();
                String aa = "";
                String[] split = sanitaryLicenseDisinfectedProducts.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setSanitaryLicenseDisinfectedProducts(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getDrugManufacturingErtificate())){
                String drugManufacturingErtificate = factorySterilize.getDrugManufacturingErtificate();
                String aa = "";
                String[] split = drugManufacturingErtificate.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setDrugManufacturingErtificate(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getGmpErtificate())){
                String gmpErtificate = factorySterilize.getGmpErtificate();
                String aa = "";
                String[] split = gmpErtificate.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setGmpErtificate(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getQualitySystem())){
                String qualitySystem = factorySterilize.getQualitySystem();
                String aa = "";
                String[] split = qualitySystem.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setQualitySystem(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getQualifiedSupplier())){
                String qualifiedSupplier = factorySterilize.getQualifiedSupplier();
                String aa = "";
                String[] split = qualifiedSupplier.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setQualifiedSupplier(aa);
            }
        }
        return list;
    }

    @Override
    public PageInfo<FactorySterilize> findPage(Page<FactorySterilize> page, FactorySterilize entity) {
        PageInfo<FactorySterilize> page1 = super.findPage(page, entity);
        List<FactorySterilize> list = page1.getList();
        for (FactorySterilize factorySterilize : list) {
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getSafetyAssessmentReport())){
                String safetyAssessmentReport = factorySterilize.getSafetyAssessmentReport();
                String aa = "";
                String[] split = safetyAssessmentReport.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setSafetyAssessmentReport(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getFilingSafetyEvaluation())){
                String filingSafetyEvaluation = factorySterilize.getFilingSafetyEvaluation();
                String aa = "";
                String[] split = filingSafetyEvaluation.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setFilingSafetyEvaluation(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getCompanyStandard())){
                String companyStandard = factorySterilize.getCompanyStandard();
                String aa = "";
                String[] split = companyStandard.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setCompanyStandard(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getProvincialInspectionReport())){
                String provincialInspectionReport = factorySterilize.getProvincialInspectionReport();
                String aa = "";
                String[] split = provincialInspectionReport.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setProvincialInspectionReport(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getPackagingRecord())){
                String packagingRecord = factorySterilize.getPackagingRecord();
                String aa = "";
                String[] split = packagingRecord.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setPackagingRecord(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getProductionBusinessLicense())){
                String productionBusinessLicense = factorySterilize.getProductionBusinessLicense();
                String aa = "";
                String[] split = productionBusinessLicense.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setProductionBusinessLicense(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getSanitaryLicenseDisinfectedProducts())){
                String sanitaryLicenseDisinfectedProducts = factorySterilize.getSanitaryLicenseDisinfectedProducts();
                String aa = "";
                String[] split = sanitaryLicenseDisinfectedProducts.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setSanitaryLicenseDisinfectedProducts(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getDrugManufacturingErtificate())){
                String drugManufacturingErtificate = factorySterilize.getDrugManufacturingErtificate();
                String aa = "";
                String[] split = drugManufacturingErtificate.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setDrugManufacturingErtificate(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getGmpErtificate())){
                String gmpErtificate = factorySterilize.getGmpErtificate();
                String aa = "";
                String[] split = gmpErtificate.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setGmpErtificate(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getQualitySystem())){
                String qualitySystem = factorySterilize.getQualitySystem();
                String aa = "";
                String[] split = qualitySystem.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setQualitySystem(aa);
            }
            if (factorySterilize != null && StringUtils.isNotBlank(factorySterilize.getQualifiedSupplier())){
                String qualifiedSupplier = factorySterilize.getQualifiedSupplier();
                String aa = "";
                String[] split = qualifiedSupplier.split(",");
                for (String image : split) {
                    aa = aa + globalSetting.getRootPath() + image + ",";
                }
                factorySterilize.setQualifiedSupplier(aa);
            }
        }
        return page1;
    }

    @Override
    public int save(FactorySterilize entity) {
        if(StringUtils.isNotBlank(entity.getSafetyAssessmentReport())){
            entity.setSafetyAssessmentReport(entity.getSafetyAssessmentReport().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getFilingSafetyEvaluation())){
            entity.setFilingSafetyEvaluation(entity.getFilingSafetyEvaluation().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getCompanyStandard())){
            entity.setCompanyStandard(entity.getCompanyStandard().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getProvincialInspectionReport())){
            entity.setProvincialInspectionReport(entity.getProvincialInspectionReport().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getPackagingRecord())){
            entity.setPackagingRecord(entity.getPackagingRecord().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getProductionBusinessLicense())){
            entity.setProductionBusinessLicense(entity.getProductionBusinessLicense().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getSanitaryLicenseDisinfectedProducts())){
            entity.setSanitaryLicenseDisinfectedProducts(entity.getSanitaryLicenseDisinfectedProducts().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getDrugManufacturingErtificate())){
            entity.setDrugManufacturingErtificate(entity.getDrugManufacturingErtificate().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getGmpErtificate())){
            entity.setGmpErtificate(entity.getGmpErtificate().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getQualitySystem())){
            entity.setQualitySystem(entity.getQualitySystem().replaceAll(globalSetting.getRootPath(),""));
        }
        if(StringUtils.isNotBlank(entity.getQualifiedSupplier())){
            entity.setQualifiedSupplier(entity.getQualifiedSupplier().replaceAll(globalSetting.getRootPath(),""));
        }
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(FactorySterilize entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(FactorySterilize entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<FactorySterilize> findRecoveryPage(Page<FactorySterilize> page, FactorySterilize entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(FactorySterilize entity) {
        return super.recovery(entity);
    }
}
