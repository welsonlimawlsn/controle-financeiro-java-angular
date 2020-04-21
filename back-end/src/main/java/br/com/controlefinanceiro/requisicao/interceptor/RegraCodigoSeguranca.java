package br.com.controlefinanceiro.requisicao.interceptor;

import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;

public interface RegraCodigoSeguranca
{
    boolean verificaRegra(RequisicaoDTO<?> requisicao) throws NegocioException;
}
