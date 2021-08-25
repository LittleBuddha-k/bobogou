package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.utils.Result;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.Goods;
import com.littlebuddha.bobogou.modules.entity.data.RegionGoods;
import com.littlebuddha.bobogou.modules.entity.data.RegionGoods;
import com.littlebuddha.bobogou.modules.entity.system.Operator;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsMapper;
import com.littlebuddha.bobogou.modules.mapper.data.RegionGoodsMapper;
import com.littlebuddha.bobogou.modules.mapper.data.RegionGoodsMapper;
import com.littlebuddha.bobogou.modules.mapper.system.OperatorMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RegionGoodsService extends CrudService<RegionGoods, RegionGoodsMapper> {

    @Resource
    private RegionGoodsMapper regionGoodsMapper;

    @Resource
    private OperatorMapper operatorMapper;

    @Resource
    private GoodsMapper goodsMapper;

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
        PageInfo<RegionGoods> pageRegionGoods = super.findPage(page, entity);
        List<RegionGoods> list = pageRegionGoods.getList();
        for (RegionGoods regionGoods : list) {
            Operator operator = new Operator();
            operator.setId(regionGoods.getAccountId());
            Operator updateBy = operatorMapper.get(operator);
            regionGoods.setUpdateBy(updateBy);
        }
        return pageRegionGoods;
    }

    @Override
    @Transactional
    public int save(RegionGoods entity) {
        entity.setIdType("AUTO");
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
