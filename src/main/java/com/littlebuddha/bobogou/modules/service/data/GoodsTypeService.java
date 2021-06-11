package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.GoodsType;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsTypeMapper;
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
public class GoodsTypeService extends CrudService<GoodsType, GoodsTypeMapper> {

    @Override
    public GoodsType get(GoodsType entity) {
        return super.get(entity);
    }

    @Override
    public List<GoodsType> findList(GoodsType entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<GoodsType> findPage(Page<GoodsType> page, GoodsType entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(GoodsType entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(GoodsType entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(GoodsType entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<GoodsType> findRecoveryPage(Page<GoodsType> page, GoodsType entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(GoodsType entity) {
        return super.recovery(entity);
    }
}
