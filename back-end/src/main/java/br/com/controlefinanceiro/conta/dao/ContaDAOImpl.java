package br.com.controlefinanceiro.conta.dao;

import java.util.List;

import br.com.controlefinanceiro.conta.entidade.Conta;
import br.com.controlefinanceiro.generico.dao.DAOImpl;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

public class ContaDAOImpl extends DAOImpl<Conta, Long> implements ContaDAO
{
    @Override
    public List<Conta> buscaPorUsuario(Usuario usuario)
    {
        return null;
    }
}
