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

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RegionGoodsService extends CrudService<RegionGoods, RegionGoodsMapper> {

    @Autowired
    private RegionGoodsMapper regionGoodsMapper;

    @Autowired
    private OperatorMapper operatorMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public RegionGoods get(RegionGoods entity) {
        return super.get(entity);
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
        int row = 0;
        RegionGoods regionGoods = null;
        entity.setIdType("AUTO");
        if (entity != null && entity.getRegionGoodsList() != null && entity.getRegionGoodsList().size() > 0){
            //所选商品列表
            List<RegionGoods> regionGoodsList = entity.getRegionGoodsList();
            for (RegionGoods goods : regionGoodsList) {
                if (RegionGoods.DEL_FLAG_NORMAL.equals(goods.getIsDeleted())) {
                    if (StringUtils.isBlank(goods.getId())) {
                        regionGoods = new RegionGoods();
                        BeanUtils.copyProperties(entity, regionGoods);
                        regionGoods.setGoodsId(goods.getGoodsId());
                        regionGoods.setAmount(goods.getAmount());
                        regionGoods.setSalesVolume(goods.getSalesVolume());
                        regionGoods.setIsMarket(goods.getIsMarket());
                        //插入数据的同时对商品库存进行操作
                        Goods stock = goodsMapper.getStock(new Goods(goods.getGoodsId()));
                        if(stock != null && stock.getStockAmount() < goods.getAmount()){
                            //如果分配数量大于库存量
                            return row = -1;
                        }else if (stock != null && stock.getStockAmount() >= goods.getAmount()){
                            Goods goodsStock = new Goods();
                            goodsStock.setId(goods.getGoodsId());
                            goodsStock.setUsedAmount(goods.getAmount());
                            goodsStock.setStockAmount(goods.getAmount());
                            goodsMapper.updateStock(goodsStock);
                            regionGoods.preInsert();
                            row = regionGoodsMapper.insert(regionGoods);
                        }
                    }else {
                        regionGoods = new RegionGoods();
                        BeanUtils.copyProperties(entity, regionGoods);
                        regionGoods.setGoodsId(goods.getGoodsId());
                        regionGoods.setAmount(goods.getAmount());
                        regionGoods.setSalesVolume(goods.getSalesVolume());
                        regionGoods.setIsMarket(goods.getIsMarket());
                        //先将本条商品区域的分配数量恢复到原本的样子
                        RegionGoods startingValue = regionGoodsMapper.get(regionGoods);
                        if (startingValue != null){
                            Goods startingStock = new Goods();
                            startingStock.setUsedAmount(startingValue.getAmount());
                            startingStock.setStockAmount(startingValue.getAmount());
                            startingStock.setId(goods.getGoodsId());
                            goodsMapper.recoveryStock(startingStock);
                        }else {

                        }
                        //更新数据的同时对商品库存进行操作
                        Goods stock = goodsMapper.getStock(new Goods(goods.getGoodsId()));
                        if(stock != null && stock.getStockAmount() < goods.getAmount()){
                            //如果分配数量大于库存量
                            return row = -1;
                        }else if (stock != null && stock.getStockAmount() >= goods.getAmount()){
                            Goods goodsStock = new Goods();
                            goodsStock.setId(goods.getGoodsId());
                            goodsStock.setUsedAmount(goods.getAmount());
                            goodsStock.setStockAmount(goods.getAmount());
                            goodsMapper.updateStock(goodsStock);
                            regionGoods.preUpdate();
                            row = regionGoodsMapper.update(regionGoods);
                        }
                    }
                }else {
                    regionGoodsMapper.deleteByLogic(goods);
                }
            }
        }
        return row;
    }

    public int updateIsMarket(RegionGoods regionGoods){
        int row = regionGoodsMapper.updateIsMarket(regionGoods);
        return row;
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
