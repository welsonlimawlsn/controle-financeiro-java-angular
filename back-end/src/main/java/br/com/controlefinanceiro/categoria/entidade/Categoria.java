package br.com.controlefinanceiro.categoria.entidade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "CGR")
public class Categoria
{
    @Id
    @GeneratedValue(generator = "SEQCGR")
    @SequenceGenerator(name = "SEQCGR", sequenceName = "SEQCGR", allocationSize = 1)
    @Column(name = "CGRID")
    private Integer id;

    @Column(name = "CGRNME")
    private String nome;
}
