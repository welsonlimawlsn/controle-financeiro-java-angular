package br.com.controlefinanceiro.generico.dao;

import java.util.Optional;

import br.com.controlefinanceiro.generico.entidade.EntidadePersistente;

public interface DAO<T extends EntidadePersistente, ID>
{
    void salva(T entity);

    void remove(T entity);

    Optional<T> buscaPorId(ID id);
}
