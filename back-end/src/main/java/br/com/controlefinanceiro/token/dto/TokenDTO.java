package br.com.controlefinanceiro.token.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class TokenDTO
{
    private String autorizacao;

    private ZonedDateTime expiracao;
}
