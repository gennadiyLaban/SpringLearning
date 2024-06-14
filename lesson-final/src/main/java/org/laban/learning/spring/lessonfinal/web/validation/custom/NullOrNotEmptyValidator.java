package org.laban.learning.spring.lessonfinal.web.validation.custom;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

public class NullOrNotEmptyValidator implements ConstraintValidator<NullOrNotEmpty, Collection> {
    @Override
    public boolean isValid(Collection value, ConstraintValidatorContext context) {
        return value == null || !value.isEmpty();
    }
}
