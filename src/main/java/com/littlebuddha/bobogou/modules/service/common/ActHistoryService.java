package com.littlebuddha.bobogou.modules.service.common;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.common.ActHistory;
import com.littlebuddha.bobogou.modules.mapper.common.ActHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ActHistoryService extends CrudService<ActHistory, ActHistoryMapper> {

    @Autowired
    private ActHistoryMapper actHistoryMapper;

    @Autowired
    private GlobalSetting globalSetting;

    @Override
    public ActHistory get(ActHistory entity) {
        ActHistory actHistory = actHistoryMapper.get(entity);
        return actHistory;
    }

    @Override
    public List<ActHistory> findList(ActHistory entity) {
        List<ActHistory> list = super.findList(entity);
        return list;
    }

    @Override
    public PageInfo<ActHistory> findPage(Page<ActHistory> page, ActHistory entity) {
        PageInfo<ActHistory> page1 = super.findPage(page, entity);
        return page1;
    }

    @Override
    public int save(ActHistory entity) {
        entity.setIdType("AUTO");
        int save = super.save(entity);
        return save;
    }

    @Override
    public int deleteByLogic(ActHistory entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(ActHistory entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<ActHistory> findRecoveryPage(Page<ActHistory> page, ActHistory entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(ActHistory entity) {
        return super.recovery(entity);
    }
}
