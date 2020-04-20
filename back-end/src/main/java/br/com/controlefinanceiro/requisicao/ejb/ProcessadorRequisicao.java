package br.com.controlefinanceiro.requisicao.ejb;

import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;

public interface ProcessadorRequisicao
{
    <REQUISICAO extends RequisicaoDTO<RESPOSTA>, RESPOSTA extends RespostaDTO> RESPOSTA processa(REQUISICAO requisicao)
            throws InfraestruturaException, NegocioException;
}
