package com.jwtsample.jwtsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.jwtsample.jwtsample", "com.jwtsample.jwtsample.services", "com.jwtsample.jwtsample.models", "com.jwtsample.jwtsample.controllers"})
public class JwtsampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtsampleApplication.class, args);
	}

}
