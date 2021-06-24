package com.hatchways.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BackendAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendAssessmentApplication.class, args);
	}

}
