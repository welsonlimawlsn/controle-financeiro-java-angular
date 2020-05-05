package br.com.controlefinanceiro.conta.dto.remocao;

import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.PathParam;
import java.util.UUID;

import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;

@Getter
@Setter
public class RemoveContaUsuarioRequisicaoDTO extends RequisicaoDTO<RemoveContaUsuarioRespostaDTO>
{
    @PathParam("codigoConta")
    private UUID codigoConta;
}
