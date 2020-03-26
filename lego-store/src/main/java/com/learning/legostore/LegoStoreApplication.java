package com.learning.legostore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.github.mongobee.Mongobee;

@SpringBootApplication
@EnableMongoRepositories
public class LegoStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegoStoreApplication.class, args);
	}
	@Autowired
	private MongoTemplate mongoTemplate;
	@Bean
	public Mongobee mongobee() {
		 // Add the correct connection settings
        Mongobee runner = new Mongobee("mongodb://localhost:27017/legoStoreDB");
        
        // If you need to have access to the MongoTemplate class,
        // you need to pass it in
        runner.setMongoTemplate(mongoTemplate);
        
        // package for data migration classes
        runner.setChangeLogsScanPackage("com.learning.legostore.repository");
        return runner;

	}

}
