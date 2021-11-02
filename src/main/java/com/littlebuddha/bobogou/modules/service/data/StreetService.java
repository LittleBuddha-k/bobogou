package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Area;
import com.littlebuddha.bobogou.modules.entity.data.Street;
import com.littlebuddha.bobogou.modules.mapper.data.AreaMapper;
import com.littlebuddha.bobogou.modules.mapper.data.StreetMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StreetService extends CrudService<Street, StreetMapper> {

    @Resource
    private AreaMapper areaMapper;

    @Resource
    private StreetMapper streetMapper;

    @Override
    public Street get(Street entity) {
        return super.get(entity);
    }

    @Override
    public List<Street> findList(Street entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Street> findPage(Page<Street> page, Street entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Street entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Street entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Street entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Street> findRecoveryPage(Page<Street> page, Street entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Street entity) {
        return super.recovery(entity);
    }

    /**
     * 根据区ids查询街道数据
     * @param areaIds
     * @return
     */
    public List<Street> noPageByArea(String areaIds) {
        List<Street> result = new ArrayList<>();
        //传递的是省ids，以“，”隔开
        if (StringUtils.isNotBlank(areaIds)){
            String[] areaArr = areaIds.split(",");
            //循环每个省id，通过查找省信息，在使用省code查找市信息
            for (String areaId : areaArr) {
                //获取省信息
                Area area = areaMapper.get(areaId);
                //省信息不为null，就通过省code查询city数据
                if (area != null) {
                    List<Street> findNoPageByArea = streetMapper.findNoPageByAreaCode(area.getCode());
                    if (findNoPageByArea != null && !findNoPageByArea.isEmpty()){
                        //将结果装入result
                        result.addAll(findNoPageByArea);
                    }
                }
            }
        }
        return result;
    }
}
