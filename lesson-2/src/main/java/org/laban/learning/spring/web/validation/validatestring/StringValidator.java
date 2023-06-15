package org.laban.learning.spring.web.validation.validatestring;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;

public class StringValidator implements ConstraintValidator<ValidateString, String> {
    private final Logger logger = LogFactory.getLogger(StringValidator.class);
    private List<String> valueList;

    @Override
    public void initialize(ValidateString constraintAnnotation) {
        valueList = new ArrayList<String>();
        for(String val : constraintAnnotation.acceptedValues()) {
            valueList.add(val.toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.debug("start validation of %s".formatted(String.valueOf(value)));
        if (Objects.isNull(value)) return false;

        return valueList.contains(value.toUpperCase());
    }

}
