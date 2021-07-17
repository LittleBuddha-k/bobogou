package com.littlebuddha.bobogou.modules.mapper.other;

import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.other.ActEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActMapper extends BaseMapper<ActEntity> {

    ActEntity<ActEntity> get(ActEntity e);

    List<ActEntity> findList(ActEntity e);

    int save(ActEntity e);

    int update(ActEntity e);
}
