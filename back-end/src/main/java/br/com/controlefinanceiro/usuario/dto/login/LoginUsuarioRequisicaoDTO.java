package br.com.controlefinanceiro.usuario.dto.login;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;

@Getter
@Setter
public class LoginUsuarioRequisicaoDTO extends RequisicaoDTO<LoginUsuarioRespostaDTO>
{
    @NotEmpty(message = Erro.Constante.SENHA_OBRIGATORIA)
    private String senha;

    @NotEmpty(message = Erro.Constante.EMAIL_OBRIGATORIO)
    @Email(message = Erro.Constante.EMAIL_INVALIDO)
    @Override
    public String getEmail()
    {
        return super.getEmail();
    }
}
