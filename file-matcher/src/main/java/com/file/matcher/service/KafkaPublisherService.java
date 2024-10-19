package com.file.matcher.service;

import com.file.matcher.exception.KafkaPublisherException;
import com.file.matcher.kafka.KafkaPropertiesProducer;
import com.file.matcher.model.FileResultMetaData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class KafkaPublisherService {

  private final KafkaPropertiesProducer kafkaProperties;
  private final KafkaProducer<String, FileResultMetaData> producer;

  @Autowired
  public KafkaPublisherService(KafkaPropertiesProducer kafkaProperties) {
    this.kafkaProperties = kafkaProperties;

    Properties properties = new Properties();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getKeySerializer());
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getValueSerializer());

    producer = new KafkaProducer<>(properties);
  }

  public void  publishEvent(FileResultMetaData fileCompareMetaData) {
    ProducerRecord<String, FileResultMetaData> record = new ProducerRecord<>(kafkaProperties.getTopic(),
            kafkaProperties.getKey(), fileCompareMetaData);
    try {
      producer.send(record);
    } catch (Exception e) {
      throw new KafkaPublisherException("Failed to send Kafka message", e);
    }
  }


}
