package com.cny.spring.service;

import com.cny.spring.dao.OrderDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : chennengyuan
 */
@Service
public class OrderService implements InitializingBean {

    //@Autowired
    private OrderDao orderDao;

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        System.out.println("setOrderDao");
        this.orderDao = orderDao;
    }

    public OrderService(){
        System.out.println("OrderService create.....");
    }

    public void hello(){
        System.out.println("hello spring");
        orderDao.hello();
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("执行一些初始化操作");
    }
}
