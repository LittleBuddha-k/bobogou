package com.littlebuddha.bobogou.modules.mapper.basic;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.basic.Qualification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 合同mapper层
 */
@Mapper
public interface QualificationMapper extends BaseMapper<Qualification> {

    /**
     * 查找当前角色审核数据
     * @param entity
     * @return
     */
    List<Qualification> findTodoList(Qualification entity);

    /**
     * 查询可下载数据--对应已通过审核的数据
     * @param entity
     * @return
     */
    List<Qualification> findDownloadDataPage(Qualification entity);

    /**
     * 更改审核状态
     * @param qualification
     * @return
     */
    int updateQualificationAct(Qualification qualification);
}
