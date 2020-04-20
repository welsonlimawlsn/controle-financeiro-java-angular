package br.com.controlefinanceiro.usuario.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.usuario.dto.login.LoginUsuarioRequisicaoDTO;
import br.com.controlefinanceiro.usuario.dto.login.LoginUsuarioRespostaDTO;
import br.com.controlefinanceiro.usuario.dto.novo.NovoUsuarioDesprotegidoRequisicaoDTO;
import br.com.controlefinanceiro.usuario.dto.novo.NovoUsuarioDesprotegidoRespostaDTO;
import br.com.controlefinanceiro.usuario.dto.novo.NovoUsuarioProtegidoRequisicaoDTO;
import br.com.controlefinanceiro.usuario.dto.novo.NovoUsuarioProtegidoRespostaDTO;

@Path("/usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface UsuarioEndpoint
{
    @POST
    NovoUsuarioDesprotegidoRespostaDTO novoUsuario(NovoUsuarioDesprotegidoRequisicaoDTO requisicao) throws InfraestruturaException, NegocioException;

    @POST
    @Path("/protegido")
    NovoUsuarioProtegidoRespostaDTO novoUsuarioProtegido(NovoUsuarioProtegidoRequisicaoDTO requisicao) throws InfraestruturaException, NegocioException;

    @POST
    @Path("/login")
    LoginUsuarioRespostaDTO loginUsuario(LoginUsuarioRequisicaoDTO requisicao) throws InfraestruturaException, NegocioException;
}
