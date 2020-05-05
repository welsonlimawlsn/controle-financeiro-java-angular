package br.com.controlefinanceiro.conta.processador.remocao;

import javax.inject.Inject;

import br.com.controlefinanceiro.conta.dao.ContaDAO;
import br.com.controlefinanceiro.conta.dto.remocao.RemoveContaUsuarioRequisicaoDTO;
import br.com.controlefinanceiro.conta.dto.remocao.RemoveContaUsuarioRespostaDTO;
import br.com.controlefinanceiro.conta.entidade.Conta;
import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.funcionalidade.constante.Funcionalidades;
import br.com.controlefinanceiro.requisicao.anotacao.Processador;
import br.com.controlefinanceiro.requisicao.processador.AbstractProcessadorRequisicao;

@Processador(Funcionalidades.REMOVE_CONTA_USUARIO)
public class RemoveContaUsuarioProcessador extends AbstractProcessadorRequisicao<RemoveContaUsuarioRequisicaoDTO, RemoveContaUsuarioRespostaDTO>
{
    @Inject
    private ContaDAO contaDAO;

    @Override
    protected void processaRequisicao(RemoveContaUsuarioRequisicaoDTO requisicao, RemoveContaUsuarioRespostaDTO resposta) throws NegocioException, InfraestruturaException
    {
        contaDAO.remove(buscaConta(requisicao));
    }

    @Override
    public void realizaPreValidacao(RemoveContaUsuarioRequisicaoDTO requisicao) throws NegocioException
    {
        buscaConta(requisicao);
    }

    private Conta buscaConta(RemoveContaUsuarioRequisicaoDTO requisicao) throws NegocioException
    {
        return contaDAO.buscaPorIdEUsuario(requisicao.getCodigoConta(), requisicao.getUsuario())
                .orElseThrow(() -> new NegocioException(Erro.CONTA_NAO_ENCONTRADA));
    }
}
