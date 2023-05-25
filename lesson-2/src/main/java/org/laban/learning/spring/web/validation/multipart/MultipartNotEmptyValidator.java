package org.laban.learning.spring.web.validation.multipart;

import org.laban.learning.spring.utils.log.LogFactory;
import org.laban.learning.spring.utils.log.Logger;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class MultipartNotEmptyValidator implements ConstraintValidator<MultipartFileNotEmpty, MultipartFile> {
    private final Logger logger = LogFactory.getLogger(MultipartNotEmptyValidator.class);

    @Override
    public boolean isValid(@Nullable MultipartFile value, ConstraintValidatorContext context) {
        logger.debug("start validation of {}", value);

        return !Objects.isNull(value) && !value.isEmpty();
    }

}