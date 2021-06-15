package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.RegionGoods;
import com.littlebuddha.bobogou.modules.entity.data.RegionGoods;
import com.littlebuddha.bobogou.modules.mapper.data.RegionGoodsMapper;
import com.littlebuddha.bobogou.modules.mapper.data.RegionGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RegionGoodsService extends CrudService<RegionGoods, RegionGoodsMapper> {

    @Autowired
    private RegionGoodsMapper regionGoodsMapper;

    @Override
    public RegionGoods get(RegionGoods entity) {
        return super.get(entity);
    }

    @Override
    public List<RegionGoods> findList(RegionGoods entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<RegionGoods> findPage(Page<RegionGoods> page, RegionGoods entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(RegionGoods entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(RegionGoods entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(RegionGoods entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<RegionGoods> findRecoveryPage(Page<RegionGoods> page, RegionGoods entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(RegionGoods entity) {
        return super.recovery(entity);
    }
}
