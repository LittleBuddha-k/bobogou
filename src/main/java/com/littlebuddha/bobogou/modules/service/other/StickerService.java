package com.littlebuddha.bobogou.modules.service.other;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.littlebuddha.bobogou.common.config.yml.GlobalSetting;
import com.littlebuddha.bobogou.modules.base.service.CrudService;
import com.littlebuddha.bobogou.modules.entity.other.Sticker;
import com.littlebuddha.bobogou.modules.mapper.other.StickerMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StickerService extends CrudService<Sticker, StickerMapper> {

    @Autowired
    private GlobalSetting globalSetting;

    @Override
    public Sticker get(Sticker entity) {
        Sticker sticker = super.get(entity);
        if (sticker != null && StringUtils.isNotBlank(sticker.getImageUrl())){
            String result = "";
            String[] split = sticker.getImageUrl().split(",");
            for (String image : split) {
                result = result + globalSetting.getRootPath() + image + ",";
            }
            sticker.setImageUrl(result);
        }
        return sticker;
    }

    @Override
    public List<Sticker> findList(Sticker entity) {
        List<Sticker> list = super.findList(entity);
        for (Sticker sticker : list) {
            if (sticker != null && StringUtils.isNotBlank(sticker.getImageUrl())){
                String result = "";
                String[] split = sticker.getImageUrl().split(",");
                for (String image : split) {
                    result = result + globalSetting.getRootPath() + image + ",";
                }
                sticker.setImageUrl(result);
            }
        }
        return list;
    }

    @Override
    public PageInfo<Sticker> findPage(Page<Sticker> page, Sticker entity) {
        if(entity != null){
            String shopName = StringUtils.deleteWhitespace(entity.getShopName());
            entity.setShopName(shopName);
        }
        PageInfo<Sticker> page1 = super.findPage(page, entity);
        List<Sticker> list = page1.getList();
        for (Sticker sticker : list) {
            if (sticker != null && StringUtils.isNotBlank(sticker.getImageUrl())){
                String result = "";
                String[] split = sticker.getImageUrl().split(",");
                for (String image : split) {
                    result = result + globalSetting.getRootPath() + image + ",";
                }
                sticker.setImageUrl(result);
            }
        }
        return page1;
    }

    @Override
    public int save(Sticker entity) {
        entity.setIdType("AUTO");
        return super.save(entity);
    }

    @Override
    public int deleteByLogic(Sticker entity) {
        return super.deleteByLogic(entity);
    }

    @Override
    public int deleteByPhysics(Sticker entity) {
        return super.deleteByPhysics(entity);
    }

    @Override
    public PageInfo<Sticker> findRecoveryPage(Page<Sticker> page, Sticker entity) {
        return super.findRecoveryPage(page, entity);
    }

    @Override
    public int recovery(Sticker entity) {
        return super.recovery(entity);
    }
}
