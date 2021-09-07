package br.com.zupacademy.thiago.proposta.biometria;

import br.com.zupacademy.thiago.proposta.cartao.Cartao;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String biometria;

    private LocalDateTime dataCriacao;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Biometria() {
    }

    public Biometria(String biometria, Cartao cartao, LocalDateTime dataCriacao) {
        this.biometria = biometria;
        this.dataCriacao = dataCriacao;
        this.cartao = cartao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biometria biometria1 = (Biometria) o;
        return biometria.equals(biometria1.biometria) && cartao.equals(biometria1.cartao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(biometria, cartao);
    }

    public Long getId() {
        return this.id;
    }
}
