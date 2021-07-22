package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.utils.DosageForm;
import com.littlebuddha.bobogou.modules.entity.data.utils.ShelfLife;
import com.littlebuddha.bobogou.modules.mapper.data.DosageFormMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DosageFormService extends CrudService<DosageForm, DosageFormMapper> {

    @Autowired
    private DosageFormMapper dosageFormMapper;

    @Override
    public DosageForm get(DosageForm entity) {
        return super.get(entity);
    }

    @Override
    public List<DosageForm> findList(DosageForm entity) {
        return super.findList(entity);
    }

    public List<ShelfLife> findShelfLifeList() {
        return dosageFormMapper.findShelfLifeList();
    }

    @Override
    public PageInfo<DosageForm> findPage(Page<DosageForm> page, DosageForm entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(DosageForm entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(DosageForm entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(DosageForm entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<DosageForm> findRecoveryPage(Page<DosageForm> page, DosageForm entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(DosageForm entity) {
        return super.recovery(entity);
    }
}
