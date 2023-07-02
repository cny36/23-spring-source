package com.cny.myspring;

import com.cny.myspring.config.ApplicationConfig;
import com.cny.myspring.service.Service1;

/**
 * @author : chennengyuan
 */
public class Main {

    public static void main(String[] args) throws Exception {
        MyAnnotationConfigApplicationContext applicationContext = new MyAnnotationConfigApplicationContext(ApplicationConfig.class);
        Service1 service1 = (Service1) applicationContext.getBean("service1");
        service1.hello();



    }

}
