package com.file.orchestrator.kafka;

import com.file.orchestrator.model.FileCompareMetaData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class FileCompareMetaDataDeserializer implements Serializer<FileCompareMetaData>, Deserializer<FileCompareMetaData> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
  }

  @Override
  public byte[] serialize(String topic, FileCompareMetaData data) {
    try {
      return objectMapper.writeValueAsBytes(data);
    } catch (IOException e) {
      throw new SerializationException("Error serializing FileCompareMetaData to JSON", e);
    }
  }

  @Override
  public FileCompareMetaData deserialize(String topic, byte[] data) {
    if (data == null) {
      return null;
    }
    try {
      return objectMapper.readValue(data, FileCompareMetaData.class);
    } catch (IOException e) {
      throw new SerializationException("Error deserializing JSON to FileCompareMetaData", e);
    }
  }

  @Override
  public void close() {
  }
}
