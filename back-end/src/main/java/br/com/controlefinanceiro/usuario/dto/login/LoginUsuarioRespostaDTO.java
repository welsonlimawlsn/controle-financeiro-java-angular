package br.com.controlefinanceiro.usuario.dto.login;

import lombok.Getter;
import lombok.Setter;

import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;
import br.com.controlefinanceiro.token.dto.TokenDTO;
import br.com.controlefinanceiro.usuario.dto.UsuarioDTO;

@Getter
@Setter
public class LoginUsuarioRespostaDTO extends RespostaDTO
{
    private UsuarioDTO usuario;

    private TokenDTO token;
}
