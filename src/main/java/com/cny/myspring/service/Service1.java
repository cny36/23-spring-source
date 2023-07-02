package com.cny.myspring.service;

import com.cny.myspring.annotion.MyAutowired;
import com.cny.myspring.annotion.MyComponent;
import com.cny.myspring.beans.MyInitializingBean;

/**
 * @author : chennengyuan
 */
@MyComponent
//@MyScope("prototype")
public class Service1 implements MyInitializingBean {

    @MyAutowired
    private DaoMapper daoMapper;

    public Service1(){
        System.out.println("Service1 create......");
    }

    public void hello() {
        System.out.println("Service1 hello()");
        daoMapper.daoHello();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Service1 执行初始化操作");
    }
}
