package br.com.controlefinanceiro.usuario.dto.novo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;

@Getter
@Setter
public abstract class AbstractNovoUsuarioRequisicaoDTO<T extends AbstractNovoUsuarioRespostaDTO> extends RequisicaoDTO<T>
{
    @NotEmpty(message = Erro.Constante.NOME_OBRIGATORIO)
    private String nome;

    @NotEmpty(message = Erro.Constante.SOBRENOME_OBRIGATORIO)
    private String sobrenome;

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
