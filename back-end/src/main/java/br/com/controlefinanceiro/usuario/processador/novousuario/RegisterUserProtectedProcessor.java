package br.com.controlefinanceiro.usuario.processador.novousuario;

import br.com.controlefinanceiro.funcionalidade.constante.Funcionalidades;
import br.com.controlefinanceiro.grupo.entidade.Grupo;
import br.com.controlefinanceiro.requisicao.anotacao.Processador;
import br.com.controlefinanceiro.usuario.dto.novo.NovoUsuarioProtegidoRequisicaoDTO;
import br.com.controlefinanceiro.usuario.dto.novo.NovoUsuarioProtegidoRespostaDTO;

@Processador(Funcionalidades.NOVO_USUARIO_PROTEGIDO)
public class RegisterUserProtectedProcessor extends AbstractRegisterUserProcessor<NovoUsuarioProtegidoRequisicaoDTO, NovoUsuarioProtegidoRespostaDTO>
{
    @Override
    protected Grupo getGroup(NovoUsuarioProtegidoRequisicaoDTO request)
    {
        return null;
    }
}
