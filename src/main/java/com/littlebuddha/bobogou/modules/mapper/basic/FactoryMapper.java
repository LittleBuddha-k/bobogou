package com.littlebuddha.bobogou.modules.mapper.basic;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import com.littlebuddha.bobogou.modules.entity.data.City;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *厂商mapper层
 */
@Mapper
public interface FactoryMapper extends BaseMapper<Factory> {

    /**
     * 查询资质过期列表
     * @param entity
     * @return
     */
    List<Factory> findListExpire(Factory entity);
}
