package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Goods;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import com.littlebuddha.bobogou.modules.entity.data.OrderInfo;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsMapper;
import com.littlebuddha.bobogou.modules.mapper.data.OrderInfoMapper;
import com.littlebuddha.bobogou.modules.mapper.data.OrderMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderService extends CrudService<Order, OrderMapper> {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private GoodsMapper medicineMapper;

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
        if (entity != null) {
            String number = StringUtils.deleteWhitespace(entity.getNumber());
            entity.setNumber(number);
            if (entity.getAddressId() != null) {
                String addressId = StringUtils.deleteWhitespace(entity.getAddressId().toString());
                entity.setAddressId(Integer.valueOf(addressId));
            }
            if (entity.getDistributionMode() != null) {
                String distributionMode = StringUtils.deleteWhitespace(entity.getDistributionMode().toString());
                entity.setDistributionMode(Integer.valueOf(distributionMode));
            }
            String trackingNo = StringUtils.deleteWhitespace(entity.getTrackingNo());
            entity.setTrackingNo(trackingNo);
        }
        return super.findPage(page, entity);
    }

    @Override
    @Transactional
    public int save(Order entity) {
        entity.setIdType("AUTO");
        int save = super.save(entity);
        super.save(entity);
        if (entity != null && entity.getOrderInfoList() != null && entity.getOrderInfoList().size() > 0) {
            for (OrderInfo orderInfo : entity.getOrderInfoList()) {
                orderInfo.setIdType("AUTO");
                Goods medicine = null;
                if (orderInfo.getGoodsId() != null && StringUtils.isNotBlank(orderInfo.getGoodsId())) {
                    medicine = medicineMapper.get(orderInfo.getGoodsId());
                }
                if (orderInfo.getId() == null) {
                    continue;
                }
                if (OrderInfo.DEL_FLAG_NORMAL.equals(orderInfo.getIsDeleted())) {
                    if (StringUtils.isBlank(orderInfo.getId())) {
                        orderInfo.setOrder(entity);
                        orderInfo.setMedicine(medicine);
                        orderInfo.preInsert();
                        orderInfoMapper.insert(orderInfo);
                    } else {
                        orderInfo.setOrder(entity);
                        orderInfo.setMedicine(medicine);
                        orderInfo.preUpdate();
                        orderInfoMapper.update(orderInfo);
                    }
                } else {
                    orderInfoMapper.deleteByPhysics(orderInfo);
                }
            }
        }
        return save;
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
