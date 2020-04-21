package br.com.controlefinanceiro.codigoconfirmacao.dao;

import javax.persistence.TypedQuery;
import java.time.ZonedDateTime;
import java.util.Optional;

import br.com.controlefinanceiro.codigoconfirmacao.entidade.CodigoSeguranca;
import br.com.controlefinanceiro.codigoconfirmacao.entidade.CodigoSegurancaId;
import br.com.controlefinanceiro.dispositivo.entidade.Dispositivo;
import br.com.controlefinanceiro.funcionalidade.entidade.Funcionalidade;
import br.com.controlefinanceiro.generico.dao.DAOImpl;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

public class CodigoSegurancaDAOImpl extends DAOImpl<CodigoSeguranca, CodigoSegurancaId> implements CodigoSegurancaDAO
{
    @Override
    public Optional<CodigoSeguranca> buscaCodigoSegurancaValido(String codigoSeguranca, Funcionalidade funcionalidade, Dispositivo dispositivo, Usuario usuario)
    {
        TypedQuery<CodigoSeguranca> query = getEntityManager().createNamedQuery("buscaCodigoSegurancaValido", CodigoSeguranca.class);
        query.setParameter("funcionalidade", funcionalidade);
        query.setParameter("codigo", codigoSeguranca);
        query.setParameter("dispositivo", dispositivo);
        query.setParameter("dataAtual", ZonedDateTime.now());
        return getSingleResult(query);
    }
}
