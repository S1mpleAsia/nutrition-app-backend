package com.example.nutritionapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.jlefebure.spring.boot.minio.*"})
public class NutritionAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutritionAppApplication.class, args);
	}

}
