package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Banner;
import com.littlebuddha.bobogou.modules.mapper.data.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BannerService extends CrudService<Banner, BannerMapper> {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public Banner get(Banner entity) {
        return super.get(entity);
    }

    @Override
    public List<Banner> findList(Banner entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Banner> findPage(Page<Banner> page, Banner entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Banner entity) {
        entity.setIdType("AUTO");
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
