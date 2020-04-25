package br.com.controlefinanceiro.conta.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ContaDTO
{
    private UUID id;

    private String nome;

    private BigDecimal valorInicial;
}
