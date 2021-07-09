package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Banner;
import com.littlebuddha.bobogou.modules.mapper.data.BannerMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BannerService extends CrudService<Banner, BannerMapper> {

    @Autowired
    private GlobalSetting globalSetting;

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public Banner get(Banner entity) {
        Banner banner = super.get(entity);
        if (banner != null){
            banner.setUrl(globalSetting.getRootPath() + banner.getUrl());
        }
        return banner;
    }

    @Override
    public List<Banner> findList(Banner entity) {
        List<Banner> list = super.findList(entity);
        for (Banner banner : list) {
            if (banner != null && StringUtils.isNotBlank(banner.getUrl())){
                banner.setUrl(globalSetting.getRootPath() + banner.getUrl());
            }
        }
        return list;
    }

    @Override
    public PageInfo<Banner> findPage(Page<Banner> page, Banner entity) {
        PageInfo<Banner> page1 = super.findPage(page, entity);
        List<Banner> list = page1.getList();
        for (Banner banner : list) {
            if (banner != null && StringUtils.isNotBlank(banner.getUrl())){
                banner.setUrl(globalSetting.getRootPath() + banner.getUrl());
            }
        }
        return page1;
    }

    @Override
    public int save(Banner entity) {
        entity.setIdType("AUTO");
        if (entity != null && StringUtils.isNotBlank(entity.getUrl())){
            entity.setUrl(entity.getUrl().replaceAll(globalSetting.getRootPath(),""));
        }
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Banner entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Banner entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Banner> findRecoveryPage(Page<Banner> page, Banner entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Banner entity) {
        return super.recovery(entity);
    }
}
