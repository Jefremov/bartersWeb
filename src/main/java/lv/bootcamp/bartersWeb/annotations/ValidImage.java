package lv.bootcamp.bartersWeb.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import lv.bootcamp.bartersWeb.exceptions.ValidImageValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidImageValidator.class)
public @interface ValidImage {
    String message() default "Invalid image file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}