package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.GoodsInfo;
import com.littlebuddha.bobogou.modules.entity.data.GoodsInfo;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsInfoMapper;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GoodsInfoService extends CrudService<GoodsInfo, GoodsInfoMapper> {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public GoodsInfo get(GoodsInfo entity) {
        return super.get(entity);
    }

    @Override
    public List<GoodsInfo> findList(GoodsInfo entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<GoodsInfo> findPage(Page<GoodsInfo> page, GoodsInfo entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(GoodsInfo entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(GoodsInfo entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(GoodsInfo entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<GoodsInfo> findRecoveryPage(Page<GoodsInfo> page, GoodsInfo entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(GoodsInfo entity) {
        return super.recovery(entity);
    }

    public GoodsInfo getByGoods(GoodsInfo goodsId) {
        GoodsInfo entity = goodsInfoMapper.getByGoods(goodsId);
        return entity;
    }
}
