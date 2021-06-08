package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.CommodityType;
import com.littlebuddha.bobogou.modules.mapper.data.CommodityTypeMapper;
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
public class CommodityTypeService extends CrudService<CommodityType, CommodityTypeMapper> {

    @Autowired
    private CommodityTypeMapper commodityTypeMapper;

    @Override
    public CommodityType get(CommodityType entity) {
        return super.get(entity);
    }

    @Override
    public List<CommodityType> findList(CommodityType entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<CommodityType> findPage(Page<CommodityType> page, CommodityType entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(CommodityType entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(CommodityType entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(CommodityType entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<CommodityType> findRecoveryPage(Page<CommodityType> page, CommodityType entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(CommodityType entity) {
        return super.recovery(entity);
    }
}
