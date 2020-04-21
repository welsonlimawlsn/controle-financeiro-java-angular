package br.com.controlefinanceiro.dispositivo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

import br.com.controlefinanceiro.requisicao.dto.RequisicaoDTO;

@Getter
@Setter
public class NovoDispositivoRequisicaoDTO extends RequisicaoDTO<NovoDispositivoRespostaDTO>
{
    @NotEmpty
    private String sistemaOperacional;
}
