package br.com.controlefinanceiro.json.servicos;

import br.com.controlefinanceiro.exception.InfraestruturaException;

public interface JsonService
{
    String serializa(Object o) throws InfraestruturaException;

    <T> T deserializa(String json, Class<T> tClass) throws InfraestruturaException;
}
