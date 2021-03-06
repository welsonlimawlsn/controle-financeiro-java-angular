package br.com.controlefinanceiro.codigoconfirmacao.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import br.com.controlefinanceiro.codigoconfirmacao.dao.CodigoSegurancaDAO;
import br.com.controlefinanceiro.codigoconfirmacao.entidade.CodigoSeguranca;
import br.com.controlefinanceiro.email.processador.ProcessadorEmail;
import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.requisicao.dao.RequisicaoDAO;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;
import br.com.controlefinanceiro.requisicao.entidade.Requisicao;
import br.com.controlefinanceiro.requisicao.servicos.RequisicaoService;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProcessadorCodigoSegurancaImpl implements ProcessadorCodigoSeguranca
{

    private static final String TEMPLATE = "codigo_seguranca.vm";

    private static final int QUANTIDADE_NUMEROS = 8;

    private static final int TEMPO_EXPIRACAO_CODIGO_SEGURANCA = 15;

    private static final String SUBJECT = "Código de segurança - Fintips";

    @EJB
    private ProcessadorEmail processadorEmail;

    @Inject
    private CodigoSegurancaDAO codigoSegurancaDAO;

    @Inject
    private RequisicaoDAO requisicaoDAO;

    @Inject
    private RequisicaoService requisicaoService;

    @Override
    public void processaNovoCodigoSeguranca(RequisicaoDTO<?> requisicaoDTO) throws InfraestruturaException
    {
        try
        {
            Requisicao requisicao = criaRequisicao(requisicaoDTO);

            CodigoSeguranca codigoSeguranca = criaCodigoSeguranca(requisicao);

            requisicaoDTO.criaResposta();

            RespostaDTO resposta = requisicaoDTO.getResposta();

            resposta.setCodigoSeguranca(codigoSeguranca.getCodigo());

            processadorEmail.processaEmail(getEmail(requisicaoDTO), null, SUBJECT, TEMPLATE, resposta);
        }
        catch (Exception e)
        {
            throw new InfraestruturaException(Erro.ERRO_AO_CRIAR_NOVO_CODIGO_SEGURANCA, e);
        }
    }

    private List<String> getEmail(RequisicaoDTO<?> requisicaoDTO)
    {
        if (requisicaoDTO.getUsuario() != null)
        {
            return Collections.singletonList(requisicaoDTO.getUsuario().getEmail());
        }
        return Collections.singletonList(requisicaoDTO.getEmail());
    }

    @Override
    public CodigoSeguranca processaCodigoSegurancaExistente(RequisicaoDTO<?> requisicao) throws NegocioException
    {
        CodigoSeguranca codigoSeguranca = codigoSegurancaDAO
                .buscaCodigoSegurancaValido(requisicao.getCodigoSeguranca(), requisicao.getFuncionalidade(),
                        requisicao.getDispositivo(), requisicao.getUsuario())
                .orElseThrow(() -> new NegocioException(Erro.CODIGO_SEGURANCA_INVALIDO));

        if (codigoSeguranca.getUsuario() != null && !codigoSeguranca.getUsuario().equals(requisicao.getUsuario()))
        {
            throw new NegocioException(Erro.CODIGO_SEGURANCA_INVALIDO);
        }

        codigoSeguranca.usa();
        codigoSeguranca.getRequisicao().conclui();

        return codigoSeguranca;
    }

    private CodigoSeguranca criaCodigoSeguranca(Requisicao requisicao)
    {
        CodigoSeguranca codigoSeguranca = new CodigoSeguranca();

        codigoSeguranca.setFuncionalidade(requisicao.getFuncionalidade());
        codigoSeguranca.setRequisicao(requisicao);
        codigoSeguranca.setUsado(false);
        codigoSeguranca.setUsuario(requisicao.getUsuario());
        codigoSeguranca.setValidade(ZonedDateTime.now().plusMinutes(TEMPO_EXPIRACAO_CODIGO_SEGURANCA));
        codigoSeguranca.setDispositivo(requisicao.getDispositivo());

        codigoSeguranca.geraCodigo(QUANTIDADE_NUMEROS);

        codigoSegurancaDAO.salva(codigoSeguranca);

        return codigoSeguranca;
    }

    private Requisicao criaRequisicao(RequisicaoDTO<?> requisicaoDTO) throws InfraestruturaException
    {

        Requisicao requisicao = requisicaoService.parseToEntity(requisicaoDTO);

        requisicaoDAO.salva(requisicao);

        return requisicao;
    }
}
