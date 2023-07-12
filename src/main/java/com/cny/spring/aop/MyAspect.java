package com.cny.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author : chennengyuan
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class MyAspect {

    @Before(value = "execution(* com.cny.spring.service.OrderService.*.*(..))")
    public void before(JoinPoint joinPoint){
        System.out.println("切面类 MyAspect      before()");
    }

}
