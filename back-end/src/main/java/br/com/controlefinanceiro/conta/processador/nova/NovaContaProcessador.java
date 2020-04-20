package br.com.controlefinanceiro.conta.processador.nova;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Optional;

import br.com.controlefinanceiro.conta.dao.ContaDAO;
import br.com.controlefinanceiro.conta.dto.nova.NovaContaRequisicaoDTO;
import br.com.controlefinanceiro.conta.dto.nova.NovaContaRespostaDTO;
import br.com.controlefinanceiro.conta.entidade.Conta;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.funcionalidade.constante.Funcionalidades;
import br.com.controlefinanceiro.requisicao.anotacao.Processador;
import br.com.controlefinanceiro.requisicao.processador.AbstractProcessadorRequisicao;

@Processador(Funcionalidades.NOVA_CONTA)
public class NovaContaProcessador extends AbstractProcessadorRequisicao<NovaContaRequisicaoDTO, NovaContaRespostaDTO>
{
    @Inject
    private ContaDAO contaDAO;

    @Override
    protected void processaRequisicao(NovaContaRequisicaoDTO requisicao, NovaContaRespostaDTO resposta) throws NegocioException, InfraestruturaException
    {
        Conta conta = new Conta();

        conta.setNome(requisicao.getNome());
        conta.setValorInicial(Optional.ofNullable(requisicao.getValorInicial()).orElse(BigDecimal.ZERO));
        conta.setUsuario(requisicao.getUsuario());

        contaDAO.salva(conta);
    }

    @Override
    public void realizaPreValidacao(NovaContaRequisicaoDTO novaContaRequisicaoDTO) throws NegocioException
    {

    }
}
