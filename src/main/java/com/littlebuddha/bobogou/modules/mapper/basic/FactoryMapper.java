package com.littlebuddha.bobogou.modules.mapper.basic;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import com.littlebuddha.bobogou.modules.entity.data.City;
import org.apache.ibatis.annotations.Mapper;

/**
 *厂商mapper层
 */
@Mapper
public interface FactoryMapper extends BaseMapper<Factory> {
}
