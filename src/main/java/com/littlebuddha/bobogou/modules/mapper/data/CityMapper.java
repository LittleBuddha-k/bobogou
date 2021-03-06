package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.City;
import com.littlebuddha.bobogou.modules.entity.system.utils.Icon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *市级管理mapper层
 */
@Mapper
public interface CityMapper extends BaseMapper<City> {

    /**
     * 用于多选市级，根据省级的信息来查询不分页的市级数据
     * @param provinceIds
     * @return
     */
    List<City> findNoPageByProvinceCode(@Param("name") String name,@Param("provinceCode") String provinceCode);

    /**
     * 根据市code查询市数据
     * @param code
     * @return
     */
    City getCityByCode(String code);
}
