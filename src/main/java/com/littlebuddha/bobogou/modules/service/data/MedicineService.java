package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Medicine;
import com.littlebuddha.bobogou.modules.mapper.data.MedicineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MedicineService extends CrudService<Medicine, MedicineMapper> {

    @Autowired
    private MedicineMapper medicineMapper;

    @Override
    public Medicine get(Medicine entity) {
        return super.get(entity);
    }

    @Override
    public List<Medicine> findList(Medicine entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Medicine> findPage(Page<Medicine> page, Medicine entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Medicine entity) {
        return super.save(entity);
    }

    @Override
    public int deleteByPhysics(Medicine entity) {
        return super.deleteByPhysics(entity);
    }
}
