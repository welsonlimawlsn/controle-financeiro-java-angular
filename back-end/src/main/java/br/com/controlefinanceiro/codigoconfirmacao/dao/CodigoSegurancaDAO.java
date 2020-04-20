package br.com.controlefinanceiro.codigoconfirmacao.dao;

import java.util.Optional;

import br.com.controlefinanceiro.codigoconfirmacao.entidade.CodigoSeguranca;
import br.com.controlefinanceiro.codigoconfirmacao.entidade.CodigoSegurancaId;
import br.com.controlefinanceiro.funcionalidade.entidade.Funcionalidade;
import br.com.controlefinanceiro.generico.dao.DAO;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

public interface CodigoSegurancaDAO extends DAO<CodigoSeguranca, CodigoSegurancaId>
{
    Optional<CodigoSeguranca> buscaCodigoSegurancaValido(String codigoSeguranca, Funcionalidade funcionalidade, String ipOrigem, Usuario usuario);
}
