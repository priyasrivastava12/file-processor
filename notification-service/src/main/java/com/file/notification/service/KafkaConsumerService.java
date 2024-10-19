package com.file.notification.service;

import com.file.notification.config.KafkaProperties;
import com.file.notification.dao.UpdateFileMetaDataRepository;
import com.file.notification.model.FileStatus;
import com.file.notification.model.FileResultMetaData;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

@Service
public class KafkaConsumerService {


  private final KafkaProperties kafkaProperties;

  private final AWSNotificationService aWSNotificationService;

  private final Consumer<String, FileResultMetaData> consumer;

  private final UpdateFileMetaDataRepository updateFileMetaDataRepository;

  @Autowired
  public KafkaConsumerService(KafkaProperties kafkaProperties, AWSNotificationService aWSNotificationService, UpdateFileMetaDataRepository updateFileMetaDataRepository) {
    this.kafkaProperties = kafkaProperties;
    this.aWSNotificationService = aWSNotificationService;
    this.updateFileMetaDataRepository = updateFileMetaDataRepository;
    Properties properties = new Properties();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getKeyDeserializer());
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getValueDeserializer());
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());

    consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties);
  }

  public void consumeNotificationDataEvent() {

    consumer.subscribe(Collections.singletonList(kafkaProperties.getTopic()));

    while (true) {
      ConsumerRecords<String, FileResultMetaData> records = consumer.poll(Duration.ofMillis(100));
      for (ConsumerRecord<String, FileResultMetaData> record : records) {
        aWSNotificationService.notifyCustomer(record.value());
        updateFileMetaDataRepository.updateFileMetadata(record.value().getTaskId(),
                FileStatus.COMPLETED_NOTIFIED.name(), record.value().getOutputfilePath());
        //System.exit(1);
      }
      try {
        Thread.sleep(1000);
      } catch (Exception e) {
      }
    }

  }


}
