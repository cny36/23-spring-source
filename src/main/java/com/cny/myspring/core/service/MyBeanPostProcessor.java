package com.cny.myspring.core.service;

import com.cny.myspring.core.annotion.MyComponent;
import com.cny.myspring.core.beanpostporcessor.BeanPostProcessor;
import org.springframework.beans.BeansException;

/**
 * @author : chennengyuan
 */
@MyComponent
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization " + beanName);
        return bean;
    }
}
