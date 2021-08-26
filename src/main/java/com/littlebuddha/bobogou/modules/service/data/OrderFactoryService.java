package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import com.littlebuddha.bobogou.modules.entity.data.OrderFactory;
import com.littlebuddha.bobogou.modules.mapper.data.OrderFactoryMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderFactoryService extends CrudService<OrderFactory, OrderFactoryMapper> {

    @Override
    public OrderFactory get(OrderFactory entity) {
        OrderFactory orderFactory = super.get(entity);
        if (orderFactory != null && orderFactory.getFreight() != null){
            orderFactory.setFreight(orderFactory.getFreight() / 100);
        }
        return orderFactory;
    }

    @Override
    public List<OrderFactory> findList(OrderFactory entity) {
        List<OrderFactory> list = super.findList(entity);
        for (OrderFactory orderFactory : list) {
            if (orderFactory != null && orderFactory.getFreight() != null){
                orderFactory.setFreight(orderFactory.getFreight() / 100);
            }
        }
        return list;
    }

    @Override
    public PageInfo<OrderFactory> findPage(Page<OrderFactory> page, OrderFactory entity) {
        if (entity != null && StringUtils.isNotBlank(entity.getTrackingNo())){
            String trackingNo = StringUtils.deleteWhitespace(entity.getTrackingNo());
            entity.setTrackingNo(trackingNo);
        }
        if (entity != null && StringUtils.isNotBlank(entity.getOrderNumber())){
            String orderNumber = StringUtils.deleteWhitespace(entity.getOrderNumber());
            entity.setOrderNumber(orderNumber);
        }
        PageInfo<OrderFactory> page1 = super.findPage(page, entity);
        List<OrderFactory> list = page1.getList();
        for (OrderFactory orderFactory : list) {
            if (orderFactory != null && orderFactory.getFreight() != null){
                orderFactory.setFreight(orderFactory.getFreight() / 100);
            }
        }
        return page1;
    }

    @Override
    public int save(OrderFactory entity) {
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(OrderFactory entity) {
        return super.deleteByLogic(entity);
    }
}
