package br.com.controlefinanceiro.usuario.processador.login;

import javax.inject.Inject;

import br.com.controlefinanceiro.criptografia.servico.CriptografiaService;
import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.funcionalidade.constante.Funcionalidades;
import br.com.controlefinanceiro.requisicao.anotacao.Processador;
import br.com.controlefinanceiro.requisicao.processador.AbstractProcessadorRequisicao;
import br.com.controlefinanceiro.token.servico.TokenService;
import br.com.controlefinanceiro.usuario.dao.UsuarioDAO;
import br.com.controlefinanceiro.usuario.dto.UsuarioDTO;
import br.com.controlefinanceiro.usuario.dto.login.LoginUsuarioRequisicaoDTO;
import br.com.controlefinanceiro.usuario.dto.login.LoginUsuarioRespostaDTO;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

@Processador(Funcionalidades.LOGIN_USUARIO)
public class LoginUsuarioProcessador extends AbstractProcessadorRequisicao<LoginUsuarioRequisicaoDTO, LoginUsuarioRespostaDTO>
{
    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private CriptografiaService criptografiaService;

    @Inject
    private TokenService tokenService;

    @Override
    protected void processaRequisicao(LoginUsuarioRequisicaoDTO requisicao, LoginUsuarioRespostaDTO resposta) throws NegocioException, InfraestruturaException
    {
        Usuario usuario = buscaUsuario(requisicao);
        validaSenha(requisicao, usuario);
        resposta.setUsuario(UsuarioDTO.builder().nome(usuario.getNome()).sobrenome(usuario.getSobrenome()).email(usuario.getEmail()).build());
        resposta.setToken(tokenService.geraToken(usuario));

        requisicao.setUsuario(usuario);
    }

    @Override
    public void realizaPreValidacao(LoginUsuarioRequisicaoDTO requisicao) throws NegocioException
    {
        Usuario usuario = buscaUsuario(requisicao);
        validaSenha(requisicao, usuario);
    }

    private Usuario buscaUsuario(LoginUsuarioRequisicaoDTO requisicao) throws NegocioException
    {
        return usuarioDAO.buscaPorEmail(requisicao.getEmail()).orElseThrow(() -> new NegocioException(Erro.EMAIL_OU_SENHA_INVALIDOS));
    }

    private void validaSenha(LoginUsuarioRequisicaoDTO requisicao, Usuario usuario) throws NegocioException
    {
        if (!criptografiaService.validaSenha(requisicao.getSenha(), usuario.getSenha()))
        {
            throw new NegocioException(Erro.EMAIL_OU_SENHA_INVALIDOS);
        }
    }
}
