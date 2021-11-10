package com.littlebuddha.bobogou.common.utils;

import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import com.littlebuddha.bobogou.modules.entity.basic.RoyaltyRecord;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import com.littlebuddha.bobogou.modules.entity.data.OrderFactory;
import com.littlebuddha.bobogou.modules.entity.data.RegionGoods;
import com.littlebuddha.bobogou.modules.entity.data.utils.OrderExportDTO;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;

import java.util.List;

/**
 * User: LittleBuddha-k
 * Date: 2021-11-05
 */
public class ListUtils {

    /**
     * 对订单列表去重
     * @param list
     * @return
     */
    public static List removeDuplicateOrder(List<Order> list) {
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
     * 对CustomerUser列表去重
     * @param list
     * @return
     */
    public static List removeDuplicateCustomerUser(List<CustomerUser> list) {
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
     * 对Factory列表去重
     * @param list
     * @return
     */
    public static List removeDuplicateFactory(List<Factory> list) {
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
     * 对RoyaltyRecord列表去重
     * @param list
     * @return
     */
    public static List removeDuplicateRoyaltyRecord(List<RoyaltyRecord> list) {
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
     * 对订单导出数据去重
     * @param list
     * @return
     */
    public static List removeDuplicateOrderExportDTO(List<OrderExportDTO> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getOrderInfoId().equals(list.get(i).getOrderInfoId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 对商品区域数据去重
     * @param list
     * @return
     */
    public static List removeDuplicateRegionGoods(List<RegionGoods> list) {
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
     * 对订单厂家数据去重
     * @param list
     * @return
     */
    public static List removeDuplicateOrderFactory(List<OrderFactory> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
}