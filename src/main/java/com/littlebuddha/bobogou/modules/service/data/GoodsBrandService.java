package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.GoodsBrand;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsBrandMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品品牌规格service层
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GoodsBrandService extends CrudService<GoodsBrand, GoodsBrandMapper> {

    @Override
    public GoodsBrand get(GoodsBrand entity) {
        return super.get(entity);
    }

    @Override
    public List<GoodsBrand> findList(GoodsBrand entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<GoodsBrand> findPage(Page<GoodsBrand> page, GoodsBrand entity) {
        if(entity != null){
            String brandName = StringUtils.deleteWhitespace(entity.getBrandName());
            entity.setBrandName(brandName);
            String classifyName = StringUtils.deleteWhitespace(entity.getClassifyName());
            entity.setClassifyName(classifyName);
        }
        return super.findPage(page, entity);
    }

    @Override
    public int save(GoodsBrand entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(GoodsBrand entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(GoodsBrand entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<GoodsBrand> findRecoveryPage(Page<GoodsBrand> page, GoodsBrand entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(GoodsBrand entity) {
        return super.recovery(entity);
    }
}
