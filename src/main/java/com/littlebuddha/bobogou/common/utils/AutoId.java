package com.littlebuddha.bobogou.common.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * @author ck
 * @date 2020/9/17 10:11
 */
public class AutoId {

    private static SecureRandom random = new SecureRandom();

    /**
     * uuid自动生成32位
     * @return
     */
    public static String getAutoId(){
        String id = UUID.randomUUID().toString().replace("-", "");
        return id;
    }

    public static void main(String[] args) {
        for (int i = 0;i<10;i++){
            String autoId = getAutoId();
            System.out.println(autoId);
        }
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static char[] randomLong() {
        char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        boolean[] flag = new boolean[letters.length];//初始化都为false
        char[] results = new char[5];
        for (int i = 0; i < 5; i++) {
            int index = 0;
            do {
                index = (int) (Math.random() * 26);//第一个肯定是不重复的
            } while (flag[index]);
            results[i] = letters[index];
            flag[index] = true;
        }
        return results;
    }

    /**
     * 获取指定位数的随机字符串---包含字母、符号
     */
    public static String getSplicing(int n){
        char [] chars= "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890,.<>/?;:'[{]}!@#$%^&*()]".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0 ; i < n ; i++){
            char aChar = chars[new Random().nextInt(chars.length)];
            stringBuilder.append(aChar);
        }
        return stringBuilder.toString();
    }

    /*public static void main(String[] args) {
        String splicing = getSplicing(12);
        System.out.println(splicing);
    }*/
}
