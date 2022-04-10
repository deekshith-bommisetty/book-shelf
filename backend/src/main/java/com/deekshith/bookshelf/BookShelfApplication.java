package com.deekshith.bookshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class BookShelfApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookShelfApplication.class, args);
	}

}
