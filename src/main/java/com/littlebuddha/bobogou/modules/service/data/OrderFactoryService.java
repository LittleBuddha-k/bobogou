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
        //??????????????????
        List<OrderFactory> result = new ArrayList<>();
        //????????????????????????
        Operator currentUser = UserUtils.getCurrentUser();
        entity.setCurrentUser(currentUser);
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        //?????????????????????????????????list
        List<OperatorRegion> operatorRegionList = operatorRegionMapper.getOperatorRegionByCurrentUser(operatorRegion);
        //??????????????????
        PageInfo<OrderFactory> pageInfo = null;
        //??????????????????????????????????????????????????????
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            if (currentUser != null && "1".equals(currentUser.getId())){//??????????????????????????????????????????
                result = orderFactoryMapper.findList(entity);
            }else {
                for (OperatorRegion region : operatorRegionList) {//???????????????????????????
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
            //??????
            result = ListUtils.removeDuplicateOrderFactory(result);
            //????????????
            List<OrderFactory> list = PageUtil.startPage(result, entity.getPageNo(), entity.getPageSize());
            if (list != null && !list.isEmpty()) {
                for (OrderFactory orderFactory : list) {
                    if (orderFactory != null && orderFactory.getFreight() != null) {
                        orderFactory.setFreight(orderFactory.getFreight() / 100);
                    }
                }
            }
            //????????????
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
        //?????????????????????????????????????????????????????????????????????
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
