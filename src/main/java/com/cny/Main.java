package com.cny;

import com.cny.config.AppConfig;
import com.cny.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author : chennengyuan
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        OrderService orderService = (OrderService) applicationContext.getBean("orderService");
        orderService.hello();
    }
}
