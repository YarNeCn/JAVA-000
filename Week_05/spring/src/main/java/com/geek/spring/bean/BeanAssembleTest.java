package com.geek.spring.bean;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: beanassemble
 * @description: Bean装配
 * @author: yarne
 * @create: 2020-11-16 16:31
 **/
@Configuration
public class BeanAssembleTest{

    @Bean
    public static User user(){
        User user=new User();
        user.setName("李四");
        user.setAge("21");
        return user;
    }


    /**
     * xml<bean><bean/>配置
     */
    public static void test1(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext=new ClassPathXmlApplicationContext("classpath:application.xml");
        User user = (User)classPathXmlApplicationContext.getBean("user");
        System.out.println(user.getName());
        classPathXmlApplicationContext.close();
    }

    /**
     * 使用注解的方式，将当前类作为配置文件，将当前类的@Bean配置进去
     */
    public static void test2(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(BeanAssembleTest.class);
        annotationConfigApplicationContext.refresh();
        User user = (User)annotationConfigApplicationContext.getBean(User.class);
        System.out.println(user.getName());
        annotationConfigApplicationContext.close();
    }

    /**
     * 使用BeanFactory的registerSingleton方法注册
     */
    public static void test3(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext();
        ConfigurableListableBeanFactory beanFactory = annotationConfigApplicationContext.getBeanFactory();
        User user1 = user();
        user1.setName("王五");
        beanFactory.registerSingleton("user",user1);
        annotationConfigApplicationContext.refresh();
        User user = (User)annotationConfigApplicationContext.getBean(User.class);
        System.out.println(user.getName());
        annotationConfigApplicationContext.close();
    }

    /**
     * @Component
     * 实现FactoryBean接口,得到的是getObject的返回值User
     */
    public static void test4(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(UserFactory.class);
        annotationConfigApplicationContext.refresh();
        User user = (User)annotationConfigApplicationContext.getBean("userFactory");
        System.out.println(user.getName());
        annotationConfigApplicationContext.close();
    }


    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();

    }
}
