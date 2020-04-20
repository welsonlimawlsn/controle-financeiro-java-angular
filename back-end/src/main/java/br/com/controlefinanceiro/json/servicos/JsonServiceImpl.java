package br.com.controlefinanceiro.json.servicos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.ext.ContextResolver;
import java.io.IOException;

import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;

public class JsonServiceImpl implements JsonService
{
    @Inject
    private ContextResolver<ObjectMapper> contextResolver;

    @Override
    public String serializa(Object o) throws InfraestruturaException
    {
        try
        {
            return getObjectMapper().writeValueAsString(o);
        }
        catch (JsonProcessingException e)
        {
            throw new InfraestruturaException(Erro.ERRO_PARSE_JSON, e);
        }
    }

    @Override
    public <T> T deserializa(String json, Class<T> tClass) throws InfraestruturaException
    {
        try
        {
            return getObjectMapper().readValue(json, tClass);
        }
        catch (IOException e)
        {
            throw new InfraestruturaException(Erro.ERRO_INTERNO, e);
        }
    }

    private ObjectMapper getObjectMapper()
    {
        return contextResolver.getContext(null);
    }
}
