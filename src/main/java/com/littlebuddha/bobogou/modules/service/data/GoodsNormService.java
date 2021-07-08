package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.GoodsNorm;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsNormMapper;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public GoodsNorm get(GoodsNorm entity) {
        return super.get(entity);
    }

    @Override
    public List<GoodsNorm> findList(GoodsNorm entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<GoodsNorm> findPage(Page<GoodsNorm> page, GoodsNorm entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(GoodsNorm entity) {
        entity.setIdType("AUTO");
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
