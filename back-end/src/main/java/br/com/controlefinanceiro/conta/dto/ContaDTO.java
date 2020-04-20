package br.com.controlefinanceiro.conta.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ContaDTO
{
    private Long id;

    private String nome;

    private BigDecimal valorInicial;
}
