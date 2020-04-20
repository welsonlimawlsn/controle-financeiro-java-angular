package br.com.controlefinanceiro.requisicao.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public abstract class RespostaDTO
{
    /**
     * Código para ser usado para envio de e-mail, não deve ir na resposta final para o cliente.
     */
    @JsonIgnore
    private String codigoSeguranca;

    /**
     * Nome do template para envio de e-mail caso necessário, não deve ir na resposta final para o cliente
     */
    @JsonIgnore
    private String templateEmail;

    @JsonIgnore
    private RequisicaoDTO<?> requisicao;

    @JsonIgnore
    private Set<String> destinatarios = new HashSet<>();

    @JsonIgnore
    private String assunto;

    @JsonIgnore
    private Set<String> copias = new HashSet<>();

    public void adicionaDestinatario(String email)
    {
        destinatarios.add(email);
    }

    public void adicionaCopia(String email)
    {
        copias.add(email);
    }
}
