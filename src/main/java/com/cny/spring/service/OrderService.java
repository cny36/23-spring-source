package com.cny.spring.service;

import com.cny.spring.aop.NeedAop;
import com.cny.spring.dao.OrderDao;
import lombok.extern.slf4j.Slf4j;
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
        System.err.println("执行set方法");
        this.orderDao = orderDao;
    }

    public OrderService() {
        System.err.println("执行无参构造方法");
    }


    public void hello() {
        System.err.print("执行方法逻辑：");
        orderDao.hello();

    }

    public void afterPropertiesSet() throws Exception {
        System.err.println("执行初始化操作");
    }
}
