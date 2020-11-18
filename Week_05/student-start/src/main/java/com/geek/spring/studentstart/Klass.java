package com.geek.spring.studentstart;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Data
public class Klass {

    @Autowired
    List<Student> students;

    public void dong(){
        System.out.println(this.getStudents());
    }

}
