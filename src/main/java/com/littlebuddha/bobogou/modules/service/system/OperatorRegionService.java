package com.littlebuddha.bobogou.modules.service.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.entity.data.City;
import com.littlebuddha.bobogou.modules.entity.data.Province;
import com.littlebuddha.bobogou.modules.entity.data.Street;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRegion;
import com.littlebuddha.bobogou.modules.mapper.data.AreaMapper;
import com.littlebuddha.bobogou.modules.mapper.data.CityMapper;
import com.littlebuddha.bobogou.modules.mapper.data.ProvinceMapper;
import com.littlebuddha.bobogou.modules.mapper.data.StreetMapper;
import com.littlebuddha.bobogou.modules.mapper.other.CustomerUserMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRegionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("oRegion")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OperatorRegionService extends CrudService<OperatorRegion, OperatorRegionMapper> {

    @Resource
    private OperatorMapper operatorMapper;

    @Resource
    private CustomerUserMapper customerUserMapper;

    @Resource
    private ProvinceMapper provinceMapper;

    @Resource
    private CityMapper cityMapper;

    @Resource
    private AreaMapper areaMapper;

    @Resource
    private StreetMapper streetMapper;

    @Resource
    private OperatorRegionMapper operatorRegionMapper;

    @Override
    public OperatorRegion get(OperatorRegion entity) {
        OperatorRegion operatorRegion = super.get(entity);
        if (operatorRegion != null) {
            if (StringUtils.isNotBlank(operatorRegion.getOperatorId())) {
                Operator select = new Operator();
                select.setId(operatorRegion.getOperatorId());
                operatorRegion.setOperator(operatorMapper.get(select));
            }
            if (StringUtils.isNotBlank(operatorRegion.getUserId())) {
                operatorRegion.setCustomerUser(customerUserMapper.get(new CustomerUser(operatorRegion.getUserId())));
            }
            if (StringUtils.isNotBlank(operatorRegion.getProvinceId())) {
                operatorRegion.setProvince(provinceMapper.get(new Province(operatorRegion.getProvinceId())));
            }
            if (StringUtils.isNotBlank(operatorRegion.getCityId())) {
                operatorRegion.setCity(cityMapper.get(new City(operatorRegion.getCityId())));
            }
            if (StringUtils.isNotBlank(operatorRegion.getDistrictId())) {
                operatorRegion.setArea(areaMapper.get(new Area(operatorRegion.getDistrictId())));
            }
            if (StringUtils.isNotBlank(operatorRegion.getStreetId())) {
                operatorRegion.setStreet(streetMapper.get(new Street(operatorRegion.getStreetId())));
            }
        }
        return operatorRegion;
    }

    @Override
    public List<OperatorRegion> findList(OperatorRegion entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<OperatorRegion> findPage(Page<OperatorRegion> page, OperatorRegion entity) {
        PageInfo<OperatorRegion> page1 = super.findPage(page, entity);
        List<OperatorRegion> list = page1.getList();
        for (OperatorRegion operatorRegion : list) {
            if (operatorRegion != null) {
                if (StringUtils.isNotBlank(operatorRegion.getOperatorId())) {
                    Operator select = new Operator();
                    select.setId(operatorRegion.getOperatorId());
                    operatorRegion.setOperator(operatorMapper.get(select));
                }
                if (StringUtils.isNotBlank(operatorRegion.getUserId())) {
                    operatorRegion.setCustomerUser(customerUserMapper.get(new CustomerUser(operatorRegion.getUserId())));
                }
                if (StringUtils.isNotBlank(operatorRegion.getProvinceId())) {
                    operatorRegion.setProvince(provinceMapper.get(new Province(operatorRegion.getProvinceId())));
                }
                if (StringUtils.isNotBlank(operatorRegion.getCityId())) {
                    operatorRegion.setCity(cityMapper.get(new City(operatorRegion.getCityId())));
                }
                if (StringUtils.isNotBlank(operatorRegion.getDistrictId())) {
                    operatorRegion.setArea(areaMapper.get(new Area(operatorRegion.getDistrictId())));
                }
                if (StringUtils.isNotBlank(operatorRegion.getStreetId())) {
                    operatorRegion.setStreet(streetMapper.get(new Street(operatorRegion.getStreetId())));
                }
            }
        }
        return page1;
    }

    @Override
    public int save(OperatorRegion entity) {
        //设置设置前端用户id
        if (entity != null && StringUtils.isNotBlank(entity.getOperatorId())) {
            Operator operator = operatorMapper.get(entity.getOperatorId());
            String userId = operator.getUserId();
            if (userId != null && StringUtils.isNotBlank(userId)) {
                entity.setUserId(userId);
            } else {
                entity.setUserId("0");
            }
        }
        //int save = super.save(entity);
        int save = 0;
        Map<String,String> provinceMapIC = new HashMap<>();//
        Map<String,String> cityMapCP = new HashMap<>();//cityID--provinceId
        Map<String,String> areaMapAC = new HashMap<>();//areaId--cityID
        Map<String,String> streetMapSA = new HashMap<>();//streetId--areaId
        if (entity.getProvinceId() != null && StringUtils.isNotBlank(entity.getProvinceId())){
            String[] provinceIdArr = entity.getProvinceId().split(",");
            for (String provinceId : provinceIdArr) {
                Province province = provinceMapper.get(provinceId);
                if (province != null) {
                    provinceMapIC.put(provinceId, province.getCode());
                }
            }
        }
        if (entity.getCityId() != null && StringUtils.isNotBlank(entity.getCityId())){
            String[] cityIdArr = entity.getCityId().split(",");
            for (String cityId : cityIdArr) {
                City city = cityMapper.get(cityId);
                if (city != null) {
                    Province province = provinceMapper.getProvinceByCode(city.getProvinceCode());
                    if (province != null) {
                        cityMapCP.put(cityId, province.getId());
                    }
                }
            }
        }
        if (entity.getDistrictId() != null && StringUtils.isNotBlank(entity.getDistrictId())){
            String[] areaIdArr = entity.getDistrictId().split(",");
            for (String areaId : areaIdArr) {
                Area area = areaMapper.get(areaId);
                if (area != null) {
                    City city = cityMapper.getCityByCode(area.getCityCode());
                    if (city != null) {
                        areaMapAC.put(areaId, city.getId());
                    }
                }
            }
        }
        if (entity.getStreetId() != null && StringUtils.isNotBlank(entity.getStreetId())){
            String[] streetIdArr = entity.getStreetId().split(",");
            for (String streetId : streetIdArr) {
                Street street = streetMapper.get(streetId);
                if (street != null) {
                    Area area = areaMapper.getAreaByCode(street.getAreaCode());
                    if (area != null) {
                        streetMapSA.put(streetId, area.getId());
                    }
                }
            }
        }
        if (entity.getStreetId() != null && StringUtils.isNotBlank(entity.getStreetId()) && !"0".equals(entity.getStreetId())){
            String[] streetIdArr = entity.getStreetId().split(",");
            for (String streetId : streetIdArr) {
                String area = streetMapSA.get(streetId);//获取区id
                String city = areaMapAC.get(area);//获取市id
                String province = cityMapCP.get(city);//获取省id
                OperatorRegion insert = new OperatorRegion();
                BeanUtils.copyProperties(entity,insert);
                insert.setStreetId(streetId);
                insert.setDistrictId(area);
                insert.setCityId(city);
                insert.setProvinceId(province);
                if (StringUtils.isBlank(insert.getId())) {
                    insert.preInsert();
                    save = operatorRegionMapper.insert(insert);
                }else {
                    insert.preUpdate();
                    save = operatorRegionMapper.update(insert);
                }
            }
            //街道数据完成过后，就删除掉其上级的区数据
            for (String streetId : streetIdArr) {
                provinceMapIC.remove(cityMapCP.get(areaMapAC.get(streetMapSA.get(streetId))));
                cityMapCP.remove(areaMapAC.get(streetMapSA.get(streetId)));
                areaMapAC.remove(streetMapSA.get(streetId));
            }
        }
        if (entity.getDistrictId() != null && StringUtils.isNotBlank(entity.getDistrictId()) && !"0".equals(entity.getDistrictId())){
            String[] areaIdArr = entity.getDistrictId().split(",");
            for (String areaId : areaIdArr) {
                OperatorRegion insert = new OperatorRegion();
                BeanUtils.copyProperties(entity,insert);
                if (areaMapAC.get(areaId) != null) {
                    String city = areaMapAC.get(areaId);//获取市id
                    String province = cityMapCP.get(city);//获取省id
                    insert.setDistrictId(areaId);
                    insert.setCityId(city);
                    insert.setProvinceId(province);
                    insert.setStreetId("0");
                    if (StringUtils.isBlank(insert.getId())) {
                        insert.preInsert();
                        save = operatorRegionMapper.insert(insert);
                    }else {
                        insert.preUpdate();
                        save = operatorRegionMapper.update(insert);
                    }
                }
            }
            //街道数据完成过后，就删除掉其上级的市数据
            for (String areaId : areaIdArr) {
                provinceMapIC.remove(cityMapCP.get(areaMapAC.get(areaId)));
                cityMapCP.remove(areaMapAC.get(areaId));
            }
        }
        if (entity.getCityId() != null && StringUtils.isNotBlank(entity.getCityId()) && !"0".equals(entity.getCityId())){
            String[] cityIdArr = entity.getCityId().split(",");
            for (String cityId : cityIdArr) {
                OperatorRegion insert = new OperatorRegion();
                BeanUtils.copyProperties(entity,insert);
                if (cityMapCP.get(cityId) != null) {
                    String province = cityMapCP.get(cityId);//获取省id
                    insert.setProvinceId(province);
                    insert.setCityId(cityId);
                    insert.setDistrictId("0");
                    insert.setStreetId("0");
                    if (StringUtils.isBlank(insert.getId())) {
                        insert.preInsert();
                        save = operatorRegionMapper.insert(insert);
                    }else {
                        insert.preUpdate();
                        save = operatorRegionMapper.update(insert);
                    }
                }
            }
            //市数据完成过后，就删除掉其上级的省数据
            for (String cityId : cityIdArr) {
                provinceMapIC.remove(cityMapCP.get(cityId));
            }
        }
        if (entity.getProvinceId() != null && StringUtils.isNotBlank(entity.getProvinceId()) && !"0".equals(entity.getProvinceId())){
            String[] provinceIdArr = entity.getProvinceId().split(",");
            for (String provinceId : provinceIdArr) {
                OperatorRegion insert = new OperatorRegion();
                BeanUtils.copyProperties(entity,insert);
                if (provinceMapIC.get(provinceId) != null) {
                    insert.setProvinceId(provinceId);
                    insert.setCityId("0");
                    insert.setDistrictId("0");
                    insert.setStreetId("0");
                    if (StringUtils.isBlank(insert.getId())) {
                        insert.preInsert();
                        save = operatorRegionMapper.insert(insert);
                    }else {
                        insert.preUpdate();
                        save = operatorRegionMapper.update(insert);
                    }
                }
            }
        }
        return save;
    }

    @Override
    public int deleteByLogic(OperatorRegion entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(OperatorRegion entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<OperatorRegion> findRecoveryPage(Page<OperatorRegion> page, OperatorRegion entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(OperatorRegion entity) {
        return super.recovery(entity);
    }
}
