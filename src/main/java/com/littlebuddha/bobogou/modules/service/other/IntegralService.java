package com.littlebuddha.bobogou.modules.service.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.Integral;
import com.littlebuddha.bobogou.modules.mapper.other.IntegralMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IntegralService extends CrudService<Integral, IntegralMapper> {

    @Override
    public Integral get(Integral entity) {
        return super.get(entity);
    }

    @Override
    public List<Integral> findList(Integral entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Integral> findPage(Page<Integral> page, Integral entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Integral entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Integral entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Integral entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Integral> findRecoveryPage(Page<Integral> page, Integral entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Integral entity) {
        return super.recovery(entity);
    }
}
