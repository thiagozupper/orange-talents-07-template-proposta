package br.com.zupacademy.thiago.proposta.cartao.carteira;

public class AssociaNovaCarteiraResponse {

    private String resultado;
    private String id;

    public AssociaNovaCarteiraResponse(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
