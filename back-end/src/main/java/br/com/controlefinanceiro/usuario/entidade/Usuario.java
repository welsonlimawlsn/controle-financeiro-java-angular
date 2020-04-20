package br.com.controlefinanceiro.usuario.entidade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.List;

import br.com.controlefinanceiro.conta.entidade.Conta;
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
public class Usuario implements EntidadePersistente
{
    @Id
    @GeneratedValue(generator = "SEQUSR", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQUSR", sequenceName = "SEQUSR", allocationSize = 1)
    @Column(name = "USRID")
    private Long id;

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
}
