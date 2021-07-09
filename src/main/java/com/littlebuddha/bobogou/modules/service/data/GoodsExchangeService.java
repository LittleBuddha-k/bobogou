package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.GoodsExchange;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsExchangeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GoodsExchangeService extends CrudService<GoodsExchange, GoodsExchangeMapper> {

    @Autowired
    private GlobalSetting globalSetting;

    @Override
    public GoodsExchange get(GoodsExchange entity) {
        GoodsExchange goodsExchange = super.get(entity);
        if(goodsExchange != null && goodsExchange.getImageUrl() != null && StringUtils.isNotBlank(goodsExchange.getImageUrl())){
            String images = "";
            String[] split = goodsExchange.getImageUrl().split(",");
            for (String image : split) {
                images = images + (globalSetting.getRootPath() + image) + ",";
            }
            goodsExchange.setImageUrl(images);
        }
        return goodsExchange;
    }

    @Override
    public List<GoodsExchange> findList(GoodsExchange entity) {
        List<GoodsExchange> list = super.findList(entity);
        for (GoodsExchange goodsExchange : list) {
            if(goodsExchange != null && goodsExchange.getImageUrl() != null && StringUtils.isNotBlank(goodsExchange.getImageUrl())){
                String images = "";
                String[] split = goodsExchange.getImageUrl().split(",");
                for (String image : split) {
                    images = images + (globalSetting.getRootPath() + image) + ",";
                }
                goodsExchange.setImageUrl(images);
            }
        }
        return list;
    }

    @Override
    public PageInfo<GoodsExchange> findPage(Page<GoodsExchange> page, GoodsExchange entity) {
        if(entity != null){
            String name = StringUtils.deleteWhitespace(entity.getName());
            entity.setName(name);
        }
        PageInfo<GoodsExchange> page1 = super.findPage(page, entity);
        List<GoodsExchange> list = page1.getList();
        for (GoodsExchange goodsExchange : list) {
            if(goodsExchange != null && goodsExchange.getImageUrl() != null && StringUtils.isNotBlank(goodsExchange.getImageUrl())){
                String images = "";
                String[] split = goodsExchange.getImageUrl().split(",");
                for (String image : split) {
                    images = images + (globalSetting.getRootPath() + image) + ",";
                }
                goodsExchange.setImageUrl(images);
            }
        }
        return page1;
    }

    @Override
    public int save(GoodsExchange entity) {
        entity.setIdType("AUTO");
        if(entity != null && entity.getImageUrl() != null && StringUtils.isNotBlank(entity.getImageUrl())){
            entity.setImageUrl(entity.getImageUrl().replaceAll(globalSetting.getRootPath(),""));
        }
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(GoodsExchange entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(GoodsExchange entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<GoodsExchange> findRecoveryPage(Page<GoodsExchange> page, GoodsExchange entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(GoodsExchange entity) {
        return super.recovery(entity);
    }
}
