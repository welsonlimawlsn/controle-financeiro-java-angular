package br.com.controlefinanceiro.usuario.dto.novo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import br.com.controlefinanceiro.email.anotacao.EnviaEmail;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

@Getter
@Setter
@EnviaEmail(vm = "confirmacao_usuario.vm")
public abstract class AbstractNovoUsuarioRespostaDTO extends RespostaDTO
{
    @JsonIgnore
    private Usuario usuario;
}
