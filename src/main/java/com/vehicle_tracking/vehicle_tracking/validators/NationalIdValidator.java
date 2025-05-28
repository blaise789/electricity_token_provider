package com.vehicle_tracking.vehicle_tracking.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NationalIdValidator implements ConstraintValidator<ValidNationalId, String> {

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        // Validate that the ID is exactly 16 digits
        return id != null && id.matches("\\d{16}");
    }
}
