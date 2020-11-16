package com.geek.spring.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @program: beanassemble
 * @description:
 * @author: yarne
 * @create: 2020-11-16 17:50
 **/
public class UserFactory implements FactoryBean<User> {
    public User getObject() {
        User user = new User();
        user.setName("赵六");
        user.setAge("21");
        return user;
    }

    public Class<?> getObjectType() {
        return User.class;
    }
}
