package br.com.controlefinanceiro.parametro.entidade;

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
@Table(name = "PMT")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Parametro implements EntidadePersistente
{
    @Id
    @Column(name = "PMTID")
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "PMTDCC")
    private String descricao;

    @Column(name = "PMTVLR")
    private String valor;
}
