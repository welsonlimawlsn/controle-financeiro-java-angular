package br.com.controlefinanceiro.configuracao;

import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.controlefinanceiro.exception.AbstractException;
import br.com.controlefinanceiro.exception.NegocioException;

@Provider
public class NegocioExceptionMapper implements ExceptionMapper<NegocioException>
{
    private static final Logger LOGGER = Logger.getLogger(NegocioExceptionMapper.class);

    @Override
    public Response toResponse(NegocioException exception)
    {
        LOGGER.error(exception.getMessage(), exception);
        AbstractException.ErrorResponse error = new AbstractException.ErrorResponse();
        error.setErros(
                exception.getMensagens() != null && !exception.getMensagens().isEmpty() ? getErrors(exception) : getError(exception));
        return Response.status(exception.getErro().getStatus()).entity(error).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    private List<AbstractException.Error> getError(NegocioException exception)
    {
        return Collections.singletonList(new AbstractException.Error(exception.getMessage(), exception.getErro().getCodigo()));
    }

    private List<AbstractException.Error> getErrors(NegocioException exception)
    {
        return exception.getMensagens().stream()
                .map(mensagem -> new AbstractException.Error(mensagem, exception.getErro().getCodigo()))
                .collect(Collectors.toList());
    }
}
