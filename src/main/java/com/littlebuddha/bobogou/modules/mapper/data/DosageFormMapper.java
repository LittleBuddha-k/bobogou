package com.littlebuddha.bobogou.modules.mapper.data;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.data.utils.DosageForm;
import com.littlebuddha.bobogou.modules.entity.data.utils.ShelfLife;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 图标mapper层
 */
@Mapper
public interface DosageFormMapper extends BaseMapper<DosageForm> {

    /**
     * 保质期下拉数据
     * @return
     */
    List<ShelfLife> findShelfLifeList();
}
