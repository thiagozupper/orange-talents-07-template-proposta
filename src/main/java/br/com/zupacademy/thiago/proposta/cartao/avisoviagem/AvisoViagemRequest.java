package br.com.zupacademy.thiago.proposta.cartao.avisoviagem;

import br.com.zupacademy.thiago.proposta.cartao.Cartao;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotEmpty
    private String destino;

    @NotNull
    @Future
    private LocalDate dataTerminoViagem;

    public AvisoViagemRequest(String destino, LocalDate dataTerminoViagem) {
        this.destino = destino;
        this.dataTerminoViagem = dataTerminoViagem;
    }

    public AvisoViagem toAvisoViagem(String ipCliente, String userAgente, Cartao cartao) {
        return new AvisoViagem(this.destino, this.dataTerminoViagem,
                ipCliente, userAgente, cartao);
    }

    public String getDestino() {
        return this.destino;
    }

    public LocalDate getDataTerminoViagem() {
        return dataTerminoViagem;
    }
}
