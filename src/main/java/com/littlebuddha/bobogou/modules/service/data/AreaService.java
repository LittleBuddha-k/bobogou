package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.mapper.data.AreaMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AreaService extends CrudService<Area, AreaMapper> {

    @Override
    public Area get(Area entity) {
        return super.get(entity);
    }

    @Override
    public List<Area> findList(Area entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Area> findPage(Page<Area> page, Area entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Area entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Area entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Area entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Area> findRecoveryPage(Page<Area> page, Area entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Area entity) {
        return super.recovery(entity);
    }
}
