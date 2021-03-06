package com.littlebuddha.bobogou.modules.service.data;

import com.littlebuddha.bobogou.common.utils.RedisUtils;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.entity.data.utils.AreaSelect;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.mapper.data.AreaMapper;
import com.littlebuddha.bobogou.modules.mapper.data.AreaSelectMapper;
import com.littlebuddha.bobogou.modules.mapper.data.CityMapper;
import com.littlebuddha.bobogou.modules.mapper.data.StreetMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AreaSelectService extends CrudService<Area, AreaMapper> {

    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private OperatorRegionMapper operatorRegionMapper;

    @Resource
    private AreaSelectMapper areaSelectMapper;

    /**
     * 查询当前用户的所有区域
     *
     * @return
     */
    public List<AreaSelect> findCurrentUserAreas() {
        Operator currentUser = UserUtils.getCurrentUser();
        String currentUserId = currentUser.getId();
        //定义一个结果集
        List<AreaSelect> result = new ArrayList<>();
        //查询一级
        List<AreaSelect> provinceList = new ArrayList<>();
        if(currentUser != null && "1".equals(currentUserId)) {//如果是admin查询所有省份
            provinceList = operatorRegionMapper.findProvinceSelectIfAdmin();
        }else {//如果不是则按照所设置的区域查找
            provinceList = operatorRegionMapper.findProvinceSelectByCurrentUser(currentUserId);
        }
        HashMap<String, AreaSelect> provinceMap = new HashMap<>();
        if (provinceList != null && !provinceList.isEmpty()) {
            for (AreaSelect areaSelect : provinceList) {
                provinceMap.put(areaSelect.getCode(), areaSelect);
            }
        }
        //查询二级
        List<AreaSelect> cityList = operatorRegionMapper.findCitySelectByCurrentUser(currentUserId);
        HashMap<String, String> cityMap = new HashMap<>();
        if (cityList != null && !cityList.isEmpty()) {
            for (AreaSelect areaSelect : cityList) {
                cityMap.put(areaSelect.getParentCode(), areaSelect.getCode());
            }
        }
        //查询三级
        List<AreaSelect> areaList = operatorRegionMapper.findAreaSelectByCurrentUser(currentUserId);
        HashMap<String, String> areaMap = new HashMap<>();
        if (areaList != null && !areaList.isEmpty()) {
            for (AreaSelect areaSelect : areaList) {
                areaMap.put(areaSelect.getParentCode(), areaSelect.getCode());
            }
        }
        //查询四级
        List<AreaSelect> streetList = operatorRegionMapper.findStreetSelectByCurrentUser(currentUserId);
        HashMap<String, String> streetMap = new HashMap<>();
        if (streetList != null && !streetList.isEmpty()) {
            for (AreaSelect areaSelect : streetList) {
                streetMap.put(areaSelect.getParentCode(), areaSelect.getCode());
            }
        }
        //給一级数据设置子数据list
        if (provinceList != null && !provinceList.isEmpty()) {
            for (int i = 0; i < provinceList.size(); i++) {
                if (cityMap.get(provinceList.get(i).getCode()) != null) {//其下设置了市级相关的选项
                    for (int j = 0; j < cityList.size(); j++) {
                        String parentCode = cityList.get(j).getParentCode();
                        String code = provinceList.get(i).getCode();
                        if (parentCode.equals(code)) {
                            AreaSelect areaSelect = provinceList.get(i);
                            List<AreaSelect> children = areaSelect.getChildren();
                            AreaSelect e = cityList.get(j);
                            children.add(e);
                        }
                    }
                }else {//其下没有设置市级相关的数据则查询其所有市
                    List<AreaSelect> citySelectByProvinceCode = areaSelectMapper.findCitySelectByProvinceCode(provinceList.get(i).getCode());
                    AreaSelect areaSelect = provinceList.get(i);
                    List<AreaSelect> children = areaSelect.getChildren();
                    children.addAll(citySelectByProvinceCode);
                }
            }
        }
        //給二级数据设置子数据list
        if (cityList != null && !cityList.isEmpty()) {
            for (int i = 0; i < cityList.size(); i++) {
                if (areaMap.get(cityList.get(i).getCode()) != null) {
                    for (int j = 0; j < areaList.size(); j++) {
                        if (cityList.get(i).getCode().equals(areaList.get(j).getParentCode())) {
                            AreaSelect areaSelect = cityList.get(i);
                            List<AreaSelect> children = areaSelect.getChildren();
                            AreaSelect e = areaList.get(j);
                            children.add(e);
                        }
                    }
                }else {//其下没有设置区级相关的数据则查询其所有区
                    List<AreaSelect> selectByProvinceCode = areaSelectMapper.findAreaSelectByProvinceCode(cityList.get(i).getCode());
                    AreaSelect areaSelect = cityList.get(i);
                    List<AreaSelect> children = areaSelect.getChildren();
                    children.addAll(selectByProvinceCode);
                }
            }
        }
        //給三级数据设置子数据list
        if (areaList != null && !areaList.isEmpty()) {
            for (int i = 0; i < areaList.size(); i++) {
                if (streetMap.get(areaList.get(i).getCode()) != null) {
                    for (int j = 0; j < streetList.size(); j++) {
                        if (areaList.get(i).getCode().equals(streetList.get(j).getParentCode())) {
                            AreaSelect areaSelect = areaList.get(i);
                            List<AreaSelect> children = areaSelect.getChildren();
                            AreaSelect e = streetList.get(j);
                            children.add(e);
                        }
                    }
                }else {//其下没有设置街道级相关的数据则查询其所有街道
                    List<AreaSelect> streetSelectByProvinceCode = areaSelectMapper.findStreetSelectByProvinceCode(areaList.get(i).getCode());
                    AreaSelect areaSelect = areaList.get(i);
                    List<AreaSelect> children = areaSelect.getChildren();
                    children.addAll(streetSelectByProvinceCode);
                }
            }
        }
        if (provinceList == null || provinceList.isEmpty()) {
            provinceList = new ArrayList<>();
        }
        return provinceList;
    }
}
