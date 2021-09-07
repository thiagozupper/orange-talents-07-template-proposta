package br.com.zupacademy.thiago.proposta.cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartaoResponse {

    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private BigDecimal limite;
    private String idProposta;

    public CartaoResponse(String id, LocalDateTime emitidoEm, String titular, BigDecimal limite, String idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.idProposta = idProposta;
    }

    public Cartao toCartao() {
        return new Cartao(this.id, this.emitidoEm, this.titular, this.limite, Long.parseLong(this.idProposta));
    }

    public String getId() {
        return this.id;
    }
}
