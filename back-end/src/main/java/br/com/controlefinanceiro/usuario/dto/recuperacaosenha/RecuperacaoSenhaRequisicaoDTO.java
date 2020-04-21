package br.com.controlefinanceiro.usuario.dto.recuperacaosenha;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.controlefinanceiro.codigoconfirmacao.anotacao.RequerCodigoSeguranca;
import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;

@Getter
@Setter
@RequerCodigoSeguranca
public class RecuperacaoSenhaRequisicaoDTO extends RequisicaoDTO<RecuperacaoSenhaRespostaDTO>
{
    @NotEmpty(message = Erro.Constante.SENHA_OBRIGATORIA)
    @Size(min = 8, message = Erro.Constante.TAMANHO_MINIMO_SENHA)
    private String novaSenha;

    @NotEmpty(message = Erro.Constante.EMAIL_OBRIGATORIO)
    @Email(message = Erro.Constante.EMAIL_INVALIDO)
    @Override
    public String getEmail()
    {
        return super.getEmail();
    }
}
