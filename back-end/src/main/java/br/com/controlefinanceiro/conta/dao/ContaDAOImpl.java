package br.com.controlefinanceiro.conta.dao;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.controlefinanceiro.conta.entidade.Conta;
import br.com.controlefinanceiro.generico.dao.DAOImpl;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

public class ContaDAOImpl extends DAOImpl<Conta, UUID> implements ContaDAO
{
    @Override
    public List<Conta> buscaPorUsuario(Usuario usuario)
    {
        return null;
    }

    @Override
    public Optional<Conta> buscaPorIdEUsuario(UUID codigoConta, Usuario usuario)
    {
        TypedQuery<Conta> query = getEntityManager().createNamedQuery("consultaPorIdEUsuario", Conta.class);
        query.setParameter("id", codigoConta);
        query.setParameter("usuario", usuario);
        return getSingleResult(query);
    }
}
