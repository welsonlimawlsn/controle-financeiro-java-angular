package br.com.controlefinanceiro.usuario.dao;

import java.util.Optional;
import java.util.UUID;

import br.com.controlefinanceiro.generico.dao.DAO;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

public interface UsuarioDAO extends DAO<Usuario, UUID>
{
    boolean existeEmail(String email);

    Optional<Usuario> buscaPorEmail(String email);
}
