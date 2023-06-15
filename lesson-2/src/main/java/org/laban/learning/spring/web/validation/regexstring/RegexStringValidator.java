package org.laban.learning.spring.web.validation.regexstring;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;

public class RegexStringValidator implements ConstraintValidator<RegexString, String> {
    private final Logger logger = LogFactory.getLogger(RegexStringValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.debug("start validation of %s".formatted(String.valueOf(value)));
        if (Objects.isNull(value)) return false;

        try {
            Pattern.compile(value);
            return true;
        } catch (PatternSyntaxException exception) {
            return false;
        }
    }

}
