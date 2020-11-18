package com.geek.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @program: JAVA-000
 * @description:
 * @author: yarne
 * @create: 2020-11-17 10:48
 **/
public class AdviceAround implements InvocationHandler {

    public Object baseInterface;

    public AdviceAround(Object baseInterface) {
        this.baseInterface = baseInterface;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(Thread.currentThread().getName()+"----AdviceAroundBefore");
        Object invoke = method.invoke(baseInterface, args);
        System.out.println(Thread.currentThread().getName()+"----AdviceAroundAfter");
        return invoke;
    }
}
