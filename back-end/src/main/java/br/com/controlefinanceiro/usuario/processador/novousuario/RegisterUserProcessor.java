package br.com.controlefinanceiro.usuario.processador.novousuario;

import br.com.controlefinanceiro.funcionalidade.constante.Funcionalidades;
import br.com.controlefinanceiro.grupo.entidade.Grupo;
import br.com.controlefinanceiro.requisicao.anotacao.Processador;
import br.com.controlefinanceiro.usuario.dto.novo.NovoUsuarioDesprotegidoRequisicaoDTO;
import br.com.controlefinanceiro.usuario.dto.novo.NovoUsuarioDesprotegidoRespostaDTO;

@Processador(Funcionalidades.NOVO_USUARIO)
public class RegisterUserProcessor extends AbstractRegisterUserProcessor<NovoUsuarioDesprotegidoRequisicaoDTO, NovoUsuarioDesprotegidoRespostaDTO>
{
    private static final int ID_DEFAULT_USER = 2;

    @Override
    protected Grupo getGroup(NovoUsuarioDesprotegidoRequisicaoDTO request)
    {
        Grupo grupo = new Grupo();

        grupo.setId(ID_DEFAULT_USER);

        return grupo;
    }
}
