package com.rajesh.practice.kafkawikimedia;

import com.rajesh.practice.kafkawikimedia.producer.WikiMediaChangeProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringKafkaRealWorldProjectApplication implements CommandLineRunner {

	@Autowired
	private WikiMediaChangeProducer wikiMediaChangeProducer;

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaRealWorldProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		wikiMediaChangeProducer.sendMessage();
	}
}
