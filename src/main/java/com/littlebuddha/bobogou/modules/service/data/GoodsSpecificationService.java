package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.GoodsSpecification;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsSpecificationMapper;
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
public class GoodsSpecificationService extends CrudService<GoodsSpecification, GoodsSpecificationMapper> {

    @Override
    public GoodsSpecification get(GoodsSpecification entity) {
        return super.get(entity);
    }

    @Override
    public List<GoodsSpecification> findList(GoodsSpecification entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<GoodsSpecification> findPage(Page<GoodsSpecification> page, GoodsSpecification entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(GoodsSpecification entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(GoodsSpecification entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(GoodsSpecification entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<GoodsSpecification> findRecoveryPage(Page<GoodsSpecification> page, GoodsSpecification entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(GoodsSpecification entity) {
        return super.recovery(entity);
    }
}
