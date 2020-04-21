package br.com.controlefinanceiro.requisicao.interceptor;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import br.com.controlefinanceiro.dispositivo.dao.DispositivoDAO;
import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.funcionalidade.dao.FuncionalidadeDAO;
import br.com.controlefinanceiro.funcionalidade.entidade.Funcionalidade;
import br.com.controlefinanceiro.requisicao.anotacao.ImplementacaoEndpoint;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;
import br.com.controlefinanceiro.token.servico.TokenService;

@Interceptor
@ImplementacaoEndpoint
public class ImplementacaoEndpointInterceptor
{

    @Inject
    @Any
    private Instance<RegraCodigoSeguranca> regras;

    @Inject
    private FuncionalidadeDAO funcionalidadeDAO;

    @Inject
    private TokenService tokenService;

    @Inject
    private HttpServletRequest httpServletRequest;

    @Inject
    private DispositivoDAO dispositivoDAO;

    @AroundInvoke
    public Object aroundInvoke(InvocationContext context) throws Exception
    {
        validaMetodo(context);

        RequisicaoDTO<?> requisicao = getRequisicao(context);

        requisicao.setFuncionalidade(getFuncionalidade(context));
        requisicao.setUsuario(tokenService.getUsuario());
        requisicao.setIpOrigem(httpServletRequest.getRemoteAddr());

        setDispositivo(requisicao);

        validaIpVersusDispositivo(requisicao);

        determinaSePrecisaCodigoSeguraca(requisicao);

        return context.proceed();
    }

    private void validaIpVersusDispositivo(RequisicaoDTO<?> requisicao) throws NegocioException
    {
        if (requisicao.getDispositivo() != null)
        {
            if (!requisicao.getDispositivo().getIp().equals(requisicao.getIpOrigem()))
            {
                throw new NegocioException(Erro.DISPOSITIVO_INVALIDO);
            }
        }
    }

    private void determinaSePrecisaCodigoSeguraca(RequisicaoDTO<?> requisicao) throws NegocioException
    {
        if (requisicao.getCodigoSeguranca() != null && !requisicao.getCodigoSeguranca().isEmpty())
        {
            requisicao.setObrigadoCodigoSeguranca(false);
            return;
        }
        for (RegraCodigoSeguranca regra : regras)
        {
            if (regra.verificaRegra(requisicao))
            {
                requisicao.setObrigadoCodigoSeguranca(true);
                return;
            }
        }
        requisicao.setObrigadoCodigoSeguranca(false);
    }

    private void setDispositivo(RequisicaoDTO<?> requisicao) throws NegocioException
    {
        String headerIdDispositivo = httpServletRequest.getHeader("Id-Dispositivo");
        if (headerIdDispositivo != null && !headerIdDispositivo.isEmpty())
        {
            requisicao.setDispositivo(dispositivoDAO.buscaPorId(UUID.fromString(headerIdDispositivo)).orElse(null));
            if (requisicao.getDispositivo() != null && !requisicao.getDispositivo().getIp().equals(requisicao.getIpOrigem()))
            {
                throw new NegocioException(Erro.DISPOSITIVO_INVALIDO);
            }
        }
    }

    private void validaMetodo(InvocationContext context) throws InfraestruturaException
    {
        if (context.getParameters().length != 1)
        {
            throw new InfraestruturaException(Erro.NUMERO_INVALIDO_PARAMENTROS);
        }
        if (!context.getMethod().getParameters()[0].isAnnotationPresent(br.com.controlefinanceiro.funcionalidade.anotacao.Funcionalidade.class))
        {
            throw new InfraestruturaException(Erro.SEM_ANOTACAO_FUNCIONALIDADE);
        }
    }

    private Funcionalidade getFuncionalidade(InvocationContext context) throws InfraestruturaException
    {
        br.com.controlefinanceiro.funcionalidade.anotacao.Funcionalidade anotacao =
                context.getMethod().getParameters()[0].getAnnotation(br.com.controlefinanceiro.funcionalidade.anotacao.Funcionalidade.class);
        return funcionalidadeDAO.buscaPorId(anotacao.value())
                .orElseThrow(() -> new InfraestruturaException(Erro.FUNCIONALIDADE_NAO_ENCONTRADA, String.valueOf(anotacao.value())));
    }

    private RequisicaoDTO<?> getRequisicao(InvocationContext context) throws InfraestruturaException
    {
        RequisicaoDTO<?> requisicao = null;
        try
        {
            requisicao = (RequisicaoDTO<?>) context.getParameters()[0];
            if (requisicao == null)
            {
                requisicao = (RequisicaoDTO<?>) context.getMethod().getParameters()[0].getType().getConstructor().newInstance();
                context.getParameters()[0] = requisicao;
            }
        }
        catch (ClassCastException e)
        {
            throw new InfraestruturaException(Erro.SEM_HERANCA_REQUISICAO, e);
        }
        catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return requisicao;
    }
}
