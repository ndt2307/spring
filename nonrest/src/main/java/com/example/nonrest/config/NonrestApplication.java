package com.example.nonrest.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.nonrest.entity")
@EnableJpaRepositories("com.example.nonrest.repository")
@ComponentScan("com.example.nonrest.controller")
public class NonrestApplication {

	public static void main(String[] args) {
		SpringApplication.run(NonrestApplication.class, args);
	}

}
