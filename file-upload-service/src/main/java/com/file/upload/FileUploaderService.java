package com.file.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.file.*")
public class FileUploaderService {

  public static void main(String[] args) {

    SpringApplication.run(FileUploaderService.class, args);
  }
}
