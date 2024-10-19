package com.file.upload.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaPropertiesProducer {

  private String bootstrapServers;
  private String keySerializer;
  private String valueSerializer;

  private String topic;

  private String key;

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getBootstrapServers() {
    return bootstrapServers;
  }

  public void setBootstrapServers(String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
  }

  public String getKeySerializer() {
    return keySerializer;
  }

  public void setKeySerializer(String keySerializer) {
    this.keySerializer = keySerializer;
  }

  public String getValueSerializer() {
    return valueSerializer;
  }

  public void setValueSerializer(String valueSerializer) {
    this.valueSerializer = valueSerializer;
  }
}