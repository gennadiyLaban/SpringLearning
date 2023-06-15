package org.laban.learning.spring.web.validation.multipart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;

@Constraint(validatedBy = MultipartNotEmptyValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@NotNull
public @interface MultipartFileNotEmpty {

    String message() default "{org.laban.learning.spring.web.validation.string.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
