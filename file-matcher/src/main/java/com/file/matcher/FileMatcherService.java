package com.file.matcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.file.*")
public class FileMatcherService {

	public static void main(String[] args) {
		SpringApplication.run(FileMatcherService.class, args);
	}
}
