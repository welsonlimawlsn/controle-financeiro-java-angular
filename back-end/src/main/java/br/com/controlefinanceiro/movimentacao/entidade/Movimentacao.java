package br.com.controlefinanceiro.movimentacao.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

import br.com.controlefinanceiro.categoria.entidade.Categoria;
import br.com.controlefinanceiro.conta.entidade.Conta;
import br.com.controlefinanceiro.generico.entidade.EntidadePersistente;
import br.com.controlefinanceiro.movimentacao.enums.TipoMovimentacao;

@Getter
@Setter
@Entity
@Table(name = "MVC")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Movimentacao implements EntidadePersistente
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQMVC")
    @SequenceGenerator(name = "SEQMVC", sequenceName = "SEQMVC", allocationSize = 1)
    @Column(name = "MVCID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MVCCTA")
    private Conta conta;

    @Column(name = "MVCVLR")
    private BigDecimal valor;

    @Column(name = "MVCTMC")
    private TipoMovimentacao tipoMovimentacao;

    @ManyToOne
    @JoinColumn(name = "MVCCGR")
    private Categoria categoria;
}
