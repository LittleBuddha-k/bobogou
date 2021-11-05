package com.littlebuddha.bobogou.common.utils;

import com.littlebuddha.bobogou.modules.entity.basic.Factory;
import com.littlebuddha.bobogou.modules.entity.basic.RoyaltyRecord;
import com.littlebuddha.bobogou.modules.entity.data.Order;
import com.littlebuddha.bobogou.modules.entity.other.CustomerUser;

import java.util.List;

/**
 * User: LittleBuddha-k
 * Date: 2021-11-05
 */
public class ListUtils<E> {

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
}