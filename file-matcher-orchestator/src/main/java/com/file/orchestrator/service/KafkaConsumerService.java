package com.file.orchestrator.service;

import com.file.orchestrator.client.FileMatcherServiceClient;
import com.file.orchestrator.config.KafkaProperties;
import com.file.orchestrator.dao.FileMetaDataRepository;
import com.file.orchestrator.model.FileCompareMetaData;
import com.file.orchestrator.model.FileStatus;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

@Service
public class KafkaConsumerService {

  private final KafkaProperties kafkaProperties;

  private final FileMetaDataRepository fileMetaDataRepository;

  private final Consumer<String, FileCompareMetaData> consumer;

  private final FileMatcherServiceClient fileMatcherServiceClient;

  @Autowired
  public KafkaConsumerService(KafkaProperties kafkaProperties, FileMatcherServiceClient fileMatcherServiceClient, FileMetaDataRepository fileMetaDataRepository) {
    this.kafkaProperties = kafkaProperties;
    this.fileMatcherServiceClient = fileMatcherServiceClient;
    this.fileMetaDataRepository = fileMetaDataRepository;
    Properties properties = new Properties();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getKeyDeserializer());
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getValueDeserializer());
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());

    consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties);
  }

  public void consumeFileMetaDataEvent() {

    consumer.subscribe(Collections.singletonList(kafkaProperties.getTopic()));

    while (true) {
      ConsumerRecords<String, FileCompareMetaData> records = consumer.poll(Duration.ofMillis(100));
      for (ConsumerRecord<String, FileCompareMetaData> record : records) {
        UUID randomUUID = UUID.randomUUID();
        String uuidString = randomUUID.toString();
        fileMatcherServiceClient.processFiles(record.value().getBuyerFilePath(),
                record.value().getSupplierFilePath(), uuidString);
        fileMetaDataRepository.saveFileMetaData(uuidString, record.value().getBuyerFilePath(),
                record.value().getSupplierFilePath(), FileStatus.WAITING);
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

  }

}
