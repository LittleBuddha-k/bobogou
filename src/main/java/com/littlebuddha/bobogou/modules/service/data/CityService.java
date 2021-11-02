package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.City;
import com.littlebuddha.bobogou.modules.entity.data.Province;
import com.littlebuddha.bobogou.modules.mapper.data.CityMapper;
import com.littlebuddha.bobogou.modules.mapper.data.ProvinceMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CityService extends CrudService<City, CityMapper> {

    @Resource
    private CityMapper cityMapper;

    @Resource
    private ProvinceMapper provinceMapper;

    @Override
    public City get(City entity) {
        return super.get(entity);
    }

    @Override
    public List<City> findList(City entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<City> findPage(Page<City> page, City entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(City entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(City entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(City entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<City> findRecoveryPage(Page<City> page, City entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(City entity) {
        return super.recovery(entity);
    }

    /**
     * 用于多选市级，根据省级的信息来查询不分页的市级数据
     * @param provinceIds
     * @return
     */
    public List<City> findNoPageByProvinceCode(String provinceIds) {
        List<City> result = new ArrayList<>();
        //传递的是省ids，以“，”隔开
        if (StringUtils.isNotBlank(provinceIds)){
            String[] provinceArr = provinceIds.split(",");
            //循环每个省id，通过查找省信息，在使用省code查找市信息
            for (String provinceId : provinceArr) {
                //获取省信息
                Province province = provinceMapper.get(provinceId);
                //省信息不为null，就通过省code查询city数据
                if (province != null) {
                    List<City> findNoPageByProvince = cityMapper.findNoPageByProvinceCode(province.getCode());
                    if (findNoPageByProvince != null && !findNoPageByProvince.isEmpty()){
                        //将结果装入result
                        result.addAll(findNoPageByProvince);
                    }
                }
            }
        }
        return result;
    }
}
