package br.com.controlefinanceiro.requisicao.servicos;

import javax.inject.Inject;

import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.funcionalidade.dao.FuncionalidadeDAO;
import br.com.controlefinanceiro.json.servicos.JsonService;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;
import br.com.controlefinanceiro.requisicao.entidade.Requisicao;
import br.com.controlefinanceiro.seguranca.servicos.CriptografiaSimetricaService;

public class RequisicaoServiceImpl implements RequisicaoService
{
    @Inject
    private FuncionalidadeDAO funcionalidadeDAO;

    @Inject
    private JsonService jsonService;

    @Inject
    private CriptografiaSimetricaService criptografiaSimetricaService;

    @Override
    public Requisicao parseToEntity(RequisicaoDTO<?> requisicaoDTO) throws InfraestruturaException
    {
        Requisicao requisicao = new Requisicao();

        requisicao.setUsuario(requisicaoDTO.getUsuario());
        requisicao.setFuncionalidade(funcionalidadeDAO.buscaPorId(requisicaoDTO.getFuncionalidade().getId()).orElse(null));
        requisicao.setConcluida(false);
        requisicao.setCorpo(criptografiaSimetricaService.criptografa(jsonService.serializa(requisicaoDTO)));
        requisicao.setIpOrigem(requisicaoDTO.getIpOrigem());

        return requisicao;
    }

    @Override
    public <T extends RequisicaoDTO<?>> T parseToDTO(Requisicao requisicao, Class<T> tClass) throws InfraestruturaException
    {
        T requisicaoDTO = jsonService.deserializa(criptografiaSimetricaService.descriptografa(requisicao.getCorpo()), tClass);

        requisicaoDTO.setFuncionalidade(requisicao.getFuncionalidade());
        requisicaoDTO.setUsuario(requisicao.getUsuario());
        requisicaoDTO.setIpOrigem(requisicao.getIpOrigem());

        return requisicaoDTO;
    }
}
