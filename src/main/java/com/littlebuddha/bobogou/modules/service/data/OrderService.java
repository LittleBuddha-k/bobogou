package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Goods;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import com.littlebuddha.bobogou.modules.entity.data.OrderInfo;
import com.littlebuddha.bobogou.modules.entity.data.utils.OrderExportDTO;
import com.littlebuddha.bobogou.modules.entity.other.UserMember;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRegion;
import com.littlebuddha.bobogou.modules.mapper.common.DictDataMapper;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsMapper;
import com.littlebuddha.bobogou.modules.mapper.data.OrderInfoMapper;
import com.littlebuddha.bobogou.modules.mapper.data.OrderMapper;
import com.littlebuddha.bobogou.modules.mapper.other.CustomerUserMapper;
import com.littlebuddha.bobogou.modules.mapper.other.UserMemberMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRegionMapper;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderService extends CrudService<Order, OrderMapper> {

    @Resource
    private CustomerUserMapper customerUserMapper;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private OperatorRegionMapper operatorRegionMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private DictDataMapper dictDataMapper;

    @Autowired
    private DictDataService dictDataService;

    @Resource
    private UserMemberMapper userMemberMapper;

    @Override
    public Order get(Order entity) {
        Order order = super.get(entity);
        if (order != null && StringUtils.isNotBlank(order.getId())) {
            List<OrderInfo> orderInfoList = orderInfoMapper.findList(new OrderInfo(order));
            order.setOrderInfoList(orderInfoList);
        }
        if (order != null && order.getUserId() != null) {
            UserMember userMember = new UserMember();
            userMember.setUserId(order.getUserId());
            List<UserMember> userMemberList = userMemberMapper.getByUser(userMember);
            if (userMemberList != null && !userMemberList.isEmpty()) {
                UserMember userMember1 = userMemberList.get(0);
                if (userMember1 != null && StringUtils.isNotBlank(userMember1.getName())) {
                    order.setName(userMember1.getName());
                }
            }
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
        if (order != null && order.getDeduction() != null) {
            order.setDeduction(order.getDeduction() / 100);
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
            if (order != null && order.getDeduction() != null) {
                order.setDeduction(order.getDeduction() / 100);
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
        }
        //查询结果
        PageInfo<Order> pageInfo = null;
        //根据当前用户等级查询对应订单数据
        Operator currentUser = UserUtils.getCurrentUser();
        entity.setCurrentUser(currentUser);
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        //查询当前用户的所有区域list
        List<OperatorRegion> operatorRegionList = operatorRegionMapper.getOperatorRegionByCurrentUser(operatorRegion);
        List<Order> result = new ArrayList<>();
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            entity.setPage(page);
            PageHelper.startPage(entity.getPageNo(), entity.getPageSize());
            if (operatorRegionList != null && !operatorRegionList.isEmpty()) {
                for (OperatorRegion region : operatorRegionList) {
                    if (region != null) {
                        entity.setProvinceId(region.getProvinceId());
                        entity.setCityId(region.getCityId());
                        entity.setAreaId(region.getAreaId());
                        entity.setStreetId(region.getStreetId());
                        List<Order> orderList = orderMapper.findList(entity);
                        if (orderList != null) {
                            result.addAll(orderList);
                        }
                    }
                }
            }
            pageInfo = new PageInfo<Order>(result);
            //对result去重
            /*if (!result.isEmpty()) {
                //去重
                for (int i = 0; i < result.size() - 1; i++) {
                    for (int j = result.size() - 1; j > i; j--) {
                        if (result.get(j).getId().equals(result.get(i).getId())) {
                            result.remove(j);
                        }
                    }
                }
            }
            for (Order order : result) {
                if (order != null && order.getGrossAmount() != null) {
                    order.setGrossAmount(order.getGrossAmount() / 100);
                }
            }*/
        }
        return pageInfo;
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

    /**
     * 查询导出数据
     *
     * @param
     * @return
     */
    public List<OrderExportDTO> findOrderExportList(Order entity) {
        if (entity != null) {
            String number = StringUtils.deleteWhitespace(entity.getNumber());
            entity.setNumber(number);
            if (entity.getAddressId() != null) {
                String addressId = StringUtils.deleteWhitespace(entity.getAddressId().toString());
                entity.setAddressId(Integer.valueOf(addressId));
            }
        }
        //查询结果
        List<OrderExportDTO> result = new ArrayList<>();
        //根据当前用户等级查询对应订单数据
        Operator currentUser = UserUtils.getCurrentUser();
        entity.setCurrentUser(currentUser);
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        //查询当前用户的所有区域list
        List<OperatorRegion> operatorRegionList = operatorRegionMapper.getOperatorRegionByCurrentUser(operatorRegion);
        if (operatorRegionList != null && !operatorRegionList.isEmpty()) {
            for (OperatorRegion region : operatorRegionList) {
                if (region != null) {
                    entity.setProvinceId(region.getProvinceId());
                    entity.setCityId(region.getCityId());
                    entity.setAreaId(region.getAreaId());
                    entity.setStreetId(region.getStreetId());
                    List<OrderExportDTO> orderList = orderMapper.findOrderExportList(entity);
                    if (orderList != null) {
                        result.addAll(orderList);
                    }
                }
            }
            //对result去重
            /*if (!result.isEmpty()) {
                //去重
                for (int i = 0; i < result.size() - 1; i++) {
                    for (int j = result.size() - 1; j > i; j--) {
                        if (result.get(j).getId().equals(result.get(i).getId())) {
                            result.remove(j);
                        }
                    }
                }
            }*/
        }
        Map<String, String> distributionModeMap = dictDataService.getMap("order_distribution_mode");
        Map<String, String> payModeMap = dictDataService.getMap("order_pay_mode");
        Map<String, String> typeMap = dictDataService.getMap("oder_type");
        Map<String, String> orderStatusMap = dictDataService.getMap("order_status");
        Map<String, String> orderIsInvoiceMap = dictDataService.getMap("data_order_is_invoice");
        for (OrderExportDTO orderExportDTO : result) {
            if (orderExportDTO != null) {
                if (orderExportDTO.getGrossAmount() != null) {
                    orderExportDTO.setGrossAmount(orderExportDTO.getGrossAmount() / 100);
                }
                if (orderExportDTO.getPrice() != null) {
                    orderExportDTO.setPrice(orderExportDTO.getPrice() / 100);
                }
                if (orderExportDTO.getDistributionMode() != null && StringUtils.isNotBlank(orderExportDTO.getDistributionMode())) {
                    String distribution = distributionModeMap.get(orderExportDTO.getDistributionMode());
                    String distributionMode = distribution == null ? "" : distribution;
                    orderExportDTO.setDistributionMode(distributionMode);
                }
                if (orderExportDTO.getPayMode() != null && StringUtils.isNotBlank(orderExportDTO.getPayMode())) {
                    String payMode = payModeMap.get(orderExportDTO.getPayMode());
                    String pay = payMode == null ? "" : payMode;
                    orderExportDTO.setPayMode(pay);
                }
                if (orderExportDTO.getType() != null && StringUtils.isNotBlank(orderExportDTO.getType())) {
                    String type = typeMap.get(orderExportDTO.getType());
                    String orderType = type == null ? "" : type;
                    orderExportDTO.setType(orderType);
                }
                if (orderExportDTO.getStatus() != null && StringUtils.isNotBlank(orderExportDTO.getStatus())) {
                    String orderStatus = orderStatusMap.get(orderExportDTO.getStatus());
                    String status = orderStatus == null ? "" : orderStatus;
                    orderExportDTO.setStatus(status);
                }
                if (orderExportDTO.getIsInvoice() != null && StringUtils.isNotBlank(orderExportDTO.getIsInvoice())) {
                    String isInvoice = orderIsInvoiceMap.get(orderExportDTO.getIsInvoice());
                    String invoice = isInvoice == null ? "" : isInvoice;
                    orderExportDTO.setIsInvoice(invoice);
                }
            }
        }
        return result;
    }
}
