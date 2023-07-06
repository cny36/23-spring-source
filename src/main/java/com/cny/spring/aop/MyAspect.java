package com.cny.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author : chennengyuan
 */
@Aspect
@Component
public class MyAspect {

    @Before(value = "execution(* com.cny.spring.service.OrderService.*.*(..))")
    public void before(JoinPoint joinPoint){
        System.out.println("MyAspect      before()");
    }

}
