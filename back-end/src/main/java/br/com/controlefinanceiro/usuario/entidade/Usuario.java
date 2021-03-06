package br.com.controlefinanceiro.usuario.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import br.com.controlefinanceiro.conta.entidade.Conta;
import br.com.controlefinanceiro.dispositivo.entidade.Dispositivo;
import br.com.controlefinanceiro.generico.entidade.EntidadePersistente;
import br.com.controlefinanceiro.grupo.entidade.Grupo;

@Getter
@Setter
@Entity
@Table(name = "USR")
@NamedQueries({
        @NamedQuery(name = "quantidadeRegistrosPorEmail", query = "SELECT COUNT (u.email) FROM Usuario u WHERE u.email = :email"),
        @NamedQuery(name = "buscaPorEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email")
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario implements EntidadePersistente
{
    @Id
    @Column(name = "USRID")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "USRNME")
    private String nome;

    @Column(name = "USRSBN")
    private String sobrenome;

    @Column(name = "USREML", unique = true)
    private String email;

    @Column(name = "USRSNH")
    private String senha;

    @ManyToOne
    @JoinColumn(name = "USRGRPID")
    private Grupo grupo;

    @OneToMany(mappedBy = "usuario")
    private List<Conta> contas;

    @ManyToMany
    @JoinTable(
            name = "USRDPV",
            joinColumns = {
                    @JoinColumn(name = "USRID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "DPVID")
            }
    )
    private Set<Dispositivo> dispositivos;

    @PrePersist
    public void prePersist()
    {
        id = UUID.randomUUID();
    }

    public void addDispositivo(Dispositivo dispositivo)
    {
        if (dispositivos == null)
        {
            dispositivos = new HashSet<>();
        }
        dispositivos.add(dispositivo);
    }

    public boolean temEsseDispositivo(Dispositivo dispositivo)
    {
        return dispositivos.contains(dispositivo);
    }
}
