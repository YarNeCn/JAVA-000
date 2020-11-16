package com.geek.spring.bean;

import org.springframework.stereotype.Component;

/**
 * @program: beanassemble
 * @description: 等待装配的Bean
 * @author: yarne
 * @create: 2020-11-16 16:54
 **/
public class User{

    String name;

    String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
