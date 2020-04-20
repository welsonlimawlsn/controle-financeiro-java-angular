package br.com.controlefinanceiro.codigoconfirmacao.entidade;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import br.com.controlefinanceiro.requisicao.entidade.Requisicao;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CodigoSegurancaId implements Serializable
{
    private String codigo;

    private Requisicao requisicao;
}
