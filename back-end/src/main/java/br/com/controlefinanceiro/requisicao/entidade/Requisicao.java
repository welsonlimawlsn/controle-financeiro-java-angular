package br.com.controlefinanceiro.requisicao.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.UUID;

import br.com.controlefinanceiro.dispositivo.entidade.Dispositivo;
import br.com.controlefinanceiro.funcionalidade.entidade.Funcionalidade;
import br.com.controlefinanceiro.generico.entidade.EntidadePersistente;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

@Getter
@Setter
@Entity
@Table(name = "RQC")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Requisicao implements EntidadePersistente
{
    @Id
    @Column(name = "RQCID")
    @EqualsAndHashCode.Include
    private UUID id;

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

    @ManyToOne
    @JoinColumn(name = "RQCDPVID")
    private Dispositivo dispositivo;

    @PrePersist
    private void prePersist() {
        id = UUID.randomUUID();
    }

    public void conclui()
    {
        concluida = true;
    }
}
