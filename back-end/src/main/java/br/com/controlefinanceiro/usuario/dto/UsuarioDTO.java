package br.com.controlefinanceiro.usuario.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioDTO
{
    private String nome;

    private String sobrenome;

    private String email;
}
