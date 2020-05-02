package br.com.controlefinanceiro.configuracao;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.parametro.servico.ParametroService;

import static br.com.controlefinanceiro.parametro.constante.Parametros.ACCESS_CONTROL;

@Provider
public class CorsConfiguration implements ContainerResponseFilter
{

    @Inject
    private ParametroService parametroService;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException
    {
        try
        {
            String accessControl = parametroService.getParametro(ACCESS_CONTROL, String.class);

            responseContext.getHeaders().add("Access-Control-Allow-Origin", accessControl);
            responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
            responseContext.getHeaders().add("Access-Control-Allow-Headers", accessControl);
            responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            responseContext.getHeaders().add("Access-Control-Expose-Headers", accessControl);

            if (requestContext.getMethod().equals("OPTIONS"))
            {
                responseContext.setStatus(Response.Status.OK.getStatusCode());
            }
        }
        catch (InfraestruturaException e)
        {
            throw new IOException(e);
        }

    }

}
