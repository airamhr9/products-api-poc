package com.airamhr9.products.application.common.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotFoundException extends RuntimeException {
  private String message;
}
