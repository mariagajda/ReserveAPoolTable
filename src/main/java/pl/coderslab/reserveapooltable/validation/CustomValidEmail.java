package pl.coderslab.reserveapooltable.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomEmailValidator.class)
@Documented
public @interface CustomValidEmail {
    String message() default "{valid.email.error}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
