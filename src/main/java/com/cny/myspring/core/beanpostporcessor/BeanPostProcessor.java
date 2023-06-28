package com.cny.myspring.core.beanpostporcessor;

import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;

/**
 * @author : chennengyuan
 */
public interface BeanPostProcessor {

    @Nullable
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


    @Nullable
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
