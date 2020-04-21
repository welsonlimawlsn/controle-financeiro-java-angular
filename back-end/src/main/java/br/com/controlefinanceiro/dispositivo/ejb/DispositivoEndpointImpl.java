package br.com.controlefinanceiro.dispositivo.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.controlefinanceiro.dispositivo.dto.NovoDispositivoRequisicaoDTO;
import br.com.controlefinanceiro.dispositivo.dto.NovoDispositivoRespostaDTO;
import br.com.controlefinanceiro.dispositivo.endpoint.DispositivoEndpoint;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.funcionalidade.anotacao.Funcionalidade;
import br.com.controlefinanceiro.funcionalidade.constante.Funcionalidades;
import br.com.controlefinanceiro.requisicao.anotacao.ImplementacaoEndpoint;
import br.com.controlefinanceiro.requisicao.ejb.ProcessadorRequisicao;

@Stateless
@ImplementacaoEndpoint
public class DispositivoEndpointImpl implements DispositivoEndpoint
{
    @EJB
    private ProcessadorRequisicao processadorRequisicao;

    @Override
    public NovoDispositivoRespostaDTO novoDispositivo(
            @Funcionalidade(Funcionalidades.NOVO_DISPOSITIVO) NovoDispositivoRequisicaoDTO requisicao)
            throws InfraestruturaException, NegocioException
    {
        return processadorRequisicao.processa(requisicao);
    }
}
