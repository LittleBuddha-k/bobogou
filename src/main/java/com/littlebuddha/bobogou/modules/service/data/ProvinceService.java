package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Province;
import com.littlebuddha.bobogou.modules.mapper.data.ProvinceMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("province")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProvinceService extends CrudService<Province, ProvinceMapper> {

    @Override
    public Province get(Province entity) {
        return super.get(entity);
    }

    @Override
    public List<Province> findList(Province entity) {
        return super.findList(entity);
    }

    /**
     * 查询选择下拉框省级数据--查询全部为词典数据使用
     * @param
     * @return
     */
    public List<Province> findToDictUse() {
        Province entity = new Province();
        return super.findList(entity);
    }

    @Override
    public PageInfo<Province> findPage(Page<Province> page, Province entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Province entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Province entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Province entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Province> findRecoveryPage(Page<Province> page, Province entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Province entity) {
        return super.recovery(entity);
    }
}
