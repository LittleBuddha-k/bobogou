package com.littlebuddha.bobogou.modules.service.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.Agreement;
import com.littlebuddha.bobogou.modules.mapper.other.AgreementMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AgreementService extends CrudService<Agreement, AgreementMapper> {

    @Override
    public Agreement get(Agreement entity) {
        return super.get(entity);
    }

    @Override
    public List<Agreement> findList(Agreement entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Agreement> findPage(Page<Agreement> page, Agreement entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Agreement entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Agreement entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Agreement entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Agreement> findRecoveryPage(Page<Agreement> page, Agreement entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Agreement entity) {
        return super.recovery(entity);
    }
}
