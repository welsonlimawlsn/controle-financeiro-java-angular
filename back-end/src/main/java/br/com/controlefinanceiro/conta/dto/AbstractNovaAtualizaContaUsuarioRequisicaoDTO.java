package br.com.controlefinanceiro.conta.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;

@Getter
@Setter
public abstract class AbstractNovaAtualizaContaUsuarioRequisicaoDTO<RESPOSTA extends AbstractNovaAtualizaContaUsuarioRespostaDTO> extends RequisicaoDTO<RESPOSTA>
{
    @NotEmpty(message = Erro.Constante.NOME_OBRIGATORIO)
    private String nome;

    private BigDecimal valorInicial;
}
