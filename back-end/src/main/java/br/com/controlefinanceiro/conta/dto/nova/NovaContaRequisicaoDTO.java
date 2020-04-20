package br.com.controlefinanceiro.conta.dto.nova;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;

@Getter
@Setter
public class NovaContaRequisicaoDTO extends RequisicaoDTO<NovaContaRespostaDTO>
{
    @NotEmpty(message = Erro.Constante.NOME_OBRIGATORIO)
    private String nome;

    private BigDecimal valorInicial;
}
