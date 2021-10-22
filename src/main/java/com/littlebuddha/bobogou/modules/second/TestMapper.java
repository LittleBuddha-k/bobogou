package com.littlebuddha.bobogou.modules.second;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *厂商mapper层
 */
@Mapper
public interface TestMapper extends BaseMapper<TerminalInf> {

    List<TerminalInf> all();
}
