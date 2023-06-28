package com.cny.myspring.core.beans;

import lombok.Data;

/**
 * 描述Bean信息的类
 *
 * @author : chennengyuan
 */
@Data
public class MyBeanDefinition {

    private Class type;
    private String scope;
    private boolean isLazy;
    private String beanname;
}
