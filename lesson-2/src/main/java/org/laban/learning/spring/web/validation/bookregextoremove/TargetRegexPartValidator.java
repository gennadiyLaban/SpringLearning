package org.laban.learning.spring.web.validation.bookregextoremove;

import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.laban.learning.spring.web.validation.validatestring.StringValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TargetRegexPartValidator implements ConstraintValidator<TargetRegexPart, String> {
    private final Logger logger = LogFactory.getLogger(TargetRegexPartValidator.class);
    private List<String> valueList;

    @Override
    public void initialize(TargetRegexPart constraintAnnotation) {
        valueList = new ArrayList<String>();
        for(String val : constraintAnnotation.acceptedValues()) {
            valueList.add(val.toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.debug("start validation of %s".formatted(String.valueOf(value)));
        if (Objects.isNull(value)) return false;

        var checkedValue = value.toUpperCase();
        return valueList.stream().anyMatch(checkedValue::startsWith);
    }

}