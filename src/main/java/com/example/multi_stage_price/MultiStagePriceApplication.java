package com.example.multi_stage_price;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MultiStagePriceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiStagePriceApplication.class, args);
	}

}
