package com.file.upload.exception;

public class KafkaPublisherException extends RuntimeException {

  public KafkaPublisherException(String message) {
    super(message);
  }

  public KafkaPublisherException(String message, Throwable cause) {
    super(message, cause);
  }
}
