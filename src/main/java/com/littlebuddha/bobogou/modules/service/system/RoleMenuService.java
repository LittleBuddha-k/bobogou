package com.littlebuddha.bobogou.modules.service.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.system.RoleMenu;
import com.littlebuddha.bobogou.modules.mapper.system.RoleMenuMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoleMenuService extends CrudService<RoleMenu, RoleMenuMapper> {

    @Override
    public RoleMenu get(RoleMenu entity) {
        return super.get(entity);
    }

    @Override
    public List<RoleMenu> findList(RoleMenu entity) {
        return super.findList(entity);
    }

    @Override
    public List<RoleMenu> findAllList(RoleMenu entity) {
        return super.findAllList(entity);
    }

    @Override
    public PageInfo<RoleMenu> findPage(Page<RoleMenu> page, RoleMenu entity) {
        return super.findPage(page, entity);
    }

    @Override
    @Transactional
    public int save(RoleMenu entity) {
        return super.save(entity);
    }

    @Override
    @Transactional
    public int deleteByPhysics(RoleMenu entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    @Transactional
    public int deleteByLogic(RoleMenu entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    @Transactional
    public int recovery(RoleMenu entity) {
        return super.recovery(entity);
    }
}
