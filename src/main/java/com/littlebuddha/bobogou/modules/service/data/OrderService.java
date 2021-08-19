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
import com.littlebuddha.bobogou.modules.mapper.other.CustomerUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderService extends CrudService<Order, OrderMapper> {

    @Resource
    private CustomerUserMapper customerUserMapper;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public Order get(Order entity) {
        Order order = super.get(entity);
        if (order != null && StringUtils.isNotBlank(order.getId())) {
            List<OrderInfo> orderInfoList = orderInfoMapper.findList(new OrderInfo(order));
            order.setOrderInfoList(orderInfoList);
        }
        if (order != null && order.getGrossAmount() != null) {
            order.setGrossAmount(order.getGrossAmount() / 100);
        }
        if (order != null && order.getPaymentAmount() != null) {
            order.setPaymentAmount(order.getPaymentAmount() / 100);
        }
        if (order != null && order.getActualAmountPaid() != null) {
            order.setActualAmountPaid(order.getActualAmountPaid() / 100);
        }
        if (order != null && order.getDeductiona() != null) {
            order.setDeductiona(order.getDeductiona() / 100);
        }
        if (order != null && order.getFreight() != null) {
            order.setFreight(order.getFreight() / 100);
        }
        if (order != null && order.getManagementCost() != null) {
            order.setManagementCost(order.getManagementCost() / 100);
        }
        if (order != null && order.getProvinceCost() != null) {
            order.setProvinceCost(order.getProvinceCost() / 100);
        }
        if (order != null && order.getCityCost() != null) {
            order.setCityCost(order.getCityCost() / 100);
        }
        if (order != null && order.getDistrictCost() != null) {
            order.setDistrictCost(order.getDistrictCost() / 100);
        }
        return order;
    }

    @Override
    public List<Order> findList(Order entity) {
        List<Order> list = super.findList(entity);
        for (Order order : list) {
            if (order != null && order.getGrossAmount() != null) {
                order.setGrossAmount(order.getGrossAmount() / 100);
            }
            if (order != null && order.getPaymentAmount() != null) {
                order.setPaymentAmount(order.getPaymentAmount() / 100);
            }
            if (order != null && order.getActualAmountPaid() != null) {
                order.setActualAmountPaid(order.getActualAmountPaid() / 100);
            }
            if (order != null && order.getDeductiona() != null) {
                order.setDeductiona(order.getDeductiona() / 100);
            }
            if (order != null && order.getFreight() != null) {
                order.setFreight(order.getFreight() / 100);
            }
            if (order != null && order.getManagementCost() != null) {
                order.setManagementCost(order.getManagementCost() / 100);
            }
            if (order != null && order.getProvinceCost() != null) {
                order.setProvinceCost(order.getProvinceCost() / 100);
            }
            if (order != null && order.getCityCost() != null) {
                order.setCityCost(order.getCityCost() / 100);
            }
            if (order != null && order.getDistrictCost() != null) {
                order.setDistrictCost(order.getDistrictCost() / 100);
            }
        }
        return list;
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
                entity.setDistributionMode(distributionMode);
            }
            String trackingNo = StringUtils.deleteWhitespace(entity.getTrackingNo());
            entity.setTrackingNo(trackingNo);
        }
        PageInfo<Order> page1 = super.findPage(page, entity);
        List<Order> list = page1.getList();
        for (Order order : list) {
            if (order != null && order.getGrossAmount() != null) {
                order.setGrossAmount(order.getGrossAmount() / 100);
            }
            if (order != null && order.getPaymentAmount() != null) {
                order.setPaymentAmount(order.getPaymentAmount() / 100);
            }
            if (order != null && order.getActualAmountPaid() != null) {
                order.setActualAmountPaid(order.getActualAmountPaid() / 100);
            }
            if (order != null && order.getDeductiona() != null) {
                order.setDeductiona(order.getDeductiona() / 100);
            }
            if (order != null && order.getFreight() != null) {
                order.setFreight(order.getFreight() / 100);
            }
            if (order != null && order.getManagementCost() != null) {
                order.setManagementCost(order.getManagementCost() / 100);
            }
            if (order != null && order.getProvinceCost() != null) {
                order.setProvinceCost(order.getProvinceCost() / 100);
            }
            if (order != null && order.getCityCost() != null) {
                order.setCityCost(order.getCityCost() / 100);
            }
            if (order != null && order.getDistrictCost() != null) {
                order.setDistrictCost(order.getDistrictCost() / 100);
            }
        }
        return page1;
    }

    @Override
    @Transactional
    public int save(Order entity) {
        entity.setIdType("AUTO");
        int save = super.save(entity);
        if (entity != null && entity.getOrderInfoList() != null && entity.getOrderInfoList().size() > 0) {
            for (OrderInfo orderInfo : entity.getOrderInfoList()) {
                orderInfo.setIdType("AUTO");
                Goods medicine = null;
                if (orderInfo.getGoodsId() != null && StringUtils.isNotBlank(orderInfo.getGoodsId())) {
                    medicine = goodsMapper.get(orderInfo.getGoodsId());
                }
                if (orderInfo.getId() == null) {
                    continue;
                }
                if (OrderInfo.DEL_FLAG_NORMAL.equals(orderInfo.getIsDeleted())) {
                    if (StringUtils.isBlank(orderInfo.getId())) {
                        orderInfo.setOrder(entity);
                        orderInfo.setGoods(medicine);
                        orderInfo.preInsert();
                        orderInfoMapper.insert(orderInfo);
                    } else {
                        orderInfo.setOrder(entity);
                        orderInfo.setGoods(medicine);
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
