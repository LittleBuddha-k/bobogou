package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.ListUtils;
import com.littlebuddha.bobogou.common.utils.PageUtil;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.ChargeBack;
import com.littlebuddha.bobogou.modules.entity.data.Goods;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import com.littlebuddha.bobogou.modules.entity.data.OrderInfo;
import com.littlebuddha.bobogou.modules.entity.data.utils.OrderExportDTO;
import com.littlebuddha.bobogou.modules.entity.other.UserMember;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRegion;
import com.littlebuddha.bobogou.modules.mapper.common.DictDataMapper;
import com.littlebuddha.bobogou.modules.mapper.data.ChargeBackMapper;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsMapper;
import com.littlebuddha.bobogou.modules.mapper.data.OrderInfoMapper;
import com.littlebuddha.bobogou.modules.mapper.data.OrderMapper;
import com.littlebuddha.bobogou.modules.mapper.other.CustomerUserMapper;
import com.littlebuddha.bobogou.modules.mapper.other.UserMemberMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRegionMapper;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import com.littlebuddha.bobogou.modules.service.other.UserMemberService;
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

    @Resource
    private ChargeBackMapper chargeBackMapper;

    @Autowired
    private UserMemberService userMemberService;

    @Override
    public Order get(Order entity) {
        Order order = super.get(entity);
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
        //????????????
        PageInfo<Order> pageInfo = null;
        //????????????????????????????????????????????????
        Operator currentUser = UserUtils.getCurrentUser();
        entity.setCurrentUser(currentUser);
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        //?????????????????????????????????list
        List<OperatorRegion> operatorRegionList = operatorRegionMapper.getOperatorRegionByCurrentUser(operatorRegion);
        List<Order> result = new ArrayList<>();
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            entity.setPage(page);
            //????????????????????????????????????????????????????????????????????????????????????????????????
            if (currentUser.getAreaManager() == 4 || currentUser.getAreaManager() == 5 || currentUser.getAreaManager() == 7) {
                /*PageHelper.startPage(entity.getPageNo(), entity.getPageSize());*/
                result = orderMapper.findList(entity);
                /*result = ListUtils.removeDuplicateOrder(result);
                pageInfo = new PageInfo<Order>(result);
                for (Order order : result) {
                    if (order != null && order.getGrossAmount() != null) {
                        order.setGrossAmount(order.getGrossAmount() / 100);
                    }
                }
                return pageInfo;*/
            } else if (operatorRegionList != null && !operatorRegionList.isEmpty()) {//??????????????????????????????????????????????????????
                for (OperatorRegion region : operatorRegionList) {
                    if (region != null) {
                        entity.setProvinceId(region.getProvinceId());
                        entity.setCityId(region.getCityId());
                        entity.setAreaId(region.getDistrictId());
                        entity.setStreetId(region.getStreetId());
                        List<Order> orderList = orderMapper.findList(entity);
                        if (orderList != null) {
                            result.addAll(orderList);
                        }
                    }
                }
            }
            for (Order order : result) {
                if (order != null && order.getGrossAmount() != null) {
                    order.setGrossAmount(order.getGrossAmount() / 100);
                }
            }
            result = ListUtils.removeDuplicateOrder(result);
            List list = PageUtil.startPage(result, entity.getPageNo(), entity.getPageSize());
            if (result != null && !result.isEmpty()) {
                pageInfo = new PageInfo<Order>();
                pageInfo.setList(list);
                pageInfo.setTotal(result.size());
            } else {
                pageInfo = new PageInfo<>();
            }
        }
        return pageInfo;
    }

    @Override
    @Transactional
    public int save(Order entity) {
        entity.setIdType("AUTO");
        if (entity != null && entity.getGrossAmount() != null) {
            entity.setGrossAmount(entity.getGrossAmount() * 100);
        }
        int save = super.save(entity);
        if (entity != null && entity.getOrderInfoList() != null && entity.getOrderInfoList().size() > 0) {
            for (OrderInfo orderInfo : entity.getOrderInfoList()) {
                if (orderInfo.getId() == null) {
                    continue;
                }
                if (StringUtils.isBlank(orderInfo.getId())) {
                    orderInfo.setGoodsId(entity.getId());
                    orderInfo.preInsert();
                    orderInfoMapper.insert(orderInfo);
                } else {
                    //?????????????????????????????????????????????????????????????????????????????????????????????
                    OrderInfo initOrderInfo = orderInfoMapper.get(orderInfo.getId());
                    //????????????
                    Goods goods = goodsMapper.get(orderInfo.getGoodsId());
                    if (orderInfo.getAmount() != initOrderInfo.getAmount()) {
                        //????????????????????????????????????????????????????????????
                        int change = initOrderInfo.getAmount() - orderInfo.getAmount();
                        //???????????????????????????????????????????????????
                        ChargeBack chargeBack = new ChargeBack();
                        chargeBack.setOrderId(orderInfo.getOrderId());
                        chargeBack.setNumber(entity.getNumber());
                        chargeBack.setOrderInfoId(orderInfo.getId());
                        chargeBack.setUserId(orderInfo.getUserId());
                        chargeBack.setGoodsId(orderInfo.getGoodsId());
                        chargeBack.setCount(initOrderInfo.getAmount() - orderInfo.getAmount());
                        chargeBack.setPrice(orderInfo.getPrice());//??????????????????
                        if (goods != null) {
                            double managementExpense = chargeBack.getCount() * orderInfo.getPrice() * (goods.getAdministrativeFee());
                            if (managementExpense != 0.0){
                                chargeBack.setManagementExpense(managementExpense / 100);
                            }
                        }
                        //todo????????????????????????
                        chargeBack.preInsert();
                        chargeBackMapper.insert(chargeBack);
                        //??????????????????????????????

                    }
                    //??????????????????
                    orderInfo.preUpdate();
                    orderInfoMapper.update(orderInfo);
                }
            }
        }
        return save;
    }

    @Override
    @Transactional
    public int deleteByLogic(Order entity) {
        int orderRow = super.deleteByLogic(entity);
        //????????????orderInfo???
        orderInfoMapper.deleteByOrderLogic(entity.getId());
        return orderRow;
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
     * ??????????????????
     *
     * @param
     * @return
     */
    public List<OrderExportDTO> findOrderExportList(String id) {
        List<OrderExportDTO> orderList = orderMapper.findOrderExportList(id);
        //orderList = ListUtils.removeDuplicateOrderExportDTO(orderList);
        Map<String, String> distributionModeMap = dictDataService.getMap("order_distribution_mode");
        Map<String, String> payModeMap = dictDataService.getMap("order_pay_mode");
        Map<String, String> typeMap = dictDataService.getMap("oder_type");
        Map<String, String> orderStatusMap = dictDataService.getMap("order_status");
        Map<String, String> orderIsInvoiceMap = dictDataService.getMap("data_order_is_invoice");
        for (OrderExportDTO orderExportDTO : orderList) {
            if (orderExportDTO != null) {
                UserMember userMember = userMemberService.getUserMemberOnlyOne(orderExportDTO.getUserId());//????????????????????????vip??????
                orderExportDTO.setName(userMember.getName());
                if (orderExportDTO.getGrossAmount() != null) {
                    orderExportDTO.setGrossAmount(orderExportDTO.getGrossAmount() / 100);
                }
                if (orderExportDTO.getPaymentAmount() != null) {
                    orderExportDTO.setPaymentAmount(orderExportDTO.getPaymentAmount() / 100);
                }
                if (orderExportDTO.getActualAmountPaid() != null) {
                    orderExportDTO.setActualAmountPaid(orderExportDTO.getActualAmountPaid() / 100);
                }
                if (orderExportDTO.getDeduction() != null) {
                    orderExportDTO.setDeduction(orderExportDTO.getDeduction() / 100);
                }
                if (orderExportDTO.getPrice() != null) {
                    orderExportDTO.setPrice(orderExportDTO.getPrice() / 100);
                }
                if (orderExportDTO.getFreight() != null) {
                    orderExportDTO.setFreight(orderExportDTO.getFreight() / 100);
                }
                if (orderExportDTO.getManagementCost() != null) {
                    orderExportDTO.setManagementCost(orderExportDTO.getManagementCost() / 100);
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
        return orderList;
    }

    /**
     * ????????????---??????id????????????????????????
     *
     * @param id
     * @return
     */
    public int confirmDeliver(String id) {
        int row = orderMapper.confirmDeliver(id);
        return row;
    }
}
