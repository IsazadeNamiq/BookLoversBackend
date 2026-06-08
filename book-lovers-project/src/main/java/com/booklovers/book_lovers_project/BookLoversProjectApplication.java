package com.booklovers.book_lovers_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookLoversProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookLoversProjectApplication.class, args);
	}

}
