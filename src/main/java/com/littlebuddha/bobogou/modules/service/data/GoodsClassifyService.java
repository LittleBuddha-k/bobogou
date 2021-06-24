package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.GoodsClassify;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsClassifyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品分类service层
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GoodsClassifyService extends CrudService<GoodsClassify, GoodsClassifyMapper> {

    @Override
    public GoodsClassify get(GoodsClassify entity) {
        return super.get(entity);
    }

    @Override
    public List<GoodsClassify> findList(GoodsClassify entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<GoodsClassify> findPage(Page<GoodsClassify> page, GoodsClassify entity) {
        return super.findPage(page, entity);
    }

    /**
     * 只查询顶级商品分类数据
     *
     * @return
     */
    public List<GoodsClassify> findLevelOneData() {

        return null;
    }

    @Override
    public int save(GoodsClassify entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(GoodsClassify entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(GoodsClassify entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<GoodsClassify> findRecoveryPage(Page<GoodsClassify> page, GoodsClassify entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(GoodsClassify entity) {
        return super.recovery(entity);
    }
}
