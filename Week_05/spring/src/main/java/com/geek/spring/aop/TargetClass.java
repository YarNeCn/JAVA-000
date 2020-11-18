package com.geek.spring.aop;

/**
 * @program: JAVA-000
 * @description: 代理目标类
 * @author: yarne
 * @create: 2020-11-16 18:20
 **/
public class TargetClass implements BaseInterface {
    public void action() {
        System.out.println("执行目标类的动作方法");
    }
}
