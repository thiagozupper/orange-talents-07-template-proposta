package br.com.zupacademy.thiago.proposta.cartao.carteira;

public enum TipoCarteira {

    PAYPAL(1),
    SAMSUNG_PAY(2);

    private int codigo;

    TipoCarteira(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TipoCarteira toEnum(int codigo) {
        for (TipoCarteira tipoCarteira : TipoCarteira.values()) {
            if (tipoCarteira.getCodigo() == codigo)
                return tipoCarteira;
        }
        throw new IllegalArgumentException();
    }
}
