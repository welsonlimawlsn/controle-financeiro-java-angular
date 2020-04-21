package br.com.controlefinanceiro.usuario.dto.recuperacaosenha;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import br.com.controlefinanceiro.email.anotacao.EnviaEmail;
import br.com.controlefinanceiro.email.anotacao.To;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;

@Getter
@Setter
@EnviaEmail(subject = "Senha alterada", vm = "recuperacao_senha.vm")
public class RecuperacaoSenhaRespostaDTO extends RespostaDTO
{
    @To
    @JsonIgnore
    private String email;

    @JsonIgnore
    private String nome;
}
