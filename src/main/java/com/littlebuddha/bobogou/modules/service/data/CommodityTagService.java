package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.CommodityTag;
import com.littlebuddha.bobogou.modules.mapper.data.CommodityTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品分类service层
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CommodityTagService extends CrudService<CommodityTag, CommodityTagMapper> {

    @Autowired
    private CommodityTagMapper commodityTagMapper;

    @Override
    public CommodityTag get(CommodityTag entity) {
        return super.get(entity);
    }

    @Override
    public List<CommodityTag> findList(CommodityTag entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<CommodityTag> findPage(Page<CommodityTag> page, CommodityTag entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(CommodityTag entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(CommodityTag entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(CommodityTag entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<CommodityTag> findRecoveryPage(Page<CommodityTag> page, CommodityTag entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(CommodityTag entity) {
        return super.recovery(entity);
    }
}
