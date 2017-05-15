package com.tieto.batla.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.tieto.batla")
public class BatlaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatlaApplication.class, args);
	}
}
