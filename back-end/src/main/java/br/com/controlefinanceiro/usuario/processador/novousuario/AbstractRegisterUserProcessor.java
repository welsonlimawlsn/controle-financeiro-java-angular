package br.com.controlefinanceiro.usuario.processador.novousuario;

import javax.inject.Inject;

import br.com.controlefinanceiro.criptografia.servico.CriptografiaService;
import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.grupo.entidade.Grupo;
import br.com.controlefinanceiro.requisicao.processador.AbstractProcessadorRequisicao;
import br.com.controlefinanceiro.usuario.dao.UsuarioDAO;
import br.com.controlefinanceiro.usuario.dto.novo.AbstractNovoUsuarioRequisicaoDTO;
import br.com.controlefinanceiro.usuario.dto.novo.AbstractNovoUsuarioRespostaDTO;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

public abstract class AbstractRegisterUserProcessor<REQUISICAO extends AbstractNovoUsuarioRequisicaoDTO<RESPOSTA>, RESPOSTA extends AbstractNovoUsuarioRespostaDTO> extends AbstractProcessadorRequisicao<REQUISICAO, RESPOSTA>
{
    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private CriptografiaService criptografiaService;

    @Override
    protected void processaRequisicao(REQUISICAO requisicao, RESPOSTA resposta) throws NegocioException
    {
        Usuario usuario = new Usuario();

        usuario.setNome(requisicao.getNome());
        usuario.setSobrenome(requisicao.getSobrenome());
        usuario.setEmail(requisicao.getEmail());
        usuario.setSenha(criptografiaService.hashSenha(requisicao.getSenha()));
        usuario.setGrupo(getGroup(requisicao));

        usuarioDAO.salva(usuario);

        resposta.setUsuario(usuario);
        resposta.setEmail(requisicao.getEmail());

        requisicao.setUsuario(usuario);
    }

    @Override
    public void realizaPreValidacao(REQUISICAO requisicao) throws NegocioException
    {
        if (usuarioDAO.existeEmail(requisicao.getEmail()))
        {
            throw new NegocioException(Erro.EMAIL_JA_EXISTENTE);
        }
    }

    protected abstract Grupo getGroup(REQUISICAO requisisao);
}
