package br.com.zupacademy.thiago.proposta.cartao;

public enum StatusCartao {
    ATIVO(0),
    BLOQUEIO_SOLICITADO(1),
    BLOQUEADO(2);

    private int codigo;

    StatusCartao(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static StatusCartao toEnum(int codigo) {
        for (StatusCartao statusCartao : StatusCartao.values()) {
            if (statusCartao.getCodigo() == codigo)
                return statusCartao;
        }
        throw new IllegalArgumentException();
    }
}
