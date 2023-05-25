package org.laban.learning.spring.web.validation.bookregextoremove;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValuableRegexPartValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@NotNull
public @interface ValuableRegexPart {
    String message() default "{org.laban.learning.spring.web.validation.string.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
