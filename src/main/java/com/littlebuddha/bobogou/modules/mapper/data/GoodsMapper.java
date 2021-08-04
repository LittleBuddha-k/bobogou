package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 根据厂商、商品名查询商品数据
     * @param goods
     * @return
     */
    List<Goods> getByFactoryAndName(Goods goods);

    /**
     * 更新商品流程状态
     * @param goods
     * @return
     */
    int updateGoodsAct(Goods goods);

    /**
     * 查找当前角色审核数据
     * @param entity
     * @return
     */
    List<Goods> findTodoList(Goods entity);

    /**
     * 商品列表数据，查询当前登录者创建的数据及其下级角色创建的数据
     *
     * @return
     */
    List<Goods> findCurrentDataList(Goods entity);
}
