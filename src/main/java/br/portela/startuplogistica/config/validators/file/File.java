package br.portela.startuplogistica.config.validators.file;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({ FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FileInputValidator.class)
public @interface File {
    String message() default "Invalid file.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String[] extensions() default { "*" };
    String[] mimeTypes() default { "*" };
}
