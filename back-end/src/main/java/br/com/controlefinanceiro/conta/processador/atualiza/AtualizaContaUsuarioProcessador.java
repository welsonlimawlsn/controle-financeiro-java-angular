package br.com.controlefinanceiro.conta.processador.atualiza;

import javax.inject.Inject;

import br.com.controlefinanceiro.conta.dao.ContaDAO;
import br.com.controlefinanceiro.conta.dto.atualiza.AtualizaContaUsuarioRequisicaoDTO;
import br.com.controlefinanceiro.conta.dto.atualiza.AtualizaContaUsuarioRespostaDTO;
import br.com.controlefinanceiro.conta.entidade.Conta;
import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.funcionalidade.constante.Funcionalidades;
import br.com.controlefinanceiro.requisicao.anotacao.Processador;
import br.com.controlefinanceiro.requisicao.processador.AbstractProcessadorRequisicao;

@Processador(Funcionalidades.ATUALIZA_CONTA_USUARIO)
public class AtualizaContaUsuarioProcessador extends AbstractProcessadorRequisicao<AtualizaContaUsuarioRequisicaoDTO, AtualizaContaUsuarioRespostaDTO>
{
    @Inject
    private ContaDAO contaDAO;

    @Override
    protected void processaRequisicao(AtualizaContaUsuarioRequisicaoDTO requisicao, AtualizaContaUsuarioRespostaDTO resposta) throws NegocioException, InfraestruturaException
    {
        Conta conta = buscaConta(requisicao);
        conta.setNome(requisicao.getNome());
        conta.setValorInicial(requisicao.getValorInicial());
    }

    @Override
    public void realizaPreValidacao(AtualizaContaUsuarioRequisicaoDTO requisicao) throws NegocioException
    {
        buscaConta(requisicao);
    }

    private Conta buscaConta(AtualizaContaUsuarioRequisicaoDTO requisicao) throws NegocioException
    {
        return this.contaDAO.buscaPorIdEUsuario(requisicao.getId(), requisicao.getUsuario())
                .orElseThrow(() -> new NegocioException(Erro.CONTA_NAO_ENCONTRADA));
    }
}
