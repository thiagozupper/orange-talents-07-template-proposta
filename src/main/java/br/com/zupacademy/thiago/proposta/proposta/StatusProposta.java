package br.com.zupacademy.thiago.proposta.proposta;

public enum StatusProposta {
    PENDENTE_DE_ANALISE(0),
    NAO_ELEGIVEL(1),
    ELEGIVEL(2);

    private int codigo;

    StatusProposta(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static StatusProposta toEnum(int codigo) {
        for (StatusProposta statusProposta : StatusProposta.values()) {
            if (statusProposta.getCodigo() == codigo)
                return statusProposta;
        }
        throw new IllegalArgumentException();
    }
}
