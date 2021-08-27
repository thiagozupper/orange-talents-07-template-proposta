package br.com.zupacademy.thiago.proposta.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PropostaController {

    @GetMapping
    public String teste() {
        return "Its Work!";
    }
}
