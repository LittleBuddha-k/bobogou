package com.littlebuddha.bobogou.modules.mapper.system;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRegion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 图标mapper层
 */
@Mapper
public interface OperatorRegionMapper extends BaseMapper<OperatorRegion> {

    /**
     * 根据用户id查询
     * @param operatorRegion
     */
    List<OperatorRegion> getOperatorRegionByCurrentUser(OperatorRegion operatorRegion);

    /**
     * 根据当前用户查询去重省级id的数据集合
     * @param operatorRegion
     * @return
     */
    List<OperatorRegion> findByCurrentUserProvinceDistinct(OperatorRegion operatorRegion);

    /**
     * 根据当前用户查询去重街道级id的数据集合
     * @param operatorRegion
     * @return
     */
    List<OperatorRegion> findByCurrentUserCityDistinct(OperatorRegion operatorRegion);

    /**
     * 根据当前用户查询去重街道级id的数据集合
     * @param operatorRegion
     * @return
     */
    List<OperatorRegion> findByCurrentUserAreaDistinct(OperatorRegion operatorRegion);

    /**
     * 根据当前用户查询去重街道级id的数据集合
     * @param operatorRegion
     * @return
     */
    List<OperatorRegion> findByCurrentUserStreetDistinct(OperatorRegion operatorRegion);
}
