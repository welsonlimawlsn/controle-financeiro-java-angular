package br.com.controlefinanceiro.grupo.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

import br.com.controlefinanceiro.funcionalidade.entidade.Funcionalidade;
import br.com.controlefinanceiro.generico.entidade.EntidadePersistente;

@Getter
@Setter
@Entity
@Table(name = "GPO")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grupo implements EntidadePersistente
{
    @Id
    @Column(name = "GPOID")
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "GPONME")
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "GPOFCN",
            joinColumns = {@JoinColumn(name = "GPOID")},
            inverseJoinColumns = {@JoinColumn(name = "FCNID")}
    )
    private List<Funcionalidade> funcionalidades;
}
