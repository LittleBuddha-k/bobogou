package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.entity.system.utils.Icon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *图标mapper层
 */
@Mapper
public interface AreaMapper extends BaseMapper<Area> {

    /**
     * 用于多选区级，根据市级的信息来查询不分页的区级数据
     * @param
     * @return
     */
    List<Area> findNoPageByCityCode(@Param("name") String name,@Param("cityCode") String cityCode);

    /**
     * 根据区code获取区数据
     * @param areaCode
     * @return
     */
    Area getAreaByCode(String areaCode);
}
