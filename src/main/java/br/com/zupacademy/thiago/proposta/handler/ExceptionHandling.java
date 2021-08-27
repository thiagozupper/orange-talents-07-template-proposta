package br.com.zupacademy.thiago.proposta.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandling {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroValidacao> inputInvalido(MethodArgumentNotValidException ex) {

        List<ErroValidacao> erros = new ArrayList<ErroValidacao>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fe -> {
            String mensagem = messageSource.getMessage(fe, LocaleContextHolder.getLocale());
            ErroValidacao erroValidacao = new ErroValidacao(fe.getField(), mensagem);
            erros.add(erroValidacao);
        });

        return erros;
    }
}
