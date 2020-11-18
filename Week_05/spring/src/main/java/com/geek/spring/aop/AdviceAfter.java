package com.geek.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @program: JAVA-000
 * @description:
 * @author: yarne
 * @create: 2020-11-17 10:48
 **/
public class AdviceAfter implements InvocationHandler {

    public Object baseInterface;

    public AdviceAfter(Object baseInterface) {
        this.baseInterface = baseInterface;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(baseInterface, args);
        System.out.println(Thread.currentThread().getName()+"----adviceAfter");
        return invoke;
    }
}
