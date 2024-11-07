package com.airamhr9.products.application.common.search;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

public class ValidSortFieldsValidator implements ConstraintValidator<ValidSortFields, PageRequest> {
  private String[] values;
  private boolean allowEmpty;

  @Override
  public void initialize(ValidSortFields annotation) {
    values = annotation.values();
    allowEmpty = annotation.allowEmpty();
  }

  @Override
  public boolean isValid(PageRequest pageRequest, ConstraintValidatorContext context) {
    if (pageRequest == null) {
      return true;
    }
    if (Strings.isBlank(pageRequest.getSortField()))  {
      return allowEmpty;
    }
    for (String value : values) {
      if (Objects.equals(value, pageRequest.getSortField())) {
        return true;
      }
    }

    return false;
  }
}