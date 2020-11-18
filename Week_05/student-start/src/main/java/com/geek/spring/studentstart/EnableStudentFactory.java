package com.geek.spring.studentstart;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: JavaCourseCodes
 * @description:
 * @author: yarne
 * @create: 2020-11-17 17:23
 **/

@Configuration
@ConditionalOnProperty(
        prefix = "spring.application.student",
        value = {"enabled"},
        havingValue = "true",
        matchIfMissing = false
)
@ComponentScan(value = "com.geek.spring.*")
public class EnableStudentFactory {

    @Bean
    @ConditionalOnMissingBean
    public Student student100(){
        Student student = new Student().create();
        student.init();
        return student;
    }

    @Bean
    @ConditionalOnMissingBean
    public School school(){
        return new School();
    }

    @Bean
    @ConditionalOnMissingBean
    public Klass klass(){
        return new Klass();
    }
}
