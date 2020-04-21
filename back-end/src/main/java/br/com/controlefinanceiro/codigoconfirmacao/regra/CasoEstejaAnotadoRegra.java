package br.com.controlefinanceiro.codigoconfirmacao.regra;

import br.com.controlefinanceiro.codigoconfirmacao.anotacao.RequerCodigoSeguranca;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;
import br.com.controlefinanceiro.requisicao.interceptor.RegraCodigoSeguranca;

public class CasoEstejaAnotadoRegra implements RegraCodigoSeguranca
{
    @Override
    public boolean verificaRegra(RequisicaoDTO<?> requisicao) throws NegocioException
    {
        return requisicao.getClass().isAnnotationPresent(RequerCodigoSeguranca.class);
    }
}
