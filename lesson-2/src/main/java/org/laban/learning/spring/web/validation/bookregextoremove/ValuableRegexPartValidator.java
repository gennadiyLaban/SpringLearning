package org.laban.learning.spring.web.validation.bookregextoremove;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.laban.learning.spring.web.dto.BookRegexToRemove;

public class ValuableRegexPartValidator implements ConstraintValidator<ValuableRegexPart, String> {
    private final Logger logger = LogFactory.getLogger(ValuableRegexPartValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.debug("start validation of %s".formatted(value));
        if (Objects.isNull(value)) return false;

        var delimiterIndex = value.indexOf(BookRegexToRemove.DELIMITER);
        if (delimiterIndex == -1 || delimiterIndex == value.length() - 1) return false;

        var regex = value.substring(delimiterIndex + 1);
        try {
            Pattern.compile(regex);
            return true;
        } catch (PatternSyntaxException e) {
            return false;
        }
    }

}
