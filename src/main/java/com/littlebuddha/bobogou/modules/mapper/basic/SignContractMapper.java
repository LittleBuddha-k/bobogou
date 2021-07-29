package com.littlebuddha.bobogou.modules.mapper.basic;


import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.basic.SignContract;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 合同mapper层
 */
@Mapper
public interface SignContractMapper extends BaseMapper<SignContract> {

    /**
     * 查找当前角色审核数据
     * @param entity
     * @return
     */
    List<SignContract> findTodoList(SignContract entity);
}
