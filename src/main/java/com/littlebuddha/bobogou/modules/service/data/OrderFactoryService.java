package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.ListUtils;
import com.littlebuddha.bobogou.common.utils.PageUtil;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import com.littlebuddha.bobogou.modules.entity.data.OrderFactory;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRegion;
import com.littlebuddha.bobogou.modules.mapper.data.OrderFactoryMapper;
import com.littlebuddha.bobogou.modules.mapper.data.OrderMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRegionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderFactoryService extends CrudService<OrderFactory, OrderFactoryMapper> {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OperatorRegionMapper operatorRegionMapper;

    @Resource
    private OrderFactoryMapper orderFactoryMapper;

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
        //封装的结果集
        List<OrderFactory> result = new ArrayList<>();
        //获取当前用户区域
        Operator currentUser = UserUtils.getCurrentUser();
        entity.setCurrentUser(currentUser);
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        //查询当前用户的所有区域list
        List<OperatorRegion> operatorRegionList = operatorRegionMapper.getOperatorRegionByCurrentUser(operatorRegion);
        //查询封装结果
        PageInfo<OrderFactory> pageInfo = null;
        //根据当前用户区域查询对应发货单位数据
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            if (currentUser != null && "1".equals(currentUser.getId())){//如果是超级管理员查询全部数据
                result = orderFactoryMapper.findList(entity);
            }else {
                for (OperatorRegion region : operatorRegionList) {//按照区域来进行查询
                    if (region != null) {
                        entity.setProvinceId(region.getProvinceId());
                        entity.setCityId(region.getCityId());
                        entity.setAreaId(region.getDistrictId());
                        entity.setStreetId(region.getStreetId());
                        List<OrderFactory> factoryList = orderFactoryMapper.findList(entity);
                        if (factoryList != null) {
                            result.addAll(factoryList);
                        }
                    }
                }
            }
            //去重
            result = ListUtils.removeDuplicateOrderFactory(result);
            //分页数据
            List<OrderFactory> list = PageUtil.startPage(result, entity.getPageNo(), entity.getPageSize());
            if (list != null && !list.isEmpty()) {
                for (OrderFactory orderFactory : list) {
                    if (orderFactory != null && orderFactory.getFreight() != null) {
                        orderFactory.setFreight(orderFactory.getFreight() / 100);
                    }
                }
            }
            //组装结果
            if (result != null && !result.isEmpty()) {
                pageInfo = new PageInfo<>();
                pageInfo.setList(list);
                pageInfo.setTotal(result.size());
            } else {
                pageInfo = new PageInfo<>();
            }
        }
        return pageInfo;
    }

    @Override
    public int save(OrderFactory entity) {
        //如果修改出库状态为已发货，关联修改订单为已发货
        //if (entity != null && "2".equals(entity.getOutStatus()));
        int save = super.save(entity);
        /*if (entity.getOrderId() != null) {
            orderMapper.confirmDeliver(entity.getOrderId());
        }*/
        return save;
    }

    @Override
    public int deleteByLogic(OrderFactory entity) {
        return super.deleteByLogic(entity);
    }
}
