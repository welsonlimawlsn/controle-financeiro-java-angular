package br.com.controlefinanceiro.codigoconfirmacao.ejb;

import br.com.controlefinanceiro.codigoconfirmacao.entidade.CodigoSeguranca;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;

public interface ProcessadorCodigoSeguranca
{
    void processaNovoCodigoSeguranca(RequisicaoDTO<?> requisicao) throws InfraestruturaException;

    CodigoSeguranca processaCodigoSegurancaExistente(RequisicaoDTO<?> requisicao) throws NegocioException;
}
