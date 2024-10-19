package com.file.upload.exception;

import org.springframework.http.ResponseEntity;

public class ResponseEntityException extends RuntimeException {

  private final ResponseEntity<String> responseEntity;

  public ResponseEntityException(ResponseEntity<String> responseEntity) {
    this.responseEntity = responseEntity;
  }

  public ResponseEntity<String> getResponseEntity() {
    return responseEntity;
  }
}

