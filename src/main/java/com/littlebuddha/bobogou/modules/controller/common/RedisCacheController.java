package com.littlebuddha.bobogou.modules.controller.common;

import com.littlebuddha.bobogou.common.utils.ListUtils;
import com.littlebuddha.bobogou.common.utils.RedisUtils;
import com.littlebuddha.bobogou.modules.entity.common.DictData;
import com.littlebuddha.bobogou.modules.service.common.DictDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User: LittleBuddha-k
 * Date: 2021-11-17
 */
@RestController
@RequestMapping("/redisCache")
public class RedisCacheController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private DictDataService dictDataService;

    @GetMapping("/get")
    public DictData get(String type,String value){
        DictData name = new DictData();
        if (StringUtils.isNotBlank(type)) {
            List<DictData> dictData = (List<DictData>)redisUtils.get(type);
            if (dictData != null && !dictData.isEmpty()){
                for (DictData dictDatum : dictData) {
                    if (dictDatum != null && StringUtils.isNotBlank(value)){
                        if (value.equals(dictDatum.getValue())){
                            name = dictDatum;
                        }
                    }
                }
            }else {
                List<DictData> list = dictDataService.findList(new DictData());
                List<DictData> keyList = ListUtils.removeDuplicateDict(list);
                for (DictData entity : keyList) {
                    List<DictData> byType = dictDataService.findByType(type);
                    entity.setChildren(byType);
                    redisUtils.set(type,entity.getChildren());
                }
                List<DictData> find = (List<DictData>)redisUtils.get(type);
                for (DictData data : find) {
                    if (data != null && value.equals(data.getValue())){
                        name = data;
                    }
                }
            }
        }
        return name;
    }
}