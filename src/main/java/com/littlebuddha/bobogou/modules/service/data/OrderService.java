package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import com.littlebuddha.bobogou.modules.mapper.data.OrderMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderService extends CrudService<Order, OrderMapper> {

    @Override
    public Order get(Order entity) {
        return super.get(entity);
    }

    @Override
    public List<Order> findList(Order entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Order> findPage(Page<Order> page, Order entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Order entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Order entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Order entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Order> findRecoveryPage(Page<Order> page, Order entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Order entity) {
        return super.recovery(entity);
    }
}
