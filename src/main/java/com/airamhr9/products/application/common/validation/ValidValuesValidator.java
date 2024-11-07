package com.airamhr9.products.application.common.validation;


import com.airamhr9.products.application.common.search.PageRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

public class ValidValuesValidator implements ConstraintValidator<ValidValues, String> {
  private String[] values;
  private boolean allowEmpty;

  @Override
  public void initialize(ValidValues annotation) {
    values = annotation.values();
    allowEmpty = annotation.allowEmpty();
  }

  @Override
  public boolean isValid(String input, ConstraintValidatorContext context) {
    if (Strings.isBlank(input)) {
      return allowEmpty;
    }
    for (String value : values) {
      if (Objects.equals(value, input)) {
        return true;
      }
    }

    return false;
  }
}