package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Classify;
import com.littlebuddha.bobogou.modules.mapper.data.ClassifyMapper;
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
public class ClassifyService extends CrudService<Classify, ClassifyMapper> {

    @Override
    public Classify get(Classify entity) {
        return super.get(entity);
    }

    @Override
    public List<Classify> findList(Classify entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Classify> findPage(Page<Classify> page, Classify entity) {
        if(entity != null){
            String name = StringUtils.deleteWhitespace(entity.getName());
            entity.setName(name);
        }
        return super.findPage(page, entity);
    }

    /**
     * 只查询顶级商品分类数据
     * @return
     */
    public List<Classify> findLevelOneData(){

        return null;
    }

    @Override
    public int save(Classify entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Classify entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Classify entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Classify> findRecoveryPage(Page<Classify> page, Classify entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Classify entity) {
        return super.recovery(entity);
    }
}
