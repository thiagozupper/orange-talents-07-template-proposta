package br.com.zupacademy.thiago.proposta.proposta;

import java.math.BigDecimal;

public class PropostaDetalhe {

    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salario;
    private StatusProposta status;
    private String numeroCartao;

    public PropostaDetalhe(String documento, String email, String nome, String endereco, BigDecimal salario,
                           StatusProposta status, String numeroCartao) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.status = status;
        this.numeroCartao = numeroCartao;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }
}
