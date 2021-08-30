package br.com.zupacademy.thiago.proposta.proposta;

import br.com.zupacademy.thiago.proposta.feing.analise.AnaliseRequest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salario;
    private int status;

    private Proposta() {
    }

    public Proposta(String documento, String email,
                    String nome, String endereco,
                    BigDecimal salario, StatusProposta status) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.status = status.getCodigo();
    }

    public void setStatus(StatusProposta status) {
        this.status = status.getCodigo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposta proposta = (Proposta) o;
        return documento.equals(proposta.documento) && email.equals(proposta.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documento, email);
    }

    public Long getId() {
        return this.id;
    }

    public AnaliseRequest toAnaliseRequest() {
        return new AnaliseRequest(this.documento, this.nome, this.id.toString());
    }
}
