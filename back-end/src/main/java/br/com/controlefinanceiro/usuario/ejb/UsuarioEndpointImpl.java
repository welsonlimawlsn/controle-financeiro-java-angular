package br.com.controlefinanceiro.usuario.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.funcionalidade.anotacao.Funcionalidade;
import br.com.controlefinanceiro.funcionalidade.constante.Funcionalidades;
import br.com.controlefinanceiro.requisicao.anotacao.ImplementacaoEndpoint;
import br.com.controlefinanceiro.requisicao.ejb.ProcessadorRequisicao;
import br.com.controlefinanceiro.usuario.dto.login.LoginUsuarioRequisicaoDTO;
import br.com.controlefinanceiro.usuario.dto.login.LoginUsuarioRespostaDTO;
import br.com.controlefinanceiro.usuario.dto.novo.NovoUsuarioDesprotegidoRequisicaoDTO;
import br.com.controlefinanceiro.usuario.dto.novo.NovoUsuarioDesprotegidoRespostaDTO;
import br.com.controlefinanceiro.usuario.dto.novo.NovoUsuarioProtegidoRequisicaoDTO;
import br.com.controlefinanceiro.usuario.dto.novo.NovoUsuarioProtegidoRespostaDTO;
import br.com.controlefinanceiro.usuario.endpoint.UsuarioEndpoint;

@Stateless
@ImplementacaoEndpoint
public class UsuarioEndpointImpl implements UsuarioEndpoint
{

    @EJB
    private ProcessadorRequisicao processadorRequisicao;

    @Override
    public NovoUsuarioDesprotegidoRespostaDTO novoUsuario(@Funcionalidade(Funcionalidades.NOVO_USUARIO) NovoUsuarioDesprotegidoRequisicaoDTO requisicao)
            throws InfraestruturaException, NegocioException
    {
        return processadorRequisicao.processa(requisicao);
    }

    @Override
    public NovoUsuarioProtegidoRespostaDTO novoUsuarioProtegido(@Funcionalidade(Funcionalidades.NOVO_USUARIO_PROTEGIDO) NovoUsuarioProtegidoRequisicaoDTO requisicao)
            throws InfraestruturaException, NegocioException
    {
        return processadorRequisicao.processa(requisicao);
    }

    @Override
    public LoginUsuarioRespostaDTO loginUsuario(@Funcionalidade(Funcionalidades.LOGIN_USUARIO) LoginUsuarioRequisicaoDTO requisicao)
            throws InfraestruturaException, NegocioException
    {
        return processadorRequisicao.processa(requisicao);
    }
}
