package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 *订单mapper层
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
