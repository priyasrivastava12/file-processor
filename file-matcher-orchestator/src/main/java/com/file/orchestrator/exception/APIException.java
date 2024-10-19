package com.file.orchestrator.exception;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class APIException extends RuntimeException {

  private final int statusCode;

  public APIException(int statusCode, String message, WebClientResponseException ex) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
