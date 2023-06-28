package com.cny.spring;

import com.cny.spring.config.AppConfig;
import com.cny.spring.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author : chennengyuan
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        OrderService orderService = (OrderService) applicationContext.getBean("orderService");
        /**
         * 对象创建的过程
         * 1.默认调用无参构造方法实例化对象
         * 2.填冲属性
         * 3.执行初始化前操作 BeanPostProcessor.postProcessBeforeInitialization
         * 4.执行初始化操作 InitializingBean.afterPropertiesSet
         * 5.执行初始化后操作 BeanPostProcessor.postProcessAfterInitialization
         */

        orderService.hello();
    }
}
