package br.com.controlefinanceiro.conta.processador.consulta;

import java.util.List;
import java.util.stream.Collectors;

import br.com.controlefinanceiro.conta.dto.ContaDTO;
import br.com.controlefinanceiro.conta.dto.consulta.ConsultaContasUsuarioRequisicaoDTO;
import br.com.controlefinanceiro.conta.dto.consulta.ConsultaContasUsuarioRespostaDTO;
import br.com.controlefinanceiro.conta.entidade.Conta;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.funcionalidade.constante.Funcionalidades;
import br.com.controlefinanceiro.requisicao.anotacao.Processador;
import br.com.controlefinanceiro.requisicao.processador.AbstractProcessadorRequisicao;

@Processador(Funcionalidades.CONSULTA_CONTAS_USUARIO)
public class ConsultaContasUsuarioProcessador extends AbstractProcessadorRequisicao<ConsultaContasUsuarioRequisicaoDTO, ConsultaContasUsuarioRespostaDTO>
{
    @Override
    protected void processaRequisicao(ConsultaContasUsuarioRequisicaoDTO requisicao, ConsultaContasUsuarioRespostaDTO resposta)
            throws NegocioException, InfraestruturaException
    {
        List<ContaDTO> contas = requisicao.getUsuario().getContas().stream()
                .map(this::criaContaDTO).collect(Collectors.toList());
        resposta.setContas(contas);
    }

    @Override
    public void realizaPreValidacao(ConsultaContasUsuarioRequisicaoDTO requisicao) throws NegocioException
    {
    }

    private ContaDTO criaContaDTO(Conta conta)
    {
        return ContaDTO.builder()
                .id(conta.getId())
                .nome(conta.getNome())
                .valorInicial(conta.getValorInicial())
                .build();
    }
}
