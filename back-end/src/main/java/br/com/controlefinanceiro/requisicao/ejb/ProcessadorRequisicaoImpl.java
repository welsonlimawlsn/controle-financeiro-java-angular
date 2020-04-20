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
import br.com.controlefinanceiro.relatorio.anotacao.EmiteRelatorio;
import br.com.controlefinanceiro.relatorio.processador.ProcessadorRelatorio;
import br.com.controlefinanceiro.requisicao.anotacao.ProcessadorSelector;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;
import br.com.controlefinanceiro.requisicao.processador.AbstractProcessadorRequisicao;
import br.com.controlefinanceiro.requisicao.servicos.RequisicaoService;

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
    private RequisicaoService requisicaoService;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public <REQUISICAO extends RequisicaoDTO<RESPOSTA>, RESPOSTA extends RespostaDTO> RESPOSTA processa(REQUISICAO requisicao)
            throws InfraestruturaException, NegocioException
    {
        try
        {

            if (requisicao.getFuncionalidade() == null)
            {
                throw new InfraestruturaException(Erro.FUNCIONALIDADE_NULA);
            }

            CodigoSeguranca codigoSeguranca = null;

            AbstractProcessadorRequisicao<REQUISICAO, RESPOSTA> processadorRequisicao =
                    (AbstractProcessadorRequisicao<REQUISICAO, RESPOSTA>) processadoresRequisicao
                            .select(new ProcessadorSelector(requisicao.getFuncionalidade().getId())).get();

            if (requisicao.getCodigoSeguranca() == null || requisicao.getCodigoSeguranca().isEmpty())
            {
                validaRequisicaoConstraints(requisicao);
                processadorRequisicao.realizaPreValidacao(requisicao);
            }
            else
            {
                codigoSeguranca = processadorCodigoSeguranca.processaCodigoSegurancaExistente(requisicao);
                requisicao = (REQUISICAO) requisicaoService.parseToDTO(codigoSeguranca.getRequisicao(), requisicao.getClass());
            }

            if (requisicao.getClass().isAnnotationPresent(RequerCodigoSeguranca.class) && codigoSeguranca == null)
            {
                processadorCodigoSeguranca.processaNovoCodigoSeguranca(requisicao);
                throw new NegocioException(Erro.CODIGO_SEGURANCA_OBRIGATORIO);
            }

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

    private Map<Class<? extends Annotation>, Processa> getOutrosProcessadores()
    {
        Map<Class<? extends Annotation>, Processa> processadores = new HashMap<>();

        processadores.put(EnviaEmail.class, (requisicao, resposta) -> processadorEmail.processaEmail(resposta));
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
