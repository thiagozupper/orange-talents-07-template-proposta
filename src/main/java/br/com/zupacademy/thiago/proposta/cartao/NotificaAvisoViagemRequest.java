package br.com.zupacademy.thiago.proposta.cartao;

public class NotificaAvisoViagemRequest {

    private String destino;
    private String validoAte;

    public NotificaAvisoViagemRequest(AvisoViagemRequest avisoViagemRequest) {
        this.destino = avisoViagemRequest.getDestino();
        this.validoAte = avisoViagemRequest.getDataTerminoViagem().toString();
    }

    public String getDestino() {
        return destino;
    }

    public String getValidoAte() {
        return validoAte;
    }
}
