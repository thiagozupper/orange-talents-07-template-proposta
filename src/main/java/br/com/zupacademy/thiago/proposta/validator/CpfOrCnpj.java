package br.com.zupacademy.thiago.proposta.validator;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@CPF
@CNPJ
@ConstraintComposition(CompositionType.OR)
@ReportAsSingleViolation
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface CpfOrCnpj {
    String message() default "Documento inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
