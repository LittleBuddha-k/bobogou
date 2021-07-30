package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.Goods;
import com.littlebuddha.bobogou.modules.entity.data.GoodsClassify;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品分类mapper层
 */
@Mapper
public interface GoodsClassifyMapper extends BaseMapper<GoodsClassify> {

    GoodsClassify getByGoods(GoodsClassify goodsClassify);

    //根据商品id逻辑删除
    void deleteLogicByGoods(GoodsClassify goodsClassify);

    //根据商品id物理删除
    void deletePhysicsByGoods(GoodsClassify goodsClassify);

    //删除的时候查询商品分类关联表中是否有关联的分类数据--如果有则不能删除
    List<GoodsClassify> findByClassify(GoodsClassify findByClassify);
}
