package br.com.zupacademy.thiago.proposta.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.charset.StandardCharsets;

public class Base64Validator implements ConstraintValidator<Base64, String> {

    @Override
    public boolean isValid(String biometria, ConstraintValidatorContext context) {
        try {
            java.util.Base64.getDecoder().decode(biometria);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

//    public static void main(String[] args) {
//        String s = java.util.Base64.getEncoder().encodeToString("biometria-thiago.silva@zup.com.br".getBytes(StandardCharsets.UTF_8));
//        System.out.println(s);
//    }
}
