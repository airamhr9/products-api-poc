package com.airamhr9.products.application.common.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidArgumentsException extends RuntimeException {
  private String message;
}
