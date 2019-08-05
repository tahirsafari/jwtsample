package com.jwtsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.jwtsample", "com.jwtsample.services", "com.jwtsample.models", "com.jwtsample.controllers"})
public class JwtsampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtsampleApplication.class, args);
	}

}
