package br.com.controlefinanceiro.conta.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.controlefinanceiro.conta.dto.consulta.ConsultaContasUsuarioRequisicaoDTO;
import br.com.controlefinanceiro.conta.dto.consulta.ConsultaContasUsuarioRespostaDTO;
import br.com.controlefinanceiro.conta.dto.nova.NovaContaRequisicaoDTO;
import br.com.controlefinanceiro.conta.dto.nova.NovaContaRespostaDTO;
import br.com.controlefinanceiro.conta.endpoint.ContaEndpoint;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.funcionalidade.anotacao.Funcionalidade;
import br.com.controlefinanceiro.funcionalidade.constante.Funcionalidades;
import br.com.controlefinanceiro.requisicao.anotacao.ImplementacaoEndpoint;
import br.com.controlefinanceiro.requisicao.ejb.ProcessadorRequisicao;

@Stateless
@ImplementacaoEndpoint
public class ContaEndpointImpl implements ContaEndpoint
{
    @EJB
    private ProcessadorRequisicao processadorRequisicao;

    @Override
    public NovaContaRespostaDTO novaConta(@Funcionalidade(Funcionalidades.NOVA_CONTA) NovaContaRequisicaoDTO requisicao)
            throws InfraestruturaException, NegocioException
    {
        return processadorRequisicao.processa(requisicao);
    }

    @Override
    public ConsultaContasUsuarioRespostaDTO consultaContasUsuario(
            @Funcionalidade(Funcionalidades.CONSULTA_CONTAS_USUARIO) ConsultaContasUsuarioRequisicaoDTO requisicao)
            throws InfraestruturaException, NegocioException
    {
        return processadorRequisicao.processa(requisicao);
    }
}
