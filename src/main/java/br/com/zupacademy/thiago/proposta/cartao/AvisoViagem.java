package br.com.zupacademy.thiago.proposta.cartao;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destino;
    private LocalDate dataTerminoViagem;
    private LocalDateTime avisadoEm;
    private String ipCliente;
    private String userAgent;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public AvisoViagem() {
    }

    public AvisoViagem(String destino, LocalDate dataTerminoViagem,
                String ipCliente, String userAgent, Cartao cartao) {
        this.destino = destino;
        this.dataTerminoViagem = dataTerminoViagem;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.cartao = cartao;
        this.avisadoEm = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvisoViagem that = (AvisoViagem) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
