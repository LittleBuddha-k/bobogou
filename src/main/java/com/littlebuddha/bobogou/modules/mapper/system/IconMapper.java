package com.littlebuddha.bobogou.modules.mapper.system;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.Role;
import com.littlebuddha.bobogou.modules.entity.system.utils.Icon;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *图标mapper层
 */
@Mapper
public interface IconMapper extends BaseMapper<Icon> {
}
