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
     * 根据order的id查询本订单下的信息导出数据
     * @param
     * @return
     */
    List<OrderExportDTO> findOrderExportList(String id);

    /**
     * 确认发货---根据id修改发货标识字段
     * @param id
     * @return
     */
    int confirmDeliver(String id);
}
