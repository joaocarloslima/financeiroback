package br.com.fiap.gestanca.models;

import java.math.BigDecimal;

public record ContaDto(
    Long id,
    String nome,
    String icone,
    BigDecimal saldo
) {

    public ContaDto(Conta conta, BigDecimal saldo){
        this(
            conta.getId(),
            conta.getNome(),
            conta.getIcone(),
            saldo
        );
    }
    
}
