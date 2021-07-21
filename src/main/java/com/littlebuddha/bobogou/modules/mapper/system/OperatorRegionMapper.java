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
}
