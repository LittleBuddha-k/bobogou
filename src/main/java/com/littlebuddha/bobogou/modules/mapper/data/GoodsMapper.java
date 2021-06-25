package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
 *药品mapper层
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    //查询库存数
    Goods getStock(Goods goods);

    //更新库存
    int updateStock(Goods goods);

    //上下架操作
    int onTheShelf(Goods goods);
}
