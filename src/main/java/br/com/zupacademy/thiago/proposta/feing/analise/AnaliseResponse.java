package br.com.zupacademy.thiago.proposta.feing.analise;

public class AnaliseResponse {

    private String documento;
    private String nome;
    private String idProposta;
    private StatusAnalise statusAnalise;

    public AnaliseResponse(String documento, String nome, String idProposta, StatusAnalise statusAnalise) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
        this.statusAnalise = statusAnalise;
    }

    @Override
    public String toString() {
        return "ResultadoAnaliseResponse{" +
                "documento='" + documento + '\'' +
                ", nome='" + nome + '\'' +
                ", idProposta='" + idProposta + '\'' +
                ", statusAnalise=" + statusAnalise +
                '}';
    }

    public StatusAnalise getStatusAnalise() {
        return statusAnalise;
    }
}
