package br.com.controlefinanceiro.requisicao.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RespostaDTO
{
    /**
     * Código para ser usado para envio de e-mail, não deve ir na resposta final para o cliente.
     */
    @JsonIgnore
    private String codigoSeguranca;
}
