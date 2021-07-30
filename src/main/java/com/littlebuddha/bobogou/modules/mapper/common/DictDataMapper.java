package com.littlebuddha.bobogou.modules.mapper.common;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import org.apache.ibatis.annotations.Mapper;

/**
 * 词典mapper层
 */
@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {

    /**
     * 通过值查名字
     * @param dictData
     * @return
     */
    DictData getByValue(DictData dictData);

    /**
     * 通过名字查值
     * @param dictData
     * @return
     */
    DictData getByName(DictData dictData);
}
