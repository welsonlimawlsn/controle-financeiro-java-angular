package br.com.controlefinanceiro.codigoconfirmacao.regra;

import javax.inject.Inject;
import java.util.Optional;

import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;
import br.com.controlefinanceiro.requisicao.interceptor.RegraCodigoSeguranca;
import br.com.controlefinanceiro.usuario.dao.UsuarioDAO;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

public class CasoSejaUmNovoDispositivoRegra implements RegraCodigoSeguranca
{
    @Inject
    private UsuarioDAO usuarioDAO;

    @Override
    public boolean verificaRegra(RequisicaoDTO<?> requisicao) throws NegocioException
    {
        if (requisicao.getUsuario() != null)
        {
            if (requisicao.getDispositivo() == null)
            {
                throw new NegocioException(Erro.SEM_PERMISSAO);
            }
            return !requisicao.getDispositivo().getUsuarios().contains(requisicao.getUsuario());
        }
        else
        {
            if (requisicao.getEmail() != null && !requisicao.getEmail().isEmpty())
            {
                Optional<Usuario> usuario = usuarioDAO.buscaPorEmail(requisicao.getEmail());
                if (usuario.isPresent())
                {
                    return !usuario.get().temEsseDispositivo(requisicao.getDispositivo());
                }
            }
        }
        return false;
    }
}
