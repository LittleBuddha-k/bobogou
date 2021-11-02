package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.Street;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 图标mapper层
 */
@Mapper
public interface StreetMapper extends BaseMapper<Street> {

    /**
     * 用于多选街道级，根据区级的信息来查询不分页的街道级数据
     * @param
     * @return
     */
    List<Street> findNoPageByAreaCode(String areaCode);
}
