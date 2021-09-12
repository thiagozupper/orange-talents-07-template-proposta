package br.com.zupacademy.thiago.proposta.cartao.carteira;

import br.com.zupacademy.thiago.proposta.cartao.Cartao;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private int carteira;

    @ManyToOne
    private Cartao cartao;

    public Carteira() {
    }

    public Carteira(String email, TipoCarteira tipoCarteira, Cartao cartao) {
        this.email = email;
        this.carteira = tipoCarteira.getCodigo();
        this.cartao = cartao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carteira carteira1 = (Carteira) o;
        return carteira == carteira1.carteira && email.equals(carteira1.email) && cartao.equals(carteira1.cartao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, carteira, cartao);
    }

    public Long getId() {
        return this.id;
    }
}
