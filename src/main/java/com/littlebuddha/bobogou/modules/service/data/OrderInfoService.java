package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.OrderInfo;
import com.littlebuddha.bobogou.modules.mapper.data.OrderInfoMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderInfoService extends CrudService<OrderInfo, OrderInfoMapper> {

    @Override
    public OrderInfo get(OrderInfo entity) {
        return super.get(entity);
    }

    @Override
    public List<OrderInfo> findList(OrderInfo entity) {
        List<OrderInfo> list = super.findList(entity);
        if (list != null && !list.isEmpty()){
            for (OrderInfo orderInfo : list) {
                if (orderInfo != null && orderInfo.getPrice() != null){
                    orderInfo.setPrice(Double.toString(Double.valueOf(orderInfo.getPrice()) / 100));
                }
            }
        }
        return list;
    }

    @Override
    public PageInfo<OrderInfo> findPage(Page<OrderInfo> page, OrderInfo entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(OrderInfo entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(OrderInfo entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(OrderInfo entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<OrderInfo> findRecoveryPage(Page<OrderInfo> page, OrderInfo entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(OrderInfo entity) {
        return super.recovery(entity);
    }
}
