package br.com.controlefinanceiro.usuario.processador.recuperacaosenha;

import javax.inject.Inject;

import br.com.controlefinanceiro.criptografia.servico.CriptografiaService;
import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.funcionalidade.constante.Funcionalidades;
import br.com.controlefinanceiro.requisicao.anotacao.Processador;
import br.com.controlefinanceiro.requisicao.processador.AbstractProcessadorRequisicao;
import br.com.controlefinanceiro.usuario.dao.UsuarioDAO;
import br.com.controlefinanceiro.usuario.dto.recuperacaosenha.RecuperacaoSenhaRequisicaoDTO;
import br.com.controlefinanceiro.usuario.dto.recuperacaosenha.RecuperacaoSenhaRespostaDTO;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

@Processador(Funcionalidades.RECUPERACAO_SENHA)
public class RecuperacaoSenhaProcessador extends AbstractProcessadorRequisicao<RecuperacaoSenhaRequisicaoDTO, RecuperacaoSenhaRespostaDTO>
{
    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private CriptografiaService criptografiaService;

    @Override
    protected void processaRequisicao(RecuperacaoSenhaRequisicaoDTO requisicao, RecuperacaoSenhaRespostaDTO resposta)
            throws NegocioException, InfraestruturaException
    {
        Usuario usuario = usuarioDAO.buscaPorEmail(requisicao.getEmail())
                .orElseThrow(() -> new InfraestruturaException(Erro.ERRO_INTERNO));
        usuario.setSenha(criptografiaService.hashSenha(requisicao.getNovaSenha()));

        resposta.setEmail(usuario.getEmail());
        resposta.setNome(usuario.getNome());

    }

    @Override
    public void realizaPreValidacao(RecuperacaoSenhaRequisicaoDTO recuperacaoSenhaRequisicaoDTO) throws NegocioException
    {

    }
}
