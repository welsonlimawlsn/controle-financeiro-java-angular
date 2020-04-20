package br.com.controlefinanceiro.funcionalidade.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.controlefinanceiro.generico.entidade.EntidadePersistente;

@Getter
@Setter
@Entity
@Table(name = "FCN")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Funcionalidade implements EntidadePersistente
{
    @Id
    @Column(name = "FCNID")
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "FCNNME")
    private String nome;

    @Column(name = "FCNDCC")
    private String descricao;

    @Column(name = "FCNATCOGR")
    private Boolean autenticacaoObrigatoria;

    @Column(name = "FCNPTDTDS")
    private Boolean permitidoParaTodos;
}
