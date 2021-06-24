package com.hatchways.demo.util.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class FutureException extends RuntimeException {

  private final HttpStatus statusCode;

  public FutureException(String statusMessage) {
    super(statusMessage);
    this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
  }

  public FutureException(String statusMessage, HttpStatus statusCode) {
    super(statusMessage);
    this.statusCode = statusCode;
  }

  public FutureException(String statusMessage, HttpStatus statusCode, Throwable cause) {
    super(statusMessage, cause);
    this.statusCode = statusCode;
  }

}
