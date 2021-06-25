package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.Goods;
import com.littlebuddha.bobogou.modules.entity.data.GoodsClassify;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品分类mapper层
 */
@Mapper
public interface GoodsClassifyMapper extends BaseMapper<GoodsClassify> {

    GoodsClassify getByGoods(GoodsClassify goodsClassify);
}
