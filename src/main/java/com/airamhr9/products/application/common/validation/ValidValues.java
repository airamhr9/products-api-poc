package com.airamhr9.products.application.common.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidValuesValidator.class})
public @interface ValidValues {
  String message() default "Invalid value";

  String[] values() default {};

  boolean allowEmpty() default true;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}