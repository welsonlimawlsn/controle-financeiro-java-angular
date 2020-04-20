package br.com.controlefinanceiro.conta.dao;

import java.util.List;

import br.com.controlefinanceiro.conta.entidade.Conta;
import br.com.controlefinanceiro.generico.dao.DAO;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

public interface ContaDAO extends DAO<Conta, Long>
{
    List<Conta> buscaPorUsuario(Usuario usuario);
}
