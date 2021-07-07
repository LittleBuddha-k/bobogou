package com.littlebuddha.bobogou.modules.service.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.Vip;
import com.littlebuddha.bobogou.modules.mapper.other.VipMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VipService extends CrudService<Vip, VipMapper> {

    @Override
    public Vip get(Vip entity) {
        return super.get(entity);
    }

    @Override
    public List<Vip> findList(Vip entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Vip> findPage(Page<Vip> page, Vip entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Vip entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }
}
