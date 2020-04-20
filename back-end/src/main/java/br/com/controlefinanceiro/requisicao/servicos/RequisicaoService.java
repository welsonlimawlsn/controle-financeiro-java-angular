package br.com.controlefinanceiro.requisicao.servicos;

import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;
import br.com.controlefinanceiro.requisicao.entidade.Requisicao;

public interface RequisicaoService
{
    Requisicao parseToEntity(RequisicaoDTO<?> requisicaoDTO) throws InfraestruturaException;

    <T extends RequisicaoDTO<?>> T parseToDTO(Requisicao requisicao, Class<T> tClass) throws InfraestruturaException;
}
