package br.com.controlefinanceiro.requisicao.entidade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.controlefinanceiro.funcionalidade.entidade.Funcionalidade;
import br.com.controlefinanceiro.generico.entidade.EntidadePersistente;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

@Getter
@Setter
@Entity
@Table(name = "RQC")
public class Requisicao implements EntidadePersistente
{
    @Id
    @GeneratedValue(generator = "SEQRQC")
    @SequenceGenerator(name = "SEQRQC", allocationSize = 1, sequenceName = "SEQRQC")
    @Column(name = "RQCID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "RQCFCNID")
    private Funcionalidade funcionalidade;

    @ManyToOne
    @JoinColumn(name = "RQCUSRID")
    private Usuario usuario;

    @Column(name = "RQCCRP", columnDefinition = "TEXT")
    private String corpo;

    @Column(name = "RQCCCD")
    private Boolean concluida;

    @Column(name = "RQCIPO")
    private String ipOrigem;

    public void conclui()
    {
        concluida = true;
    }
}
