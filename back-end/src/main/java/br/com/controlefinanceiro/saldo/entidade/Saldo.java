package br.com.controlefinanceiro.saldo.entidade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

import br.com.controlefinanceiro.conta.entidade.Conta;
import br.com.controlefinanceiro.generico.entidade.EntidadePersistente;
import br.com.controlefinanceiro.saldo.enums.TipoSaldo;

@Getter
@Setter
@Entity
@Table(name = "SLD")
@IdClass(SaldoId.class)
public class Saldo implements EntidadePersistente
{
    @Id
    @ManyToOne
    @JoinColumn(name = "SLDCTA")
    private Conta conta;

    @Id
    @Column(name = "SLDCTC")
    private Long competencia;

    @Id
    @Column(name = "SLDTSD")
    private TipoSaldo tipoSaldo;

    @Column(name = "SLDVLR")
    private BigDecimal valor;
}
