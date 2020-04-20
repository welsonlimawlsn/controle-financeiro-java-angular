package br.com.controlefinanceiro.parametro.servico;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;

import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.parametro.dao.ParametroDAO;
import br.com.controlefinanceiro.parametro.entidade.Parametro;

public class ParametroService
{
    @Inject
    private ParametroDAO parametroDAO;

    public <T> T getParametro(Integer id, Class<T> type) throws InfraestruturaException
    {
        try
        {
            Parametro parametro = parametroDAO.buscaPorId(id)
                    .orElseThrow(() -> new InfraestruturaException(Erro.PARAMETRO_NAO_ENCONTRADO, id.toString()));
            return type.getConstructor(String.class).newInstance(parametro.getValor());
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            throw new InfraestruturaException(Erro.ERRO_INTERNO, e);
        }
    }
}
