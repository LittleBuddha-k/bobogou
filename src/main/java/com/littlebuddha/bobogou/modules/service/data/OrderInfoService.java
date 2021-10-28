package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Goods;
import com.littlebuddha.bobogou.modules.entity.data.OrderInfo;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsMapper;
import com.littlebuddha.bobogou.modules.mapper.data.OrderInfoMapper;
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
public class OrderInfoService extends CrudService<OrderInfo, OrderInfoMapper> {

    @Resource
    private CustomerUserMapper customerUserMapper;

    @Resource
    private GoodsMapper goodsMapper;

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
    @Transactional
    public int deleteByLogic(OrderInfo entity) {
        int integral = 0;
        int healthBeans = 0;
        int count = 0;
        OrderInfo orderInfo = get(entity);
        String userId = orderInfo.getUserId();
        if (orderInfo != null && StringUtils.isNotBlank(orderInfo.getAmount())){
            count = Integer.valueOf(orderInfo.getAmount());
        }
        if (orderInfo != null){
            String goodsId = orderInfo.getGoodsId();
            if (StringUtils.isNotBlank(goodsId)){
                Goods goods = goodsMapper.get(goodsId);
                if (goods != null && goods.getIntegral() != null){
                    integral = goods.getIntegral().intValue() * count;
                }
                if (goods != null && goods.getHealthBeans() != null){
                    healthBeans = goods.getHealthBeans().intValue() * count;
                }
            }
        }
        //删除订单商品信息
        int row = super.deleteByLogic(entity);
        //更新用户的积分和播播豆
        customerUserMapper.updateIntegralAndHealthBeans(integral,healthBeans,userId);
        return row;
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
