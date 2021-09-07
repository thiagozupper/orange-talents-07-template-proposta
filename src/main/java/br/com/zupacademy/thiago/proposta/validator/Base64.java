package br.com.zupacademy.thiago.proposta.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = Base64Validator.class)
public @interface Base64 {

    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String message() default "A biometria não está em base64";
}
