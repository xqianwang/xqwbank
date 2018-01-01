package com.example.xqwbank;

import org.axonframework.spring.config.EnableAxonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAxonAutoConfiguration
@SpringBootApplication
public class XqwbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(XqwbankApplication.class, args);
	}
}
