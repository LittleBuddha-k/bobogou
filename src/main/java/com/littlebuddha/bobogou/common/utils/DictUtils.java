package com.littlebuddha.bobogou.common.utils;/*
package com.littlebuddha.recruit.common.utils;

import com.littlebuddha.recruit.modules.service.system.DictTypeService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class DictUtils {


    private static DictTypeService dictTypeService = (DictTypeService) ApplicationContextUtils.getBean("dictTypeService");

    public static final String CACHE_DICT_MAP = "dictMap";

    public static String getDictLabel(String value, String type, String defaultLabel) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            for (DictValue dictValue : getDictList(type)) {
                if (value.equals(dictValue.getValue())) {
                    return dictValue.getLabel();
                }
            }
        }
        return defaultLabel;
    }

    public static String getDictLabels(String values, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)) {
            List<String> valueList = Lists.newArrayList();
            for (String value : StringUtils.split(values, ",")) {
                valueList.add(getDictLabel(value, type, defaultValue));
            }
            return StringUtils.join(valueList, ",");
        }
        return defaultValue;
    }

    public static String getDictValue(String label, String type, String defaultLabel) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)) {
            for (DictValue dictValue : getDictList(type)) {
                if (label.equals(dictValue.getLabel())) {
                    return dictValue.getValue();
                }
            }
        }
        return defaultLabel;
    }

    public static List<DictValue> getDictList(String type) {
        @SuppressWarnings("unchecked")
        Map<String, List<DictValue>> dictMap = (Map<String, List<DictValue>>) CacheUtils.get(CACHE_DICT_MAP);
        if (dictMap == null) {
            dictMap = Maps.newHashMap();
            for (DictType dictType : dictTypeService.findList(new DictType())) {
                List<DictValue> dictList = dictMap.get(dictType.getType());
                dictType = dictTypeService.get(dictType.getId());
                if (dictList != null) {
                    dictList.addAll(dictType.getDictValueList());
                } else {
                    dictMap.put(dictType.getType(), Lists.newArrayList(dictType.getDictValueList()));
                }
            }
            CacheUtils.put(CACHE_DICT_MAP, dictMap);
        }
        List<DictValue> dictList = dictMap.get(type);
        if (dictList == null) {
            dictList = Lists.newArrayList();
        }
        return dictList;
    }

    */
/*
     * ?????????????????????????????????????????????
     *//*

    public static Object getValue(Object obj, String filed) {
        try {
            Class clazz = obj.getClass();
            PropertyDescriptor pd = new PropertyDescriptor(filed, clazz);
            Method getMethod = pd.getReadMethod();//??????get??????

            if (pd != null) {

                Object o = getMethod.invoke(obj);//??????get??????????????????Object
                return o;

            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
*/
