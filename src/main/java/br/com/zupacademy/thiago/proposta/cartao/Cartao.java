package br.com.zupacademy.thiago.proposta.cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Cartao {

    @Id
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private BigDecimal limite;
    private Long idProposta;

    public Cartao() {
    }

    public Cartao(String id, LocalDateTime emitidoEm, String titular,
                  BigDecimal limite, Long idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.idProposta = idProposta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cartao cartao = (Cartao) o;
        return id.equals(cartao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
