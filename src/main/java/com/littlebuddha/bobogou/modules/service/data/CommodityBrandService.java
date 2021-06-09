package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.CommodityBrand;
import com.littlebuddha.bobogou.modules.mapper.data.CommodityBrandMapper;
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
public class CommodityBrandService extends CrudService<CommodityBrand, CommodityBrandMapper> {

    @Autowired
    private CommodityBrandMapper commodityBrandMapper;

    @Override
    public CommodityBrand get(CommodityBrand entity) {
        return super.get(entity);
    }

    @Override
    public List<CommodityBrand> findList(CommodityBrand entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<CommodityBrand> findPage(Page<CommodityBrand> page, CommodityBrand entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(CommodityBrand entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(CommodityBrand entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(CommodityBrand entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<CommodityBrand> findRecoveryPage(Page<CommodityBrand> page, CommodityBrand entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(CommodityBrand entity) {
        return super.recovery(entity);
    }
}
