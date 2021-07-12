package com.littlebuddha.bobogou.modules.service.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRegion;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRegionMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OperatorRegionService extends CrudService<OperatorRegion, OperatorRegionMapper> {

    @Override
    public OperatorRegion get(OperatorRegion entity) {
        return super.get(entity);
    }

    @Override
    public List<OperatorRegion> findList(OperatorRegion entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<OperatorRegion> findPage(Page<OperatorRegion> page, OperatorRegion entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(OperatorRegion entity) {
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(OperatorRegion entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(OperatorRegion entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<OperatorRegion> findRecoveryPage(Page<OperatorRegion> page, OperatorRegion entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(OperatorRegion entity) {
        return super.recovery(entity);
    }
}
