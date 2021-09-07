package br.com.zupacademy.thiago.proposta.biometria;

import br.com.zupacademy.thiago.proposta.cartao.Cartao;
import br.com.zupacademy.thiago.proposta.validator.Base64;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class BiometriaRequest {

    @NotBlank
    @Base64
    private String biometria;

    public String getBiometria() {
        return biometria;
    }

    public Biometria toBiometria(Cartao cartao) {
        return new Biometria(biometria, cartao, LocalDateTime.now());
    }
}
