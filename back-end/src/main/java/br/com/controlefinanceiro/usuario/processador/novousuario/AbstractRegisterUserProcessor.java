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
    protected void processaRequisicao(REQUISICAO requisisao, RESPOSTA resposta) throws NegocioException
    {
        if (usuarioDAO.existeEmail(requisisao.getEmail()))
        {
            throw new NegocioException(Erro.EMAIL_JA_EXISTENTE);
        }

        Usuario usuario = new Usuario();

        usuario.setNome(requisisao.getNome());
        usuario.setSobrenome(requisisao.getSobrenome());
        usuario.setEmail(requisisao.getEmail());
        usuario.setSenha(criptografiaService.hashSenha(requisisao.getSenha()));
        usuario.setGrupo(getGroup(requisisao));

        usuarioDAO.salva(usuario);

        resposta.setUsuario(usuario);
        resposta.setEmail(requisisao.getEmail());
    }

    protected abstract Grupo getGroup(REQUISICAO requisisao);
}
