package com.geek.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @program: JAVA-000
 * @description:
 * @author: yarne
 * @create: 2020-11-17 10:44
 **/
public class AdviceBefore implements InvocationHandler {

    public Object baseInterface;

    public AdviceBefore(Object baseInterface) {
        this.baseInterface = baseInterface;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(Thread.currentThread().getName()+"----beforeAdvice");
        return method.invoke(baseInterface,args);
    }
}
