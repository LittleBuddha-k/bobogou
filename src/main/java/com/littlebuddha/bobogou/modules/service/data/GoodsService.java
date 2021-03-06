package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.common.utils.MarkdownUtils;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.*;
import com.littlebuddha.bobogou.modules.mapper.data.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GoodsService extends CrudService<Goods, GoodsMapper> {

    @Autowired
    private GlobalSetting globalSetting;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Resource
    private GoodsClassifyMapper goodsClassifyMapper;

    @Resource
    private GoodsSpecificationMapper goodsSpecificationMapper;

    @Resource
    private GoodsNormMapper goodsNormMapper;

    @Resource
    private ClassifyMapper classifyMapper;

    @Override
    public Goods get(Goods entity) {
        Goods goods = super.get(entity);
        if (goods != null && StringUtils.isNotBlank(goods.getCertificateImageWatermark())) {
            String certificateImageWatermark = goods.getCertificateImageWatermark();
            goods.setCertificateImageWatermark(globalSetting.getRootPath() + certificateImageWatermark);
        }
        if (goods != null && StringUtils.isNotBlank(goods.getSupplierBusinessLicense())) {
            String supplierBusinessLicense = goods.getSupplierBusinessLicense();
            goods.setSupplierBusinessLicense(globalSetting.getRootPath() + supplierBusinessLicense);
        }
        if (goods != null && StringUtils.isNotBlank(goods.getFrontImages())) {
            String frontImages = goods.getFrontImages();
            String aa = "";
            String[] split = frontImages.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            goods.setFrontImages(aa);
        }
        if (goods != null && StringUtils.isNotBlank(goods.getBackImages())) {
            String backImages = goods.getBackImages();
            String aa = "";
            String[] split = backImages.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            goods.setBackImages(aa);
        }
        if (goods != null && StringUtils.isNotBlank(goods.getBottomImages())) {
            String bottomImages = goods.getBottomImages();
            String aa = "";
            String[] split = bottomImages.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            goods.setBottomImages(aa);
        }
        if (goods != null && goods.getPurchasingPrice() != null) {
            goods.setPurchasingPrice(goods.getPurchasingPrice() / 100);
        }
        if (goods != null && goods.getOriginalCost() != null) {
            goods.setOriginalCost(goods.getOriginalCost() / 100);
        }
        if (goods != null && goods.getSellingPrice() != null) {
            goods.setSellingPrice(goods.getSellingPrice() / 100);
        }
        if (goods != null && goods.getVipPrice() != null) {
            goods.setVipPrice(goods.getVipPrice() / 100);
        }
        return goods;
    }

    @Override
    public List<Goods> findList(Goods entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Goods> findPage(Page<Goods> page, Goods entity) {
        if (entity != null) {
            String name = StringUtils.deleteWhitespace(entity.getName());
            entity.setName(name);
        }
        PageInfo<Goods> page1 = super.findPage(page, entity);
        List<Goods> list = page1.getList();
        for (Goods goods : list) {
            goods.setPurchasingPrice(goods.getPurchasingPrice() / 100);
            goods.setOriginalCost(goods.getOriginalCost() / 100);
            goods.setSellingPrice(goods.getSellingPrice() / 100);
            goods.setVipPrice(goods.getVipPrice() / 100);
        }
        return page1;
    }

    @Override
    @Transactional
    public int save(Goods entity) {
        entity.setIdType("AUTO");
        //??????????????????????????????
        if (entity != null && StringUtils.isNotBlank(entity.getCertificateImageWatermark())) {
            entity.setCertificateImageWatermark(entity.getCertificateImageWatermark().replaceAll(globalSetting.getRootPath(), ""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getSupplierBusinessLicense())) {
            entity.setSupplierBusinessLicense(entity.getSupplierBusinessLicense().replaceAll(globalSetting.getRootPath(), ""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getFrontImages())) {
            entity.setFrontImages(entity.getFrontImages().replaceAll(globalSetting.getRootPath(), ""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getBackImages())) {
            entity.setBackImages(entity.getBackImages().replaceAll(globalSetting.getRootPath(), ""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getBottomImages())) {
            entity.setBottomImages(entity.getBottomImages().replaceAll(globalSetting.getRootPath(), ""));
        }
        if (entity != null && entity.getPurchasingPrice() != null) {
            entity.setPurchasingPrice(entity.getPurchasingPrice() * 100);//??????????????????????????????
        }
        if (entity != null && entity.getOriginalCost() != null) {
            entity.setOriginalCost(entity.getOriginalCost() * 100);//??????????????????????????????
        }
        if (entity != null && entity.getSellingPrice() != null) {
            entity.setSellingPrice(entity.getSellingPrice() * 100);//??????????????????????????????
        }
        if (entity != null && entity.getVipPrice() != null) {
            entity.setVipPrice(entity.getVipPrice() * 100);//??????????????????????????????
        }
        int save = super.save(entity);
        //??????????????????
        if (entity != null && entity.getGoodsInfo() != null) {
            GoodsInfo goodsInfo = entity.getGoodsInfo();
            if (goodsInfo.DEL_FLAG_NORMAL.equals(goodsInfo.getIsDeleted())) {
                if (StringUtils.isBlank(goodsInfo.getId())) {
                    goodsInfo.setIdType("AUTO");
                    goodsInfo.setId(entity.getId());
                    String contentHtml = MarkdownUtils.markdownToHtmlExtensions(goodsInfo.getContent());
                    goodsInfo.setContentHtml(contentHtml);
                    goodsInfo.preInsert();
                    goodsInfoMapper.insert(goodsInfo);
                } else {
                    String contentHtml = MarkdownUtils.markdownToHtmlExtensions(goodsInfo.getContent());
                    goodsInfo.setContentHtml(contentHtml);
                    goodsInfo.preUpdate();
                    goodsInfoMapper.update(goodsInfo);
                }
            } else {
                goodsInfoMapper.deleteByLogic(goodsInfo);
            }
        }

        //??????????????????
        if (entity != null) {
            //???????????????????????????????????????
            GoodsClassify goodsClassify1 = new GoodsClassify();
            goodsClassify1.setGoodsId(entity.getId());
            goodsClassifyMapper.deletePhysicsByGoods(goodsClassify1);
            //???????????????????????????
            String goodsClassifyLevelOneIds = entity.getGoodsClassifyLevelOneIds();
            String goodsClassifyLevelTwoIds = entity.getGoodsClassifyLevelTwoIds();
            String goodsClassifyLevelThreeIds = entity.getGoodsClassifyLevelThreeIds();
            GoodsClassify goodsClassify = new GoodsClassify();
            if (goodsClassifyLevelOneIds != null && StringUtils.isNotBlank(goodsClassifyLevelOneIds)){
                String[] split = goodsClassifyLevelOneIds.split(",");
                for (String levelOne : split) {
                    if (levelOne != null){
                        goodsClassify.setIdType("AUTO");
                        goodsClassify.setClassifyId(levelOne);
                        goodsClassify.setLevel(1);
                        goodsClassify.preInsert();
                        goodsClassify.setGoodsId(entity.getId());
                        goodsClassifyMapper.insert(goodsClassify);
                    }
                }
            }
            if (goodsClassifyLevelTwoIds != null && StringUtils.isNotBlank(goodsClassifyLevelTwoIds)){
                String[] split = goodsClassifyLevelTwoIds.split(",");
                for (String leveTwo : split) {
                    if (leveTwo != null){
                        goodsClassify.setIdType("AUTO");
                        goodsClassify.setClassifyId(leveTwo);
                        goodsClassify.setLevel(2);
                        goodsClassify.preInsert();
                        goodsClassify.setGoodsId(entity.getId());
                        goodsClassifyMapper.insert(goodsClassify);
                    }
                }
            }
            if (goodsClassifyLevelThreeIds != null && StringUtils.isNotBlank(goodsClassifyLevelThreeIds)){
                String[] split = goodsClassifyLevelThreeIds.split(",");
                for (String levelThree : split) {
                    if (levelThree != null){
                        goodsClassify.setIdType("AUTO");
                        goodsClassify.setClassifyId(levelThree);
                        goodsClassify.setLevel(3);
                        goodsClassify.preInsert();
                        goodsClassify.setGoodsId(entity.getId());
                        goodsClassifyMapper.insert(goodsClassify);
                    }
                }
            }
        }
        //??????????????????
        if (entity != null && entity.getGoodsSpecification() != null) {
            GoodsSpecification goodsSpecification = entity.getGoodsSpecification();
            if (goodsSpecification.DEL_FLAG_NORMAL.equals(goodsSpecification.getIsDeleted())) {
                if (StringUtils.isBlank(goodsSpecification.getId())) {
                    goodsSpecification.setIdType("AUTO");
                    goodsSpecification.setId(entity.getId());
                    goodsSpecification.preInsert();
                    goodsSpecificationMapper.insert(goodsSpecification);
                } else {
                    goodsSpecification.preUpdate();
                    goodsSpecificationMapper.update(goodsSpecification);
                }
            } else {
                goodsSpecificationMapper.deleteByLogic(goodsSpecification);
            }
        }
        //??????????????????
        if (entity != null && entity.getGoodsNorm() != null) {
            GoodsNorm goodsNorm = entity.getGoodsNorm();
            if (goodsNorm.DEL_FLAG_NORMAL.equals(goodsNorm.getIsDeleted())) {
                if (StringUtils.isBlank(goodsNorm.getId())) {
                    goodsNorm.setIdType("AUTO");
                    goodsNorm.setId(entity.getId());
                    goodsNorm.setFactoryId(entity.getFactoryId());
                    goodsNorm.setGoodsName(entity.getName());
                    //??????????????????????????????
                    goodsNorm.setSampleBox(goodsNorm.getSampleBox().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setOuterPackingBox(goodsNorm.getOuterPackingBox().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setInstructionBook(goodsNorm.getInstructionBook().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setOtherData(goodsNorm.getOtherData().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setRelatedPictures(goodsNorm.getRelatedPictures().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setInstructions(goodsNorm.getInstructions().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setQualityStandard(goodsNorm.getQualityStandard().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setSurveyReport(goodsNorm.getSurveyReport().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setProductionBusinessLicense(goodsNorm.getProductionBusinessLicense().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setProductionCertificate(goodsNorm.getProductionCertificate().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.preInsert();
                    goodsNormMapper.insert(goodsNorm);
                } else {
                    goodsNorm.setFactoryId(entity.getFactoryId());
                    goodsNorm.setGoodsName(entity.getName());
                    //??????????????????????????????
                    goodsNorm.setSampleBox(goodsNorm.getSampleBox().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setOuterPackingBox(goodsNorm.getOuterPackingBox().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setInstructionBook(goodsNorm.getInstructionBook().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setOtherData(goodsNorm.getOtherData().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setRelatedPictures(goodsNorm.getRelatedPictures().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setInstructions(goodsNorm.getInstructions().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setQualityStandard(goodsNorm.getQualityStandard().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setSurveyReport(goodsNorm.getSurveyReport().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setProductionBusinessLicense(goodsNorm.getProductionBusinessLicense().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.setProductionCertificate(goodsNorm.getProductionCertificate().replaceAll(globalSetting.getRootPath(), ""));
                    goodsNorm.preUpdate();
                    goodsNormMapper.update(goodsNorm);
                }
            } else {
                goodsNormMapper.deleteByLogic(goodsNorm);
            }
        }
        return save;
    }

    @Override
    @Transactional
    public int deleteByLogic(Goods entity) {
        int row = super.deleteByLogic(entity);
        if (entity != null && StringUtils.isNotBlank(entity.getId())) {
            //??????????????????
            GoodsClassify goodsClassify = new GoodsClassify();
            goodsClassify.setGoodsId(entity.getId());
            goodsClassifyMapper.deletePhysicsByGoods(goodsClassify);
            //??????????????????
            GoodsSpecification specification = new GoodsSpecification();
            specification.setId(entity.getId());
            goodsSpecificationMapper.deleteByLogic(specification);
            //??????????????????
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setId(entity.getId());
            goodsInfoMapper.deleteByLogic(goodsInfo);
            //??????????????????
            GoodsNorm goodsNorm = new GoodsNorm();
            goodsNorm.setId(entity.getId());
            goodsNormMapper.deleteByLogic(goodsNorm);
        }
        return row;
    }

    @Override
    @Transactional
    public int deleteByPhysics(Goods entity) {
        int row = super.deleteByPhysics(entity);
        if (entity != null && StringUtils.isNotBlank(entity.getId())) {
            //??????????????????
            GoodsClassify goodsClassify = new GoodsClassify();
            goodsClassify.setGoodsId(entity.getId());
            goodsClassifyMapper.deletePhysicsByGoods(goodsClassify);
            //??????????????????
            GoodsSpecification specification = new GoodsSpecification();
            specification.setId(entity.getId());
            goodsSpecificationMapper.deleteByPhysics(specification);
            //??????????????????
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setId(entity.getId());
            goodsInfoMapper.deleteByPhysics(goodsInfo);
        }
        return row;
    }

    public int onTheShelf(Goods goods) {
        int row = goodsMapper.onTheShelf(goods);
        return row;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param goods
     * @return
     */
    public List<Goods> findByFactoryAndName(Goods goods) {
        List<Goods> result = goodsMapper.getByFactoryAndName(goods);
        return result;
    }

    /**
     * ????????????????????????
     *
     * @param goods
     * @return
     */
    public int updateGoodsAct(Goods goods) {
        int row = goodsMapper.updateGoodsAct(goods);
        return row;
    }

    public PageInfo<Goods> findTodoPage(Page<Goods> page, Goods entity) {
        if (entity != null) {
            String name = StringUtils.deleteWhitespace(entity.getName());
            entity.setName(name);
        }
        PageInfo<Goods> pageInfo = null;
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            entity.setPage(page);
            PageHelper.startPage(entity.getPageNo(), entity.getPageSize());
            List<Goods> list = goodsMapper.findTodoList(entity);
            for (Goods goods : list) {
                goods.setPurchasingPrice(goods.getPurchasingPrice() / 100);
                goods.setOriginalCost(goods.getOriginalCost() / 100);
                goods.setSellingPrice(goods.getSellingPrice() / 100);
                goods.setVipPrice(goods.getVipPrice() / 100);
            }
            pageInfo = new PageInfo<Goods>(list);
        }
        return pageInfo;
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????
     *
     * @return
     */
    public PageInfo<Goods> findCurrentDataPage(Page<Goods> page, Goods entity) {
        if (entity != null) {
            String name = StringUtils.deleteWhitespace(entity.getName());
            entity.setName(name);
        }
        PageInfo<Goods> pageInfo = null;
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            entity.setPage(page);
            PageHelper.startPage(entity.getPageNo(), entity.getPageSize());
            List<Goods> list = goodsMapper.findCurrentDataList(entity);
            for (Goods goods : list) {
                goods.setPurchasingPrice(goods.getPurchasingPrice() / 100);
                goods.setOriginalCost(goods.getOriginalCost() / 100);
                goods.setSellingPrice(goods.getSellingPrice() / 100);
                goods.setVipPrice(goods.getVipPrice() / 100);
            }
            pageInfo = new PageInfo<Goods>(list);
        }
        return pageInfo;
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????
     * ????????????????????????????????????????????????
     *
     * @return
     */
    public PageInfo<Goods> findFinishedDataPage(Page<Goods> page, Goods entity) {
        if (entity != null) {
            String name = StringUtils.deleteWhitespace(entity.getName());
            entity.setName(name);
        }
        PageInfo<Goods> pageInfo = null;
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            entity.setPage(page);
            PageHelper.startPage(entity.getPageNo(), entity.getPageSize());
            List<Goods> list = goodsMapper.findFinishedDataPage(entity);
            for (Goods goods : list) {
                goods.setPurchasingPrice(goods.getPurchasingPrice() / 100);
                goods.setOriginalCost(goods.getOriginalCost() / 100);
                goods.setSellingPrice(goods.getSellingPrice() / 100);
                goods.setVipPrice(goods.getVipPrice() / 100);
            }
            pageInfo = new PageInfo<Goods>(list);
        }
        return pageInfo;
    }
}
