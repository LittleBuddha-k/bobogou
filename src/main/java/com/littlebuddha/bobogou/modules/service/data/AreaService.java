package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.entity.data.City;
import com.littlebuddha.bobogou.modules.mapper.data.AreaMapper;
import com.littlebuddha.bobogou.modules.mapper.data.CityMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AreaService extends CrudService<Area, AreaMapper> {

    @Resource
    private AreaMapper areaMapper;

    @Resource
    private CityMapper cityMapper;

    @Override
    public Area get(Area entity) {
        return super.get(entity);
    }

    @Override
    public List<Area> findList(Area entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Area> findPage(Page<Area> page, Area entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Area entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Area entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Area entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Area> findRecoveryPage(Page<Area> page, Area entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Area entity) {
        return super.recovery(entity);
    }

    /**
     * 根据市ids查询区级数据
     * @param
     * @return
     */
    public List<Area> noPageByCity(String cityIds) {
        List<Area> result = new ArrayList<>();
        //传递的是省ids，以“，”隔开
        if (StringUtils.isNotBlank(cityIds)){
            String[] cityArr = cityIds.split(",");
            //循环每个省id，通过查找省信息，在使用省code查找市信息
            for (String cityId : cityArr) {
                //获取省信息
                City city = cityMapper.get(cityId);
                //省信息不为null，就通过省code查询city数据
                if (city != null) {
                    List<Area> findNoPageByCity = areaMapper.findNoPageByCityCode(city.getCode());
                    if (findNoPageByCity != null && !findNoPageByCity.isEmpty()){
                        //将结果装入result
                        result.addAll(findNoPageByCity);
                    }
                }
            }
        }
        return result;
    }
}
