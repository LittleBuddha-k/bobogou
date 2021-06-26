package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.GoodsClassify;
import com.littlebuddha.bobogou.modules.entity.data.GoodsInfo;
import com.littlebuddha.bobogou.modules.entity.data.Goods;
import com.littlebuddha.bobogou.modules.entity.data.GoodsType;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsClassifyMapper;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsInfoMapper;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsMapper;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsTypeMapper;
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
        return save;
    }

    @Override
    public int deleteByPhysics(Goods entity) {
        return super.deleteByPhysics(entity);
    }

    public int onTheShelf(Goods goods) {
        int row = goodsMapper.onTheShelf(goods);
        return row;
    }
}
