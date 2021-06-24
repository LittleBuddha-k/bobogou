package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.GoodsExchange;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsExchangeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GoodsExchangeService extends CrudService<GoodsExchange, GoodsExchangeMapper> {

    @Override
    public GoodsExchange get(GoodsExchange entity) {
        return super.get(entity);
    }

    @Override
    public List<GoodsExchange> findList(GoodsExchange entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<GoodsExchange> findPage(Page<GoodsExchange> page, GoodsExchange entity) {
        if(entity != null){
            String name = StringUtils.deleteWhitespace(entity.getName());
            entity.setName(name);
        }
        return super.findPage(page, entity);
    }

    @Override
    public int save(GoodsExchange entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(GoodsExchange entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(GoodsExchange entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<GoodsExchange> findRecoveryPage(Page<GoodsExchange> page, GoodsExchange entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(GoodsExchange entity) {
        return super.recovery(entity);
    }
}
