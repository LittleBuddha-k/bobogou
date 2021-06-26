package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.RegionGoods;
import org.apache.ibatis.annotations.Mapper;

/**
 * 轮播图mapper层
 */
@Mapper
public interface RegionGoodsMapper extends BaseMapper<RegionGoods> {

    //修改区域商品上下架
    int updateIsMarket(RegionGoods regionGoods);
}
