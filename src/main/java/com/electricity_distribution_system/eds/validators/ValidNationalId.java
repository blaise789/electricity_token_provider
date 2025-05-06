package com.electricity_distribution_system.eds.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NationalIdValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNationalId {
    String message() default "Invalid National ID. It must be 16 digits and numeric.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}