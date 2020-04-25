package br.com.controlefinanceiro.conta.entidade;

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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import br.com.controlefinanceiro.generico.entidade.EntidadePersistente;
import br.com.controlefinanceiro.movimentacao.entidade.Movimentacao;
import br.com.controlefinanceiro.saldo.entidade.Saldo;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

@Getter
@Setter
@Entity
@Table(name = "CTA")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Conta implements EntidadePersistente
{
    @Id
    @Column(name = "CTAID")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "CTANME")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "CTAUSR")
    private Usuario usuario;

    @Column(name = "CTAVLRINC")
    private BigDecimal valorInicial;

    @OneToMany(mappedBy = "conta")
    private List<Movimentacao> movimentacoes;

    @OneToMany(mappedBy = "conta")
    private List<Saldo> saldos;

    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID();
    }
}
