package com.littlebuddha.bobogou.common.utils;

import com.littlebuddha.bobogou.modules.entity.system.Menu;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuUtils {

    //去重
    public static List removeDuplicate(List<Menu> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 排序
     *
     * @return
     */
    public static List<Menu> sort(List<Menu> sourceList, List<Menu> targetList, String parentId) {
        List<Menu> childrenList = null;
        for (Menu source : sourceList) {
            if (StringUtils.isNotBlank(source.getId()) && source.getParent().getId().equals(parentId)) {
                targetList.add(source);
                // 判断有没有子节点，有则继续追加
                if (source.getHasChildren()) {
                    //排序
                    for (Menu menu : sourceList) {
                        if (menu.getParent().getId().equals(source.getId())) {
                            sort(sourceList, targetList, source.getId());
                            break;
                        }
                    }
                }
            }
        }
        return targetList;
    }

    /**
     * 为菜单数据设置子节点列表
     *
     * @param menuList
     * @return
     */
    public static List<Menu> setChildrenList(List<Menu> menuList) {
        Map<String, String> menuMap = new HashMap<>();
        //用于存放根节点
        Menu root = null;
        //便利列表
        for (Menu menu : menuList) {
            if ("0".equals(menu.getParent().getId())) {
                root = menu;
            }
            for (Menu menu1 : menuList) {
                if (menu.getParent().getId().equals(menu1.getId())) {
                    menu1.getChild().add(menu);
                }
            }
        }
        //遍历菜单表，在map以id、name形式存放
        //便利列表，set进父节点
        return menuList;
    }

}
