package com.file.orchestrator;

import com.file.orchestrator.service.KafkaConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.file.*")
public class FileMatcherOrchestator implements CommandLineRunner {

  @Autowired
  private KafkaConsumerService kafkaConsumerService;

  public static void main(String[] args) {
    SpringApplication.run(FileMatcherOrchestator.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    kafkaConsumerService.consumeFileMetaDataEvent();
  }
}
