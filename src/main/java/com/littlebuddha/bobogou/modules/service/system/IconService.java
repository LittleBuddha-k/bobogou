package com.littlebuddha.bobogou.modules.service.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.entity.system.utils.Icon;
import com.littlebuddha.bobogou.modules.mapper.system.IconMapper;
import com.littlebuddha.bobogou.modules.mapper.system.RoleMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IconService extends CrudService<Icon, IconMapper> {

    @Override
    public Icon get(Icon entity) {
        return super.get(entity);
    }

    @Override
    public List<Icon> findList(Icon entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Icon> findPage(Page<Icon> page, Icon entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Icon entity) {
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Icon entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Icon entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Icon> findRecoveryPage(Page<Icon> page, Icon entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Icon entity) {
        return super.recovery(entity);
    }
}
