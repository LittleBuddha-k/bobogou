package com.littlebuddha.bobogou.modules.service.other;

import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.ActEntity;
import com.littlebuddha.bobogou.modules.mapper.other.ActMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ActService extends CrudService<ActEntity, ActMapper> {

    @Override
    public ActEntity get(ActEntity entity) {
        return super.get(entity);
    }

    @Override
    public List<ActEntity> findList(ActEntity entity) {
        return super.findList(entity);
    }

    @Override
    public int save(ActEntity entity) {
        return super.save(entity);
    }
}
