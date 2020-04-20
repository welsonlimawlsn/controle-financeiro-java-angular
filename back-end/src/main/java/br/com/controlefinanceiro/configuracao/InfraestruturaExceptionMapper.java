package br.com.controlefinanceiro.configuracao;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;
import java.util.List;

import br.com.controlefinanceiro.exception.InfraestruturaException;

@Provider
public class InfraestruturaExceptionMapper implements ExceptionMapper<InfraestruturaException>
{
    private static final Logger LOGGER = Logger.getLogger(InfraestruturaExceptionMapper.class);

    @Override
    public Response toResponse(InfraestruturaException exception)
    {
        LOGGER.error(exception.getMessage(), exception);
        ErrorResponse error = new ErrorResponse();
        error.setMessages(Collections.singletonList("Erro interno"));
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Getter
    @Setter
    private static class ErrorResponse
    {
        private List<String> messages;
    }
}
