package br.com.controlefinanceiro.dispositivo.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.UUID;

import br.com.controlefinanceiro.generico.entidade.EntidadePersistente;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

@Entity
@Table(name = "DPV")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Dispositivo implements EntidadePersistente
{
    @Id
    @Column(name = "DPVID")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "DPVIP")
    private String ip;

    @Column(name = "DPVSO")
    private String sistemaOperacional;

    @Column(name = "DPVMAC")
    private String mac;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DPVUSR")
    private Usuario usuario;

    @PrePersist
    public void prePersist()
    {
        id = UUID.randomUUID();
    }
}
