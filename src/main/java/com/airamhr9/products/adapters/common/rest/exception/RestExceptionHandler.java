package com.airamhr9.products.adapters.common.rest.exception;

import com.airamhr9.products.application.common.exception.InvalidArgumentsException;
import com.airamhr9.products.application.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(InvalidArgumentsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<RestErrorResponse> handleInvalidArgumentsException(InvalidArgumentsException ex) {
    RestErrorResponse errorResponse = new RestErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage()
    );
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<RestErrorResponse> handleNotFoundException(NotFoundException ex) {
    RestErrorResponse errorResponse = new RestErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<RestValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestValidationErrorResponse.from(ex));
  }
}
