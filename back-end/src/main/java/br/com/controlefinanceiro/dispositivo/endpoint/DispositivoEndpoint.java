package br.com.controlefinanceiro.dispositivo.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.controlefinanceiro.dispositivo.dto.NovoDispositivoRequisicaoDTO;
import br.com.controlefinanceiro.dispositivo.dto.NovoDispositivoRespostaDTO;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;

@Path("/dispositivo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface DispositivoEndpoint
{
    @POST
    NovoDispositivoRespostaDTO novoDispositivo(NovoDispositivoRequisicaoDTO requisicao) throws InfraestruturaException, NegocioException;
}
