package br.com.controlefinanceiro.usuario.dto.novo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import br.com.controlefinanceiro.email.anotacao.EnviaEmail;
import br.com.controlefinanceiro.email.anotacao.To;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

@Getter
@Setter
@EnviaEmail(vm = "confirmacao_usuario.vm", subject = "Seja bem vindo ao Fintips")
public abstract class AbstractNovoUsuarioRespostaDTO extends RespostaDTO
{
    @JsonIgnore
    private Usuario usuario;

    @To
    @JsonIgnore
    private String email;
}
