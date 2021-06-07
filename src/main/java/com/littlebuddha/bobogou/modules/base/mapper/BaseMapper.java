package com.littlebuddha.bobogou.modules.base.mapper;

import java.util.List;

public interface BaseMapper<E> {

    /**
     * 新增数据
     * @param entity
     * @return
     */
    int insert(E entity);

    /**
     * 物理删除
     * @param entity
     * @return
     */
    int deleteByPhysics(E entity);

    /**
     * 逻辑删除
     * @param entity
     * @return
     */
    int deleteByLogic(E entity);

    /**
     * 修改
     * @param entity
     * @return
     */
    int update(E entity);

    /**
     * 获取单条数据
     * @param id
     * @return
     */
    E get(String id);

    /**
     * 获取单条数据
     * @param entity
     * @return
     */
    E get(E entity);

    /**
     * 通过名称查询
     * @param entity
     * @return
     */
    List<E> findByName(E entity);

    /**
     * 有条件的查询数据列表
     * @param entity
     * @return
     */
    List<E> findList(E entity);

    /**
     * 有条件的查询数据列表
     * @param entity
     * @return
     */
    List<E> findRecoveryList(E entity);

    /**
     * 查询所有数据
     * @return
     */
    List<E> findAllList(E entity);

    /**
     * 逻辑删除的恢复
     * @param id
     * @return
     */
    int recovery(String id);

    /**
     * 逻辑删除的恢复
     * @param entity
     * @return
     */
    int recovery(E entity);

    /**
     * 获取数据总条数
     * @param entity
     * @param <E>
     * @return
     */
    int getTotalCount(E entity);
}
