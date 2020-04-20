package br.com.controlefinanceiro.generico.dao;

import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

import br.com.controlefinanceiro.generico.entidade.EntidadePersistente;

@Getter
public class DAOImpl<T extends EntidadePersistente, ID> implements DAO<T, ID>
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void salva(T entity)
    {
        entityManager.persist(entity);
    }

    @Override
    public Optional<T> buscaPorId(ID id)
    {
        return Optional.ofNullable(entityManager.find(getTypeEntity(), id));
    }

    private Class<T> getTypeEntity()
    {
        ParameterizedType aClass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<T> entityClass = (Class<T>) aClass.getActualTypeArguments()[0];
        return entityClass;
    }

    protected <R> Optional<R> getSingleResult(TypedQuery<R> query)
    {
        try
        {
            return Optional.ofNullable(query.getSingleResult());
        }
        catch (NoResultException e)
        {
            return Optional.empty();
        }
    }
}
