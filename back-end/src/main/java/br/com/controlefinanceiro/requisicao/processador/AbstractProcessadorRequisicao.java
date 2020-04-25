package br.com.controlefinanceiro.requisicao.processador;

import javax.inject.Inject;

import br.com.controlefinanceiro.dispositivo.dao.DispositivoDAO;
import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;
import br.com.controlefinanceiro.usuario.dao.UsuarioDAO;

public abstract class AbstractProcessadorRequisicao<REQUISICAO extends RequisicaoDTO<RESPOSTA>, RESPOSTA extends RespostaDTO>
{
    @Inject
    private DispositivoDAO dispositivoDAO;

    @Inject
    private UsuarioDAO usuarioDAO;

    public RESPOSTA processa(REQUISICAO requisicao) throws InfraestruturaException, NegocioException
    {
        validaRequisitosAcesso(requisicao);

        requisicao.criaResposta();

        processaRequisicao(requisicao, requisicao.getResposta());

        relacionaUsuarioComDispositivoCasoNecessario(requisicao);

        return requisicao.getResposta();
    }

    private void relacionaUsuarioComDispositivoCasoNecessario(REQUISICAO requisicao)
    {
        if (requisicao.getUsuario() != null)
        {
            requisicao.getUsuario().addDispositivo(dispositivoDAO.buscaPorId(requisicao.getDispositivo().getId()).get());
        }
    }

    protected abstract void processaRequisicao(REQUISICAO requisicao, RESPOSTA resposta) throws NegocioException, InfraestruturaException;

    public abstract void realizaPreValidacao(REQUISICAO requisicao) throws NegocioException;

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
