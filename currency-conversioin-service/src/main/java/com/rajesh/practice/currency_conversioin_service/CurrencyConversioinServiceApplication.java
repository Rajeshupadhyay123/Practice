package com.rajesh.practice.currency_conversioin_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurrencyConversioinServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversioinServiceApplication.class, args);
	}

}
