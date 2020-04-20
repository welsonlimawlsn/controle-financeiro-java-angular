package br.com.controlefinanceiro.usuario.dao;

import javax.persistence.TypedQuery;
import java.util.Optional;

import br.com.controlefinanceiro.generico.dao.DAOImpl;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

public class UsuarioDAOImpl extends DAOImpl<Usuario, Long> implements UsuarioDAO
{
    @Override
    public boolean existeEmail(String email)
    {
        TypedQuery<Long> query = getEntityManager().createNamedQuery("quantidadeRegistrosPorEmail", Long.class);
        query.setParameter("email", email);
        return getSingleResult(query).orElse(0L) > 0;
    }

    @Override
    public Optional<Usuario> buscaPorEmail(String email)
    {
        TypedQuery<Usuario> query = getEntityManager().createNamedQuery("buscaPorEmail", Usuario.class);
        query.setParameter("email", email);
        return getSingleResult(query);
    }

}
