package br.com.controlefinanceiro.usuario.dto.novo;

import lombok.Getter;
import lombok.Setter;

import br.com.controlefinanceiro.codigoconfirmacao.anotacao.RequerCodigoSeguranca;

@Getter
@Setter
@RequerCodigoSeguranca
public class NovoUsuarioDesprotegidoRequisicaoDTO extends AbstractNovoUsuarioRequisicaoDTO<NovoUsuarioDesprotegidoRespostaDTO>
{
    @Override
    public String getEmailEnvioCodigoSeguranca()
    {
        return getEmail();
    }
}