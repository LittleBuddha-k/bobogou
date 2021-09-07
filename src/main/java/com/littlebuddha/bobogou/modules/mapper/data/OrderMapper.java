package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import com.littlebuddha.bobogou.modules.entity.data.utils.OrderExportDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *订单mapper层
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 查询导出数据
     * @param
     * @return
     */
    List<OrderExportDTO> findOrderExportList(Order order);
}
