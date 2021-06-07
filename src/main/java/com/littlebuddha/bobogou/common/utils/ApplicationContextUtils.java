package com.littlebuddha.bobogou.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 从工厂中为
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 根据指定名字获取工厂中的对象
     * @return
     */
    public static Object getBean(String beanName){
        Object bean = applicationContext.getBean(beanName);
        return bean;
    }
}
