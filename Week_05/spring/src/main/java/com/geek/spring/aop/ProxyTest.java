package com.geek.spring.aop;

import java.lang.reflect.Proxy;

/**
 * @program: JAVA-000
 * @description: 测试类
 * @author: yarne
 * @create: 2020-11-16 18:37
 **/
public class ProxyTest {
    public static void main(String[] args) {
        BaseInterface baseInterface=new TargetClass();
        ProxyClass proxyClass = new ProxyClass(baseInterface);
        proxyClass.bind(proxyClass);
        Object o = Proxy.newProxyInstance(TargetClass.class.getClassLoader(), TargetClass.class.getInterfaces(), proxyClass);
        BaseInterface o1 = (BaseInterface) o;
        o1.action();
    }
}
