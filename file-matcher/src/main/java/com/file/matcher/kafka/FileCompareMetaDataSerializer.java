package com.file.matcher.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.file.matcher.model.FileResultMetaData;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class FileCompareMetaDataSerializer implements Serializer<FileResultMetaData>, Deserializer<FileResultMetaData> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
  }

  @Override
  public byte[] serialize(String topic, FileResultMetaData data) {
    try {
      return objectMapper.writeValueAsBytes(data);
    } catch (IOException e) {
      throw new SerializationException("Error serializing FileCompareMetaData to JSON", e);
    }
  }

  @Override
  public FileResultMetaData deserialize(String topic, byte[] data) {
    if (data == null) {
      return null;
    }
    try {
      return objectMapper.readValue(data, FileResultMetaData.class);
    } catch (IOException e) {
      throw new SerializationException("Error deserializing JSON to FileCompareMetaData", e);
    }
  }

  @Override
  public void close() {
  }
}
