package ru.kpfu.itis.services.validation.annotations;

import ru.kpfu.itis.utils.HttpResponseStatus;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUserEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface UniqueUserEmail {

    String message() default HttpResponseStatus.DUPLICATE_EMAIL;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
