package com.littlebuddha.bobogou.modules.mapper.system;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.utils.AreaSelect;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRegion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 图标mapper层
 */
@Mapper
public interface OperatorRegionMapper extends BaseMapper<OperatorRegion> {

    /**
     * 根据用户id查询
     *
     * @param operatorRegion
     */
    List<OperatorRegion> getOperatorRegionByCurrentUser(OperatorRegion operatorRegion);

    /**
     * 根据当前用户查询区域选项数据
     *
     * @param currentUserId
     * @return
     */
    List<AreaSelect> findProvinceSelectByCurrentUser(@Param("currentUserId") String currentUserId);

    /**
     * 根据当前用户查询区域选项数据
     *
     * @param currentUserId
     * @return
     */
    List<AreaSelect> findCitySelectByCurrentUser(@Param("currentUserId") String currentUserId);

    /**
     * 根据当前用户查询区域选项数据
     *
     * @param currentUserId
     * @return
     */
    List<AreaSelect> findAreaSelectByCurrentUser(@Param("currentUserId") String currentUserId);

    /**
     * 根据当前用户查询区域选项数据
     *
     * @param currentUserId
     * @return
     */
    List<AreaSelect> findStreetSelectByCurrentUser(@Param("currentUserId") String currentUserId);
}
