package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.ListUtils;
import com.littlebuddha.bobogou.common.utils.PageUtil;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.common.utils.UserUtils;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.*;
import com.littlebuddha.bobogou.modules.entity.data.RegionGoods;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.entity.system.OperatorRegion;
import com.littlebuddha.bobogou.modules.mapper.data.*;
import com.littlebuddha.bobogou.modules.mapper.data.RegionGoodsMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorRegionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RegionGoodsService extends CrudService<RegionGoods, RegionGoodsMapper> {

    @Resource
    private RegionGoodsMapper regionGoodsMapper;

    @Resource
    private OperatorMapper operatorMapper;

    @Resource
    private GoodsMapper goodsMapper;

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
    public RegionGoods get(RegionGoods entity) {
        RegionGoods regionGoods = super.get(entity);
        if (regionGoods != null && StringUtils.isNotBlank(regionGoods.getGoodsId())){
            Goods goods = goodsMapper.get(new Goods(regionGoods.getGoodsId()));
            regionGoods.setMedicine(goods);
        }
        return regionGoods;
    }

    @Override
    public List<RegionGoods> findList(RegionGoods entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<RegionGoods> findPage(Page<RegionGoods> page, RegionGoods entity) {
        //封装的结果集
        List<RegionGoods> result = new ArrayList<>();
        //获取当前用户区域
        Operator currentUser = UserUtils.getCurrentUser();
        entity.setCurrentUser(currentUser);
        OperatorRegion operatorRegion = new OperatorRegion();
        operatorRegion.setOperatorId(currentUser.getId());
        //查询当前用户的所有区域list
        List<OperatorRegion> operatorRegionList = operatorRegionMapper.getOperatorRegionByCurrentUser(operatorRegion);
        //查询封装结果
        PageInfo<RegionGoods> pageInfo = null;
        //根据当前用户区域查询对应分配区域内的商品数据
        if (entity.getPageNo() != null && entity.getPageSize() != null) {
            entity.setPage(page);
            if (currentUser != null && "1".equals(currentUser.getId())){//超级管理员查询所有数据
                result = regionGoodsMapper.findList(entity);
            }else {
                for (OperatorRegion region : operatorRegionList) {//按照区域来进行查询
                    if (region != null) {
                        entity.setProvinceId(region.getProvinceId());
                        entity.setCityId(region.getCityId());
                        entity.setDistrictId(region.getDistrictId());
                        entity.setStreetId(region.getStreetId());
                        List<RegionGoods> royaltyRecordList = regionGoodsMapper.findList(entity);
                        if (royaltyRecordList != null) {
                            result.addAll(royaltyRecordList);
                        }
                    }
                }
            }
            //去重
            result = ListUtils.removeDuplicateRegionGoods(result);
            //分页数据
            List list = PageUtil.startPage(result, entity.getPageNo(), entity.getPageSize());
            //组装结果
            if (result != null && !result.isEmpty()) {
                pageInfo = new PageInfo<RegionGoods>();
                pageInfo.setList(list);
                pageInfo.setTotal(result.size());
            } else {
                pageInfo = new PageInfo<>();
            }
        }
        return pageInfo;
    }

    @Override
    @Transactional
    public int save(RegionGoods entity) {
        /*entity.setIdType("AUTO");
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
                RegionGoods insert = new RegionGoods();
                BeanUtils.copyProperties(entity,insert);
                insert.setStreetId(streetId);
                insert.setDistrictId(area);
                insert.setCityId(city);
                insert.setProvinceId(province);
                if (StringUtils.isBlank(insert.getId())) {
                    insert.preInsert();
                    save = regionGoodsMapper.insert(insert);
                }else {
                    insert.preUpdate();
                    save = regionGoodsMapper.update(insert);
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
                RegionGoods insert = new RegionGoods();
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
                        save = regionGoodsMapper.insert(insert);
                    }else {
                        insert.preUpdate();
                        save = regionGoodsMapper.update(insert);
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
                RegionGoods insert = new RegionGoods();
                BeanUtils.copyProperties(entity,insert);
                if (cityMapCP.get(cityId) != null) {
                    String province = cityMapCP.get(cityId);//获取省id
                    insert.setProvinceId(province);
                    insert.setCityId(cityId);
                    insert.setDistrictId("0");
                    insert.setStreetId("0");
                    if (StringUtils.isBlank(insert.getId())) {
                        insert.preInsert();
                        save = regionGoodsMapper.insert(insert);
                    }else {
                        insert.preUpdate();
                        save = regionGoodsMapper.update(insert);
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
                RegionGoods insert = new RegionGoods();
                BeanUtils.copyProperties(entity,insert);
                if (provinceMapIC.get(provinceId) != null) {
                    insert.setProvinceId(provinceId);
                    insert.setCityId("0");
                    insert.setDistrictId("0");
                    insert.setStreetId("0");
                    if (StringUtils.isBlank(insert.getId())) {
                        insert.preInsert();
                        save = regionGoodsMapper.insert(insert);
                    }else {
                        insert.preUpdate();
                        save = regionGoodsMapper.update(insert);
                    }
                }
            }
        }*/
        int save = super.save(entity);
        return save;
    }

    @Override
    public int deleteByLogic(RegionGoods entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(RegionGoods entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<RegionGoods> findRecoveryPage(Page<RegionGoods> page, RegionGoods entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(RegionGoods entity) {
        return super.recovery(entity);
    }
}
