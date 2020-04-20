package br.com.controlefinanceiro.usuario.dto.novo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NovoUsuarioProtegidoRequisicaoDTO extends AbstractNovoUsuarioRequisicaoDTO<NovoUsuarioProtegidoRespostaDTO>
{
    private Integer idGrupo;
}
