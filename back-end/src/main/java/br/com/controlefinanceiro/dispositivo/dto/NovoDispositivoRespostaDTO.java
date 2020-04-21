package br.com.controlefinanceiro.dispositivo.dto;

import lombok.Getter;
import lombok.Setter;

import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;

@Getter
@Setter
public class NovoDispositivoRespostaDTO extends RespostaDTO
{
    private String idDispositivo;
}
