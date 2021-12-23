package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.entity.data.utils.AreaSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *区域选择Mapper
 */
@Mapper
public interface AreaSelectMapper extends BaseMapper<AreaSelect> {

    public List<AreaSelect> findCitySelectByProvinceCode(@Param("provinceCode") String provinceCode);

    public List<AreaSelect> findAreaSelectByProvinceCode(@Param("cityCode") String cityCode);

    public List<AreaSelect> findStreetSelectByProvinceCode(@Param("areaCode") String areaCode);
}
