package br.com.controlefinanceiro.conta.endpoint;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.controlefinanceiro.conta.dto.atualiza.AtualizaContaUsuarioRequisicaoDTO;
import br.com.controlefinanceiro.conta.dto.atualiza.AtualizaContaUsuarioRespostaDTO;
import br.com.controlefinanceiro.conta.dto.consulta.ConsultaContasUsuarioRequisicaoDTO;
import br.com.controlefinanceiro.conta.dto.consulta.ConsultaContasUsuarioRespostaDTO;
import br.com.controlefinanceiro.conta.dto.nova.NovaContaRequisicaoDTO;
import br.com.controlefinanceiro.conta.dto.nova.NovaContaRespostaDTO;
import br.com.controlefinanceiro.conta.dto.remocao.RemoveContaUsuarioRequisicaoDTO;
import br.com.controlefinanceiro.conta.dto.remocao.RemoveContaUsuarioRespostaDTO;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;

@Path("/conta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ContaEndpoint
{
    @POST
    NovaContaRespostaDTO novaConta(NovaContaRequisicaoDTO requisicao) throws InfraestruturaException, NegocioException;

    @GET
    ConsultaContasUsuarioRespostaDTO consultaContasUsuario(ConsultaContasUsuarioRequisicaoDTO requisicao) throws InfraestruturaException, NegocioException;

    @Path("/{codigoConta}")
    @DELETE
    RemoveContaUsuarioRespostaDTO removeContaUsuario(@BeanParam RemoveContaUsuarioRequisicaoDTO requisicao) throws InfraestruturaException, NegocioException;

    @PUT
    AtualizaContaUsuarioRespostaDTO atualizaContaUsuario(AtualizaContaUsuarioRequisicaoDTO requisicao) throws InfraestruturaException, NegocioException;
}
