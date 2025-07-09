package com.todoapp.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface StrongPassword {
    String message() default "La contraseña no es lo suficientemente fuerte";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

