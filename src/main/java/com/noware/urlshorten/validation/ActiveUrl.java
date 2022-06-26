package com.noware.urlshorten.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ActiveUrlValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ActiveUrl {
    String message() default "URL is invalid or not reachable";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}