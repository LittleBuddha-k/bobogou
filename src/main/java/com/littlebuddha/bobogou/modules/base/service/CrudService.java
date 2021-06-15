package com.littlebuddha.bobogou.modules.base.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.entity.DataEntity;
import com.littlebuddha.bobogou.modules.base.mapper.BaseMapper;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * @author ck
 * @date 2020/7/13 9:23
 */
public abstract class CrudService<E extends DataEntity,M extends BaseMapper<E>> {

    @Autowired
    private M mapper;

    /**
     * 获取单条数据
     * @param id
     * @return
     */
    public E get(String id){
        return mapper.get(id);
    }

    /**
     * 获取单条数据
     * @param entity
     * @return
     */
    public E get(E entity){
        return mapper.get(entity);
    }

    /**
     * 根据条件查询数据列表
     * @param entity
     * @return
     */
    public List<E> findList(E entity){
        return mapper.findList(entity);
    }

    /**
     * 查询分页数据
     * @param page
     * @param entity
     * @return
     */
    public PageInfo<E> findPage(Page<E> page, E entity){
        PageInfo<E> pageInfo = null;
        if(entity.getPageNo() != null && entity.getPageSize() != null){
            entity.setPage(page);
            PageHelper.startPage(entity.getPageNo(),entity.getPageSize());
            List<E> list = mapper.findList(entity);
            pageInfo = new PageInfo<E>(list);
        }
        return pageInfo;
    }

    /**
     * 查询所有数据列表
     * @param entity
     * @return
     */
    public List<E> findAllList(E entity){
        return mapper.findAllList(entity);
    }

    /**
     * 新增数据业务层
     * 包含了update数据操作
     * @param entity
     * @return
     */
    public int save(E entity){
        int row = 0;
        if (entity.getIsNewData()){
            entity.preInsert();
            row = mapper.insert(entity);
        }else{
            entity.preUpdate();
            row = mapper.update(entity);
        }
        return row;
    }

    /**
     * 物理删除业务层
     * @param entity
     * @return
     */
    public int deleteByPhysics(E entity){
        int row = mapper.deleteByPhysics(entity);
        return row;
    }

    /**
     * 逻辑删除业务层
     * @param entity
     * @return
     */
    public int deleteByLogic(E entity){
        int row = mapper.deleteByLogic(entity);
        return row;
    }

    /**
     * 物理删除全部业务层
     * @param entities
     * @return
     */
    public int deleteAllByPhysics(Collection<E> entities){
        int result = 0;
        for (E entity : entities) {
            int row = mapper.deleteByPhysics(entity);
            result = result + row;
        }
        return result;
    }

    /**
     * 逻辑删除全部业务层
     * @param entities
     * @return
     */
    public int deleteAllByLogic(Collection<E> entities){
        int result = 0;
        for (E entity : entities) {
            int row = mapper.deleteByLogic(entity);
            result = result + row;
        }
        return result;
    }

    /**
     * 单条逻辑删除数据的恢复
     * @param entity
     */
    public int recovery(E entity){
        int row = mapper.recovery(entity);
        return row;
    }

    /**
     * 多条逻辑删除数据的恢复
     * @param entities
     */
    public int recovery(Collection<E> entities){
        int recovery = 0;
        for (E entity : entities) {
            recovery = mapper.recovery(entity);
        }
        return recovery;
    }

    /**
     * 查询分页恢复数据
     * @param page
     * @param entity
     * @return
     */
    public PageInfo<E> findRecoveryPage(Page<E> page, E entity){
        PageHelper.startPage(entity.getPageNo(),entity.getPageSize());
        if(entity.getPageNo() != null && entity.getPageSize() != null){
            entity.setPage(page);
        }
        List<E> list = mapper.findRecoveryList(entity);
        PageInfo<E> pageInfo = new PageInfo<E>(list);
        return pageInfo;
    }
}
