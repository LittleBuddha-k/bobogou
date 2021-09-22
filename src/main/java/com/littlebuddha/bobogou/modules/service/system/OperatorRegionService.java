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
import java.util.List;

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
            if (StringUtils.isNotBlank(operatorRegion.getAreaId())) {
                operatorRegion.setArea(areaMapper.get(new Area(operatorRegion.getAreaId())));
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

    /**
     * 查询当前用户的省数据
     *
     * @return
     */
    public List<Province> currentUserProvince() {
        Operator currentUser = UserUtils.getCurrentUser();
        //通过当前用户查询当前用户的省级数据
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        List<OperatorRegion> operatorRegionByCurrentUserDistinct = operatorRegionMapper.findByCurrentUserProvinceDistinct(operatorRegion);
        //返回筛选的省级结果集
        List<Province> result = new ArrayList<>();
        if (operatorRegionByCurrentUserDistinct != null && !operatorRegionByCurrentUserDistinct.isEmpty()) {
            for (OperatorRegion region : operatorRegionByCurrentUserDistinct) {
                if (region != null && StringUtils.isNotBlank(region.getProvinceId())) {
                    String provinceId = region.getProvinceId();
                    Province province = provinceMapper.get(provinceId);
                    result.add(province);
                }
            }
        }
        //根据其所有省级id数据查询省级数据
        return result;
    }

    /**
     * 查询当前用户的市数据
     *
     * @return
     */
    public List<City> currentUserCity() {
        Operator currentUser = UserUtils.getCurrentUser();
        //通过当前用户查询当前用户的省级数据
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        List<OperatorRegion> currentUserCityDistinct = operatorRegionMapper.findByCurrentUserCityDistinct(operatorRegion);
        //返回筛选的市级结果集
        List<City> result = new ArrayList<>();
        if (currentUserCityDistinct != null && !currentUserCityDistinct.isEmpty()) {
            for (OperatorRegion region : currentUserCityDistinct) {
                if (region != null && StringUtils.isNotBlank(region.getCityId())) {
                    String cityId = region.getCityId();
                    City city = cityMapper.get(cityId);
                    result.add(city);
                }
            }
        }
        //根据其所有省级id数据查询省级数据
        return result;
    }

    /**
     * 查询当前用户的区数据
     *
     * @return
     */
    public List<Area> currentUserArea() {
        Operator currentUser = UserUtils.getCurrentUser();
        //通过当前用户查询当前用户的省级数据
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        List<OperatorRegion> currentUserAreaDistinct = operatorRegionMapper.findByCurrentUserAreaDistinct(operatorRegion);
        //返回筛选的省级结果集
        List<Area> result = new ArrayList<>();
        if (currentUserAreaDistinct != null && !currentUserAreaDistinct.isEmpty()) {
            for (OperatorRegion region : currentUserAreaDistinct) {
                if (region != null && StringUtils.isNotBlank(region.getAreaId())) {
                    String areaId = region.getAreaId();
                    Area area = areaMapper.get(areaId);
                    result.add(area);
                }
            }
        }
        //根据其所有省级id数据查询省级数据
        return result;
    }

    /**
     * 查询当前用户的街道数据
     *
     * @return
     */
    public List<Street> currentUserStreet() {
        Operator currentUser = UserUtils.getCurrentUser();
        //通过当前用户查询当前用户的省级数据
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        List<OperatorRegion> currentUserStreetDistinct = operatorRegionMapper.findByCurrentUserStreetDistinct(operatorRegion);
        //返回筛选的省级结果集
        List<Street> result = new ArrayList<>();
        if (currentUserStreetDistinct != null && !currentUserStreetDistinct.isEmpty()) {
            for (OperatorRegion region : currentUserStreetDistinct) {
                if (region != null && StringUtils.isNotBlank(region.getStreetId())) {
                    String streetId = region.getStreetId();
                    Street street = streetMapper.get(streetId);
                    result.add(street);
                }
            }
        }
        //根据其所有省级id数据查询省级数据
        return result;
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
                if (StringUtils.isNotBlank(operatorRegion.getAreaId())) {
                    operatorRegion.setArea(areaMapper.get(new Area(operatorRegion.getAreaId())));
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
        if (entity != null && StringUtils.isBlank(entity.getId())) {
            //新增
            if (StringUtils.isNotBlank(entity.getStreetId())) {
                String streetId = entity.getStreetId();
                String[] street = streetId.split(",");
                for (String streetsId : street) {
                    OperatorRegion operatorRegion = new OperatorRegion();
                    BeanUtils.copyProperties(entity, operatorRegion);
                    operatorRegion.setIdType("AUTO");
                    operatorRegion.setStreetId(streetsId);
                    operatorRegion.preInsert();
                    save = operatorRegionMapper.insert(operatorRegion);
                }
            } else {
                entity.preInsert();
                save = operatorRegionMapper.insert(entity);
            }
        } else {
            //修改
            if (StringUtils.isNotBlank(entity.getStreetId())) {
                String streetId = entity.getStreetId();
                String[] street = streetId.split(",");
                for (String streetsId : street) {
                    OperatorRegion operatorRegion = new OperatorRegion();
                    BeanUtils.copyProperties(entity, operatorRegion);
                    operatorRegion.setIdType("AUTO");
                    operatorRegion.setStreetId(streetsId);
                    operatorRegion.preUpdate();
                    save = operatorRegionMapper.update(operatorRegion);
                }
            } else {
                entity.preUpdate();
                save = operatorRegionMapper.update(entity);
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
