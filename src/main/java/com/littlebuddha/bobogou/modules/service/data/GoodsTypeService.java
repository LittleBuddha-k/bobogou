package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.GoodsType;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsTypeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GoodsTypeService extends CrudService<GoodsType, GoodsTypeMapper> {

    @Autowired
    private GlobalSetting globalSetting;

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    public GoodsType get(GoodsType entity) {
        GoodsType goodsType = super.get(entity);
        return goodsType;
    }

    @Override
    public List<GoodsType> findList(GoodsType entity) {
        List<GoodsType> list = super.findList(entity);
        return list;
    }

    @Override
    public PageInfo<GoodsType> findPage(Page<GoodsType> page, GoodsType entity) {
        PageInfo<GoodsType> page1 = super.findPage(page, entity);
        return page1;
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
