package br.com.controlefinanceiro.requisicao.processador;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;

public abstract class AbstractProcessadorRequisicao<REQUISICAO extends RequisicaoDTO<RESPOSTA>, RESPOSTA extends RespostaDTO>
{

    public RESPOSTA processa(REQUISICAO requisicao) throws InfraestruturaException, NegocioException
    {
        validaRequisitosAcesso(requisicao);

        requisicao.criaResposta();

        processaRequisicao(requisicao, requisicao.getResposta());
        return requisicao.getResposta();
    }

    protected abstract void processaRequisicao(REQUISICAO requisicao, RESPOSTA resposta) throws NegocioException, InfraestruturaException;

    private void validaRequisitosAcesso(REQUISICAO requisicao) throws NegocioException
    {
        Boolean autenticacaoObrigatoria = requisicao.getFuncionalidade().getAutenticacaoObrigatoria();
        if (autenticacaoObrigatoria && requisicao.getUsuario() == null)
        {
            throw new NegocioException(Erro.AUTENTICACAO_OBRIGATORIA);
        }
        if (autenticacaoObrigatoria && !usuarioTemPermissao(requisicao))
        {
            throw new NegocioException(Erro.SEM_PERMISSAO);
        }
    }

    private boolean usuarioTemPermissao(REQUISICAO requisicao)
    {
        return requisicao.getUsuario().getGrupo().getFuncionalidades().contains(requisicao.getFuncionalidade());
    }
}
