package com.learning.legostore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class LegoStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegoStoreApplication.class, args);
	}

}
