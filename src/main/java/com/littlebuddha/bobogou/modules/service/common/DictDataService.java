package com.littlebuddha.bobogou.modules.service.common;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.mapper.common.DictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DictDataService extends CrudService<DictData, DictDataMapper> {

    @Autowired
    private DictDataMapper dictDataMapper;

    @Autowired
    private GlobalSetting globalSetting;

    @Override
    public DictData get(DictData entity) {
        DictData dictData = dictDataMapper.get(entity);
        return dictData;
    }

    public DictData getByValue(DictData entity) {
        DictData dictData = dictDataMapper.getByValue(entity);
        return dictData;
    }

    public DictData getByName(DictData entity) {
        DictData dictData = dictDataMapper.getByName(entity);
        return dictData;
    }

    /**
     * 根据type以map方式存放value、name属性
     * @return
     */
    public Map<String,String> getMap(String type){
        DictData entity = new DictData();
        entity.setType(type);
        List<DictData> list = super.findList(entity);
        Map<String,String> map = new HashMap<>();
        for (DictData dictData : list) {
            map.put(dictData.getValue(),dictData.getName());
        }
        return map;
    }

    @Override
    public List<DictData> findList(DictData entity) {
        List<DictData> list = super.findList(entity);
        return list;
    }

    @Override
    public PageInfo<DictData> findPage(Page<DictData> page, DictData entity) {
        PageInfo<DictData> page1 = super.findPage(page, entity);
        return page1;
    }

    @Override
    public int save(DictData entity) {
        int save = super.save(entity);
        return save;
    }

    @Override
    public int deleteByLogic(DictData entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(DictData entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<DictData> findRecoveryPage(Page<DictData> page, DictData entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(DictData entity) {
        return super.recovery(entity);
    }
}
