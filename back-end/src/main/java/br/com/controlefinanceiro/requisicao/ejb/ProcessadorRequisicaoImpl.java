package br.com.controlefinanceiro.requisicao.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.controlefinanceiro.codigoconfirmacao.anotacao.RequerCodigoSeguranca;
import br.com.controlefinanceiro.codigoconfirmacao.ejb.ProcessadorCodigoSeguranca;
import br.com.controlefinanceiro.codigoconfirmacao.entidade.CodigoSeguranca;
import br.com.controlefinanceiro.email.anotacao.EnviaEmail;
import br.com.controlefinanceiro.email.processador.ProcessadorEmail;
import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.json.servicos.JsonService;
import br.com.controlefinanceiro.relatorio.anotacao.EmiteRelatorio;
import br.com.controlefinanceiro.relatorio.processador.ProcessadorRelatorio;
import br.com.controlefinanceiro.requisicao.anotacao.ProcessadorSelector;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;
import br.com.controlefinanceiro.requisicao.entidade.Requisicao;
import br.com.controlefinanceiro.requisicao.processador.AbstractProcessadorRequisicao;

@Stateless
public class ProcessadorRequisicaoImpl implements ProcessadorRequisicao
{
    private final Map<Class<? extends Annotation>, Processa> outrosProcessadores = getOutrosProcessadores();

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @EJB
    private ProcessadorEmail processadorEmail;

    @EJB
    private ProcessadorCodigoSeguranca processadorCodigoSeguranca;

    @Inject
    @Any
    private Instance<AbstractProcessadorRequisicao<?, ?>> processadoresRequisicao;

    @Inject
    private ProcessadorRelatorio processadorRelatorio;

    @Inject
    private JsonService jsonService;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public <REQUISICAO extends RequisicaoDTO<RESPOSTA>, RESPOSTA extends RespostaDTO> RESPOSTA processa(REQUISICAO requisicao)
            throws InfraestruturaException, NegocioException
    {

        if (requisicao.getFuncionalidade() == null)
        {
            throw new InfraestruturaException(Erro.FUNCIONALIDADE_NULA);
        }

        CodigoSeguranca codigoSeguranca = null;

        if (requisicao.getCodigoSeguranca() == null || requisicao.getCodigoSeguranca().isEmpty())
        {
            validaRequisicaoConstraints(requisicao);
        }
        else
        {
            codigoSeguranca = processadorCodigoSeguranca.processaCodigoSegurancaExistente(requisicao);
            requisicao = getRequisicaoCodigoSeguranca(codigoSeguranca, requisicao);
        }

        if (requisicao.getClass().isAnnotationPresent(RequerCodigoSeguranca.class) && codigoSeguranca == null)
        {
            processadorCodigoSeguranca.processaNovoCodigoSeguranca(requisicao);
            throw new NegocioException(Erro.CODIGO_SEGURANCA_OBRIGATORIO);
        }

        try
        {
            AbstractProcessadorRequisicao<REQUISICAO, RESPOSTA> processadorRequisicao =
                    (AbstractProcessadorRequisicao<REQUISICAO, RESPOSTA>) processadoresRequisicao
                            .select(new ProcessadorSelector(requisicao.getFuncionalidade().getId())).get();

            RESPOSTA resposta = processadorRequisicao.processa(requisicao);

            for (Map.Entry<Class<? extends Annotation>, Processa> entry : outrosProcessadores.entrySet())
            {
                Annotation annotation = resposta.getClass().getAnnotation(entry.getKey());
                if (annotation != null)
                {
                    entry.getValue().processa(requisicao, resposta);
                }
            }

            return resposta;
        }
        catch (UnsatisfiedResolutionException e)
        {
            throw new InfraestruturaException(Erro.PROCESSADOR_NAO_ENCONTRADO, e, requisicao.getFuncionalidade().getNome());
        }
    }

    private <REQUISICAO extends RequisicaoDTO<RESPOSTA>, RESPOSTA extends RespostaDTO> REQUISICAO getRequisicaoCodigoSeguranca(CodigoSeguranca codigoSeguranca, REQUISICAO requisicaoDTO) throws InfraestruturaException
    {
        Requisicao requisicao = codigoSeguranca.getRequisicao();

        REQUISICAO novaRequisicao = (REQUISICAO) jsonService.deserializa(requisicao.getCorpo(), requisicaoDTO.getClass());

        novaRequisicao.setIpOrigem(requisicaoDTO.getIpOrigem());
        novaRequisicao.setUsuario(requisicaoDTO.getUsuario());
        novaRequisicao.setFuncionalidade(requisicaoDTO.getFuncionalidade());
        novaRequisicao.setResposta(requisicaoDTO.getResposta());

        return novaRequisicao;
    }

    private Map<Class<? extends Annotation>, Processa> getOutrosProcessadores()
    {
        Map<Class<? extends Annotation>, Processa> processadores = new HashMap<>();

        processadores.put(EnviaEmail.class, (requisicao, resposta) -> {
            resposta.setTemplateEmail(resposta.getClass().getAnnotation(EnviaEmail.class).vm());
            processadorEmail.processaEmail(resposta);
        });
        processadores.put(EmiteRelatorio.class, (requisicao, resposta) -> processadorRelatorio.processaRelatorio(resposta));

        return processadores;
    }

    private void validaRequisicaoConstraints(RequisicaoDTO<?> requisicao) throws NegocioException, InfraestruturaException
    {
        Set<ConstraintViolation<RequisicaoDTO<?>>> constraintViolations = VALIDATOR.validate(requisicao);
        if (!constraintViolations.isEmpty())
        {
            throw new NegocioException(constraintViolations.stream()
                    .map(ConstraintViolation::getMessage).collect(Collectors.toList()), Erro.INFORMACOES_INVALIDAS);
        }
        requisicao.validaRequisicao();
    }

    private interface Processa
    {
        void processa(RequisicaoDTO<?> requisicao, RespostaDTO resposta) throws InfraestruturaException;
    }

}
