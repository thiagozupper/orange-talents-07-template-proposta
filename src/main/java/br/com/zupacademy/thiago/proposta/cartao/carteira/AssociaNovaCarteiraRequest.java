package br.com.zupacademy.thiago.proposta.cartao.carteira;

import br.com.zupacademy.thiago.proposta.cartao.Cartao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AssociaNovaCarteiraRequest {

    @NotEmpty
    @Email
    private String email;

    @NotNull
    private TipoCarteira carteira;

    public AssociaNovaCarteiraRequest(String email, TipoCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getCarteira() {
        return carteira;
    }

    public Carteira toCarteira(Cartao cartao) {
        return new Carteira(this.email, this.carteira, cartao);
    }
}
