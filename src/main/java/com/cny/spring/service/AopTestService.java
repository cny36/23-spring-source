package com.cny.spring.service;

import com.cny.spring.aop.NeedAop;
import org.springframework.stereotype.Service;

/**
 * @author : chennengyuan
 */
@Service
public class AopTestService {

    @NeedAop
    public void run() {
        System.out.println("执行方法逻辑");
    }
}
