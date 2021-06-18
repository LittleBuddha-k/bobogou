package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.Banner;
import com.littlebuddha.bobogou.modules.entity.data.GoodsInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品详情mapper层
 */
@Mapper
public interface GoodsInfoMapper extends BaseMapper<GoodsInfo> {

    GoodsInfo getByGoods(GoodsInfo goodsId);
}
