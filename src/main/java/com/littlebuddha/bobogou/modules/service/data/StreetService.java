package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Street;
import com.littlebuddha.bobogou.modules.mapper.data.StreetMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StreetService extends CrudService<Street, StreetMapper> {

    @Override
    public Street get(Street entity) {
        return super.get(entity);
    }

    @Override
    public List<Street> findList(Street entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Street> findPage(Page<Street> page, Street entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Street entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Street entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Street entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Street> findRecoveryPage(Page<Street> page, Street entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Street entity) {
        return super.recovery(entity);
    }
}
