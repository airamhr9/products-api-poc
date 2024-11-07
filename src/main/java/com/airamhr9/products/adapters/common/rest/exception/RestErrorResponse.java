package com.airamhr9.products.adapters.common.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestErrorResponse {
  private int statusCode;
  private String message;
}
