package com.airamhr9.products.application.common.search;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidSortFieldsValidator.class})
public @interface ValidSortFields {
  String message() default "Invalid sort field";

  String[] values() default {};

  boolean allowEmpty() default true;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}