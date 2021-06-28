package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
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
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private GoodsClassifyMapper goodsClassifyMapper;

    @Autowired
    private GoodsSpecificationMapper goodsSpecificationMapper;

    @Override
    public Goods get(Goods entity) {
        return super.get(entity);
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
        int save = super.save(entity);
        //插入商品详情
        if (entity != null && entity.getGoodsInfo() != null) {
            GoodsInfo goodsInfo = entity.getGoodsInfo();
            if (goodsInfo.DEL_FLAG_NORMAL.equals(goodsInfo.getIsDeleted())) {
                if (StringUtils.isBlank(goodsInfo.getId())) {
                    goodsInfo.setIdType("AUTO");
                    goodsInfo.setId(entity.getId());
                    goodsInfo.preInsert();
                    goodsInfoMapper.insert(goodsInfo);
                }else {
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
}
