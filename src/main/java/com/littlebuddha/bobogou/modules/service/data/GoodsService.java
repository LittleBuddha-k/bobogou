package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
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

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GoodsService extends CrudService<Goods, GoodsMapper> {

    @Autowired
    private GlobalSetting globalSetting;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private GoodsClassifyMapper goodsClassifyMapper;

    @Autowired
    private GoodsSpecificationMapper goodsSpecificationMapper;

    @Autowired
    private GoodsNormMapper goodsNormMapper;

    @Override
    public Goods get(Goods entity) {
        Goods goods = super.get(entity);
        if (goods != null && StringUtils.isNotBlank(goods.getCertificateImageWatermark())){
            String certificateImageWatermark = goods.getCertificateImageWatermark();
            String aa = "";
            String[] split = certificateImageWatermark.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            goods.setCertificateImageWatermark(aa);
        }
        if (goods != null && StringUtils.isNotBlank(goods.getCertificateImage())){
            String certificateImage = goods.getCertificateImage();
            String aa = "";
            String[] split = certificateImage.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            goods.setCertificateImage(aa);
        }
        if (goods != null && StringUtils.isNotBlank(goods.getImages())){
            String images = goods.getImages();
            String aa = "";
            String[] split = images.split(",");
            for (String image : split) {
                aa = aa + globalSetting.getRootPath() + image + ",";
            }
            goods.setImages(aa);
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
        return super.findPage(page, entity);
    }

    @Override
    @Transactional
    public int save(Goods entity) {
        entity.setIdType("AUTO");
        //设置图片插入路径格式
        if (entity != null && StringUtils.isNotBlank(entity.getCertificateImageWatermark())){
            entity.setCertificateImageWatermark(entity.getCertificateImageWatermark().replaceAll(globalSetting.getRootPath(),""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getCertificateImage())){
            entity.setCertificateImage(entity.getCertificateImage().replaceAll(globalSetting.getRootPath(),""));
        }
        if (entity != null && StringUtils.isNotBlank(entity.getImages())){
            entity.setImages(entity.getImages().replaceAll(globalSetting.getRootPath(),""));
        }
        int save = super.save(entity);
        //插入商品详情
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
                }else {
                    String contentHtml = MarkdownUtils.markdownToHtmlExtensions(goodsInfo.getContent());
                    goodsInfo.setContentHtml(contentHtml);
                    goodsInfo.preUpdate();
                    goodsInfoMapper.update(goodsInfo);
                }
            }else {
                goodsInfoMapper.deleteByLogic(goodsInfo);
            }
        }

        //插入商品分类
        if (entity != null && entity.getGoodsClassify() != null){
            //所选商品列表
            GoodsClassify goodsClassify = entity.getGoodsClassify();
            if (goodsClassify.DEL_FLAG_NORMAL.equals(goodsClassify.getIsDeleted())) {
                    if (StringUtils.isBlank(goodsClassify.getId())) {
                        goodsClassify.setGoodsId(entity.getId());
                        goodsClassify.preInsert();
                        goodsClassifyMapper.insert(goodsClassify);
                    }else {
                        goodsClassify.preUpdate();
                        goodsClassifyMapper.update(goodsClassify);
                    }
                }else {
                goodsClassifyMapper.deleteByLogic(goodsClassify);
                }
            }

        //插入商品规格
        if (entity != null && entity.getGoodsSpecification() != null) {
            GoodsSpecification goodsSpecification = entity.getGoodsSpecification();
            if (goodsSpecification.DEL_FLAG_NORMAL.equals(goodsSpecification.getIsDeleted())) {
                if (StringUtils.isBlank(goodsSpecification.getId())) {
                    goodsSpecification.setIdType("AUTO");
                    goodsSpecification.setId(entity.getId());
                    goodsSpecification.preInsert();
                    goodsSpecificationMapper.insert(goodsSpecification);
                }else {
                    goodsSpecification.preUpdate();
                    goodsSpecificationMapper.update(goodsSpecification);
                }
            }else {
                goodsSpecificationMapper.deleteByLogic(goodsSpecification);
            }
        }

        //插入商品规范
        if (entity != null && entity.getGoodsNorm() != null) {
            GoodsNorm goodsNorm = entity.getGoodsNorm();
            if (goodsNorm.DEL_FLAG_NORMAL.equals(goodsNorm.getIsDeleted())) {
                if (StringUtils.isBlank(goodsNorm.getId())) {
                    goodsNorm.setIdType("AUTO");
                    goodsNorm.setId(entity.getId());
                    goodsNorm.setFactoryId(entity.getFactoryId());
                    goodsNorm.setGoodsName(entity.getName());
                    //设置图片路径保存格式
                    goodsNorm.setSampleBox(goodsNorm.getSampleBox().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setOuterPackingBox(goodsNorm.getOuterPackingBox().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setInstructionBook(goodsNorm.getInstructionBook().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setOtherData(goodsNorm.getOtherData().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setRelatedPictures(goodsNorm.getRelatedPictures().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setInstructions(goodsNorm.getInstructions().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setQualityStandard(goodsNorm.getQualityStandard().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setSurveyReport(goodsNorm.getSurveyReport().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setProductionBusinessLicense(goodsNorm.getProductionBusinessLicense().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setProductionCertificate(goodsNorm.getProductionCertificate().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.preInsert();
                    goodsNormMapper.insert(goodsNorm);
                }else {
                    goodsNorm.setFactoryId(entity.getFactoryId());
                    goodsNorm.setGoodsName(entity.getName());
                    //设置图片路径保存格式
                    goodsNorm.setSampleBox(goodsNorm.getSampleBox().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setOuterPackingBox(goodsNorm.getOuterPackingBox().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setInstructionBook(goodsNorm.getInstructionBook().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setOtherData(goodsNorm.getOtherData().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setRelatedPictures(goodsNorm.getRelatedPictures().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setInstructions(goodsNorm.getInstructions().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setQualityStandard(goodsNorm.getQualityStandard().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setSurveyReport(goodsNorm.getSurveyReport().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setProductionBusinessLicense(goodsNorm.getProductionBusinessLicense().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.setProductionCertificate(goodsNorm.getProductionCertificate().replaceAll(globalSetting.getRootPath(),""));
                    goodsNorm.preUpdate();
                    goodsNormMapper.update(goodsNorm);
                }
            }else {
                goodsNormMapper.deleteByLogic(goodsNorm);
            }
        }
        return save;
    }

    @Override
    @Transactional
    public int deleteByLogic(Goods entity) {
        int row = super.deleteByLogic(entity);
        if (entity != null && StringUtils.isNotBlank(entity.getId())){
            //删除商品分类
            GoodsClassify goodsClassify = new GoodsClassify();
            goodsClassify.setGoodsId(entity.getId());
            goodsClassifyMapper.deleteLogicByGoods(goodsClassify);
            //删除商品规格
            GoodsSpecification specification = new GoodsSpecification();
            specification.setId(entity.getId());
            goodsSpecificationMapper.deleteByLogic(specification);
            //删除商品详情
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setId(entity.getId());
            goodsInfoMapper.deleteByLogic(goodsInfo);
            //删除商品规范
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
        if (entity != null && StringUtils.isNotBlank(entity.getId())){
            //删除商品分类
            GoodsClassify goodsClassify = new GoodsClassify();
            goodsClassify.setGoodsId(entity.getId());
            goodsClassifyMapper.deletePhysicsByGoods(goodsClassify);
            //删除商品规格
            GoodsSpecification specification = new GoodsSpecification();
            specification.setId(entity.getId());
            goodsSpecificationMapper.deleteByPhysics(specification);
            //删除商品详情
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
     * 根据厂商、商品名查询商品数据
     * @param goods
     * @return
     */
    public List<Goods> findByFactoryAndName(Goods goods) {
        List<Goods> result = goodsMapper.getByFactoryAndName(goods);
        return result;
    }
}
