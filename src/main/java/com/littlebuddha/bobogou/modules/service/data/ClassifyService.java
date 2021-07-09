package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Classify;
import com.littlebuddha.bobogou.modules.mapper.data.ClassifyMapper;
import org.apache.commons.lang3.StringUtils;
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
public class ClassifyService extends CrudService<Classify, ClassifyMapper> {

    @Autowired
    private GlobalSetting globalSetting;

    @Override
    public Classify get(Classify entity) {
        Classify classify = super.get(entity);
        if (classify != null) {
            classify.setIcon(globalSetting.getRootPath() + classify.getIcon());
        }
        return classify;
    }

    @Override
    public List<Classify> findList(Classify entity) {
        List<Classify> list = super.findList(entity);
        for (Classify classify : list) {
            if (classify != null && StringUtils.isNotBlank(classify.getIcon())) {
                classify.setIcon(globalSetting.getRootPath() + classify.getIcon());
            }
        }
        return list;
    }

    @Override
    public PageInfo<Classify> findPage(Page<Classify> page, Classify entity) {
        if (entity != null) {
            String name = StringUtils.deleteWhitespace(entity.getName());
            entity.setName(name);
        }
        PageInfo<Classify> page1 = super.findPage(page, entity);
        List<Classify> list = page1.getList();
        for (Classify classify : list) {
            if (classify != null && StringUtils.isNotBlank(classify.getIcon())) {
                classify.setIcon(globalSetting.getRootPath() + classify.getIcon());
            }
        }
        return page1;
    }

    /**
     * 只查询顶级商品分类数据
     *
     * @return
     */
    public List<Classify> findLevelOneData() {

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
