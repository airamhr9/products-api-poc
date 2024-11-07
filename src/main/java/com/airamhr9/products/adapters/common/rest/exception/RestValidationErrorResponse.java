package com.airamhr9.products.adapters.common.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RestValidationErrorResponse extends RestErrorResponse {
  private List<InvalidField> wrongFields;

  public static RestValidationErrorResponse from(final MethodArgumentNotValidException validationException) {
    final RestValidationErrorResponse response = new RestValidationErrorResponse();
    response.setMessage("Validation error");
    response.setStatusCode(HttpStatus.BAD_REQUEST.value());

    final List<InvalidField> invalidFields = new ArrayList<>(validationException.getBindingResult().getErrorCount());
    for (FieldError fieldError : validationException.getBindingResult().getFieldErrors()) {
      invalidFields.add(new InvalidField(fieldError.getField(),
              fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : "",
              fieldError.getDefaultMessage()));
    }

    response.setWrongFields(invalidFields);

    return response;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class InvalidField {
    private String field;
    private String rejectedValue;
    private String message;
  }
}
