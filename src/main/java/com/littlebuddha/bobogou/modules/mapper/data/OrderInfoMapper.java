package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单mapper层
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
}
