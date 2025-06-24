package com.catalogue.catalogueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class CatalogueServiceApplication {

	@Autowired
    DatabaseInterface vehicleRepo;

	public static void main(String[] args) {
		SpringApplication.run(CatalogueServiceApplication.class, args);
	}


}
