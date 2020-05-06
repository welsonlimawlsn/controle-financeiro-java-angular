package br.com.controlefinanceiro.conta.dto.atualiza;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import br.com.controlefinanceiro.conta.dto.AbstractNovaAtualizaContaUsuarioRequisicaoDTO;

@Getter
@Setter
public class AtualizaContaUsuarioRequisicaoDTO extends AbstractNovaAtualizaContaUsuarioRequisicaoDTO<AtualizaContaUsuarioRespostaDTO>
{
    private UUID id;
}
