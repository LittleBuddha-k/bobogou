package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.GoodsTag;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsTagMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品分类service层
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GoodsTagService extends CrudService<GoodsTag, GoodsTagMapper> {

    @Override
    public GoodsTag get(GoodsTag entity) {
        return super.get(entity);
    }

    @Override
    public List<GoodsTag> findList(GoodsTag entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<GoodsTag> findPage(Page<GoodsTag> page, GoodsTag entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(GoodsTag entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(GoodsTag entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(GoodsTag entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<GoodsTag> findRecoveryPage(Page<GoodsTag> page, GoodsTag entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(GoodsTag entity) {
        return super.recovery(entity);
    }
}
