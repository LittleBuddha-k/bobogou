package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import com.littlebuddha.bobogou.modules.entity.data.GoodsNorm;
import com.littlebuddha.bobogou.modules.mapper.basic.FactoryMapper;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsNormMapper;
import com.littlebuddha.bobogou.modules.service.basic.FactoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品品牌规格service层
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GoodsNormService extends CrudService<GoodsNorm, GoodsNormMapper> {

    @Autowired
    private GlobalSetting globalSetting;

    @Autowired
    private FactoryMapper factoryMapper;

    @Override
    public GoodsNorm get(GoodsNorm entity) {
        GoodsNorm goodsNorm = super.get(entity);
        if (goodsNorm != null && goodsNorm.getSampleBox() != null && StringUtils.isNotBlank(goodsNorm.getSampleBox())){
            String sampleBox = goodsNorm.getSampleBox();
            String[] split = sampleBox.split(",");
            String aa = "";
            for (String st : split) {
                aa = aa + globalSetting.getRootPath() + st + ",";
            }
            goodsNorm.setSampleBox(aa);
        }
        if (goodsNorm != null && goodsNorm.getOuterPackingBox() != null && StringUtils.isNotBlank(goodsNorm.getOuterPackingBox())){
            String outerPackingBox = goodsNorm.getOuterPackingBox();
            String[] split = outerPackingBox.split(",");
            String aa = "";
            for (String st : split) {
                aa = aa + globalSetting.getRootPath() + st + ",";
            }
            goodsNorm.setOuterPackingBox(aa);
        }
        if (goodsNorm != null && goodsNorm.getInstructionBook() != null && StringUtils.isNotBlank(goodsNorm.getInstructionBook())){
            String instructionBook = goodsNorm.getInstructionBook();
            String[] split = instructionBook.split(",");
            String aa = "";
            for (String st : split) {
                aa = aa + globalSetting.getRootPath() + st + ",";
            }
            goodsNorm.setInstructionBook(aa);
        }
        if (goodsNorm != null && goodsNorm.getOtherData() != null && StringUtils.isNotBlank(goodsNorm.getOtherData())){
            String otherData = goodsNorm.getOtherData();
            String[] split = otherData.split(",");
            String aa = "";
            for (String st : split) {
                aa = aa + globalSetting.getRootPath() + st + ",";
            }
            goodsNorm.setOtherData(aa);
        }
        if (goodsNorm != null && goodsNorm.getRelatedPictures() != null && StringUtils.isNotBlank(goodsNorm.getRelatedPictures())){
            String relatedPictures = goodsNorm.getRelatedPictures();
            String[] split = relatedPictures.split(",");
            String aa = "";
            for (String st : split) {
                aa = aa + globalSetting.getRootPath() + st + ",";
            }
            goodsNorm.setRelatedPictures(aa);
        }
        if (goodsNorm != null && goodsNorm.getInstructions() != null && StringUtils.isNotBlank(goodsNorm.getInstructions())){
            String instructions = goodsNorm.getInstructions();
            String[] split = instructions.split(",");
            String aa = "";
            for (String st : split) {
                aa = aa + globalSetting.getRootPath() + st + ",";
            }
            goodsNorm.setInstructions(aa);
        }
        if (goodsNorm != null && goodsNorm.getQualityStandard() != null && StringUtils.isNotBlank(goodsNorm.getQualityStandard())){
            String qualityStandard = goodsNorm.getQualityStandard();
            String[] split = qualityStandard.split(",");
            String aa = "";
            for (String st : split) {
                aa = aa + globalSetting.getRootPath() + st + ",";
            }
            goodsNorm.setQualityStandard(aa);
        }
        if (goodsNorm != null && goodsNorm.getSurveyReport() != null && StringUtils.isNotBlank(goodsNorm.getSurveyReport())){
            String surveyReport = goodsNorm.getSurveyReport();
            String[] split = surveyReport.split(",");
            String aa = "";
            for (String st : split) {
                aa = aa + globalSetting.getRootPath() + st + ",";
            }
            goodsNorm.setSurveyReport(aa);
        }
        if (goodsNorm != null && goodsNorm.getProductionBusinessLicense() != null && StringUtils.isNotBlank(goodsNorm.getProductionBusinessLicense())){
            String productionBusinessLicense = goodsNorm.getProductionBusinessLicense();
            String[] split = productionBusinessLicense.split(",");
            String aa = "";
            for (String st : split) {
                aa = aa + globalSetting.getRootPath() + st + ",";
            }
            goodsNorm.setProductionBusinessLicense(aa);
        }
        if (goodsNorm != null && goodsNorm.getProductionCertificate() != null && StringUtils.isNotBlank(goodsNorm.getProductionCertificate())){
            String productionCertificate = goodsNorm.getProductionCertificate();
            String[] split = productionCertificate.split(",");
            String aa = "";
            for (String st : split) {
                aa = aa + globalSetting.getRootPath() + st + ",";
            }
            goodsNorm.setProductionCertificate(aa);
        }
        return goodsNorm;
    }

    @Override
    public List<GoodsNorm> findList(GoodsNorm entity) {
        List<GoodsNorm> list = super.findList(entity);
        for (GoodsNorm goodsNorm : list) {
            if (goodsNorm.getSampleBox() != null && StringUtils.isNotBlank(goodsNorm.getSampleBox())){
                String sampleBox = goodsNorm.getSampleBox();
                String[] split = sampleBox.split(",");
                String aa = "";
                for (String st : split) {
                    aa = aa + globalSetting.getRootPath() + st + ",";
                }
                goodsNorm.setSampleBox(aa);
            }
            if (goodsNorm.getOuterPackingBox() != null && StringUtils.isNotBlank(goodsNorm.getOuterPackingBox())){
                String outerPackingBox = goodsNorm.getOuterPackingBox();
                String[] split = outerPackingBox.split(",");
                String aa = "";
                for (String st : split) {
                    aa = aa + globalSetting.getRootPath() + st + ",";
                }
                goodsNorm.setOuterPackingBox(aa);
            }
            if (goodsNorm.getInstructionBook() != null && StringUtils.isNotBlank(goodsNorm.getInstructionBook())){
                String instructionBook = goodsNorm.getInstructionBook();
                String[] split = instructionBook.split(",");
                String aa = "";
                for (String st : split) {
                    aa = aa + globalSetting.getRootPath() + st + ",";
                }
                goodsNorm.setInstructionBook(aa);
            }
            if (goodsNorm.getOtherData() != null && StringUtils.isNotBlank(goodsNorm.getOtherData())){
                String otherData = goodsNorm.getOtherData();
                String[] split = otherData.split(",");
                String aa = "";
                for (String st : split) {
                    aa = aa + globalSetting.getRootPath() + st + ",";
                }
                goodsNorm.setOtherData(aa);
            }
            if (goodsNorm.getRelatedPictures() != null && StringUtils.isNotBlank(goodsNorm.getRelatedPictures())){
                String relatedPictures = goodsNorm.getRelatedPictures();
                String[] split = relatedPictures.split(",");
                String aa = "";
                for (String st : split) {
                    aa = aa + globalSetting.getRootPath() + st + ",";
                }
                goodsNorm.setRelatedPictures(aa);
            }
            if (goodsNorm.getInstructions() != null && StringUtils.isNotBlank(goodsNorm.getInstructions())){
                String instructions = goodsNorm.getInstructions();
                String[] split = instructions.split(",");
                String aa = "";
                for (String st : split) {
                    aa = aa + globalSetting.getRootPath() + st + ",";
                }
                goodsNorm.setInstructions(aa);
            }
        }
        return list;
    }

    @Override
    public PageInfo<GoodsNorm> findPage(Page<GoodsNorm> page, GoodsNorm entity) {
        PageInfo<GoodsNorm> page1 = super.findPage(page, entity);
        List<GoodsNorm> list = page1.getList();
        for (GoodsNorm goodsNorm : list) {
            if (goodsNorm != null && StringUtils.isNotBlank(goodsNorm.getFactoryId().toString())){
                Factory factory = factoryMapper.get(new Factory(goodsNorm.getFactoryId().toString()));
                goodsNorm.setFactory(factory);
            }
            if (goodsNorm.getSampleBox() != null && StringUtils.isNotBlank(goodsNorm.getSampleBox())){
                String sampleBox = goodsNorm.getSampleBox();
                String[] split = sampleBox.split(",");
                String aa = "";
                for (String st : split) {
                    aa = aa + globalSetting.getRootPath() + st + ",";
                }
                goodsNorm.setSampleBox(aa);
            }
            if (goodsNorm.getOuterPackingBox() != null && StringUtils.isNotBlank(goodsNorm.getOuterPackingBox())){
                String outerPackingBox = goodsNorm.getOuterPackingBox();
                String[] split = outerPackingBox.split(",");
                String aa = "";
                for (String st : split) {
                    aa = aa + globalSetting.getRootPath() + st + ",";
                }
                goodsNorm.setOuterPackingBox(aa);
            }
            if (goodsNorm.getInstructionBook() != null && StringUtils.isNotBlank(goodsNorm.getInstructionBook())){
                String instructionBook = goodsNorm.getInstructionBook();
                String[] split = instructionBook.split(",");
                String aa = "";
                for (String st : split) {
                    aa = aa + globalSetting.getRootPath() + st + ",";
                }
                goodsNorm.setInstructionBook(aa);
            }
            if (goodsNorm.getOtherData() != null && StringUtils.isNotBlank(goodsNorm.getOtherData())){
                String otherData = goodsNorm.getOtherData();
                String[] split = otherData.split(",");
                String aa = "";
                for (String st : split) {
                    aa = aa + globalSetting.getRootPath() + st + ",";
                }
                goodsNorm.setOtherData(aa);
            }
            if (goodsNorm.getRelatedPictures() != null && StringUtils.isNotBlank(goodsNorm.getRelatedPictures())){
                String relatedPictures = goodsNorm.getRelatedPictures();
                String[] split = relatedPictures.split(",");
                String aa = "";
                for (String st : split) {
                    aa = aa + globalSetting.getRootPath() + st + ",";
                }
                goodsNorm.setRelatedPictures(aa);
            }
            if (goodsNorm.getInstructions() != null && StringUtils.isNotBlank(goodsNorm.getInstructions())){
                String instructions = goodsNorm.getInstructions();
                String[] split = instructions.split(",");
                String aa = "";
                for (String st : split) {
                    aa = aa + globalSetting.getRootPath() + st + ",";
                }
                goodsNorm.setInstructions(aa);
            }
        }
        return page1;
    }

    @Override
    public int save(GoodsNorm entity) {
        entity.setIdType("AUTO");
        if (entity.getSampleBox() != null){
            entity.setSampleBox(entity.getSampleBox().replaceAll(globalSetting.getRootPath(),""));
        }
        if (entity.getOuterPackingBox() != null){
            entity.setOuterPackingBox(entity.getOuterPackingBox().replaceAll(globalSetting.getRootPath(),""));
        }
        if (entity.getInstructionBook() != null){
            entity.setInstructionBook(entity.getInstructionBook().replaceAll(globalSetting.getRootPath(),""));
        }
        if (entity.getOtherData() != null){
            entity.setOtherData(entity.getOtherData().replaceAll(globalSetting.getRootPath(),""));
        }
        if (entity.getRelatedPictures() != null){
            entity.setRelatedPictures(entity.getRelatedPictures().replaceAll(globalSetting.getRootPath(),""));
        }
        if (entity.getInstructions() != null){
            entity.setInstructions(entity.getInstructions().replaceAll(globalSetting.getRootPath(),""));
        }
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(GoodsNorm entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(GoodsNorm entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<GoodsNorm> findRecoveryPage(Page<GoodsNorm> page, GoodsNorm entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(GoodsNorm entity) {
        return super.recovery(entity);
    }
}
