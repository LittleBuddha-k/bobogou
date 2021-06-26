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

    //在区域商品做更新时先将原有的区域商品分配数量恢复到商品表更新库存
    int recoveryStock(Goods goods);

    //上下架操作
    int onTheShelf(Goods goods);
}
