package com.littlebuddha.bobogou.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {

    /**
     * 获取完整的时间---并为字符串格式
     * @param date
     * @return
     */
    public static String getFullDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 获取指定天数的日期
     * @param num 指定的天数
     * @return
     */
    public static Date getSpecifyDate (int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, num);
        Date date = calendar.getTime();
        return date;
    }

    public static void main(String[] args) {
        System.out.println(getFullDate(new Date()));
        //Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String currdate = format.format(date);
        //
        //System.out.println("当前日期是:" + currdate);
        Date addDate = getSpecifyDate(30);
        System.out.println("30天以后的日期是:"+format.format(addDate));
    }
}
