package com.cny.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author : chennengyuan
 */
@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy
public class MyAspect {

    @Pointcut(value = "@annotation(com.cny.spring.aop.NeedAop)")
    //@Pointcut(value = "execution(* com.cny.spring.service.AopTestService.*.*(..))")
    public void pointcut() {
    }


    @Before(value = "pointcut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("切面类 MyAspect      before()");
    }

    @Around(value = "pointcut()")
    public Object arount(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("切面类 MyAspect      arount() 开始");
        Object result = proceedingJoinPoint.proceed();
        System.out.println("切面类 MyAspect      arount() 结束");
        return result;
    }

    @After(value = "pointcut()")
    public void after(JoinPoint joinPoint) {
        System.out.println("切面类 MyAspect      after()");
    }

    @AfterReturning(value = "pointcut()", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) {
        System.out.println("切面类 MyAspect      afterReturning()");
    }

    @AfterThrowing(value = "pointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        System.out.println("切面类 MyAspect      afterThrowing()");
    }


}
