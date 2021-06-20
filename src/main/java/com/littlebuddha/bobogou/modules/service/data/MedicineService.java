package com.littlebuddha.bobogou.modules.service.data;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.data.GoodsInfo;
import com.littlebuddha.bobogou.modules.entity.data.Medicine;
import com.littlebuddha.bobogou.modules.mapper.data.GoodsInfoMapper;
import com.littlebuddha.bobogou.modules.mapper.data.MedicineMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MedicineService extends CrudService<Medicine, MedicineMapper> {

    @Autowired
    private MedicineMapper medicineMapper;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public Medicine get(Medicine entity) {
        return super.get(entity);
    }

    @Override
    public List<Medicine> findList(Medicine entity) {
        return super.findList(entity);
    }

    @Override
    public PageInfo<Medicine> findPage(Page<Medicine> page, Medicine entity) {
        return super.findPage(page, entity);
    }

    @Override
    public int save(Medicine entity) {
        int save = super.save(entity);
        GoodsInfo goodsInfo = entity.getGoodsInfo();
        if (goodsInfo != null && StringUtils.isNotBlank(goodsInfo.getContent())) {
            goodsInfo.setMedicine(entity);
            //一条商品对应一条详情
            List<GoodsInfo> list = goodsInfoMapper.findList(goodsInfo);
            if (GoodsInfo.DEL_FLAG_NORMAL.equals(goodsInfo.getIsDeleted())) {
                //即是此条商品还未建立详情信息
                if (list.size() <= 0) {
                    goodsInfo.preInsert();
                    goodsInfoMapper.insert(goodsInfo);
                } else {
                    GoodsInfo byGoods = goodsInfoMapper.getByGoods(new GoodsInfo(entity));
                    goodsInfo.setId(byGoods.getId());
                    goodsInfo.preUpdate();
                    goodsInfoMapper.update(goodsInfo);
                }
            }else {
                goodsInfoMapper.deleteByPhysics(goodsInfo);
            }
        }
        return save;
    }

    @Override
    public int deleteByPhysics(Medicine entity) {
        return super.deleteByPhysics(entity);
    }
}
