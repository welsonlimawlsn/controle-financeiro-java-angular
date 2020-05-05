package br.com.controlefinanceiro.conta.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.controlefinanceiro.conta.entidade.Conta;
import br.com.controlefinanceiro.generico.dao.DAO;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

public interface ContaDAO extends DAO<Conta, UUID>
{
    List<Conta> buscaPorUsuario(Usuario usuario);

    Optional<Conta> buscaPorIdEUsuario(UUID codigoConta, Usuario usuario);
}
