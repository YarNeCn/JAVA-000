package com.geek.spring.studentstart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentStartApplication implements CommandLineRunner {

	@Autowired
	School school;

	public static void main(String[] args) {
		SpringApplication.run(StudentStartApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		school.ding();
	}
}
