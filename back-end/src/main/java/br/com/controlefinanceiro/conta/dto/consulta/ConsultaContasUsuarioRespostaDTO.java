package br.com.controlefinanceiro.conta.dto.consulta;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import br.com.controlefinanceiro.conta.dto.ContaDTO;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;

@Getter
@Setter
public class ConsultaContasUsuarioRespostaDTO extends RespostaDTO
{
    private List<ContaDTO> contas;
}
