package br.com.controlefinanceiro.codigoconfirmacao.entidade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Random;

import br.com.controlefinanceiro.funcionalidade.entidade.Funcionalidade;
import br.com.controlefinanceiro.generico.entidade.EntidadePersistente;
import br.com.controlefinanceiro.requisicao.entidade.Requisicao;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

@Getter
@Setter
@Entity
@Table(name = "CSG")
@NamedQueries({
        @NamedQuery(
                name = "buscaCodigoSegurancaValido",
                query = "SELECT cs FROM CodigoSeguranca cs " +
                        "WHERE cs.codigo = :codigo AND cs.funcionalidade = :funcionalidade " +
                        "AND cs.ipOrigem = :ipOrigem AND cs.validade >= :dataAtual " +
                        "AND cs.usado = false " +
                        "AND cs.requisicao.concluida = false")
})
@IdClass(CodigoSegurancaId.class)
public class CodigoSeguranca implements EntidadePersistente
{
    @Id
    @Column(name = "CSGCDG")
    private String codigo;

    @Id
    @ManyToOne
    @JoinColumn(name = "CSGRQCID")
    private Requisicao requisicao;

    @Column(name = "CSGIPO")
    private String ipOrigem;

    @Column(name = "CSGVLD")
    private ZonedDateTime validade;

    @Column(name = "CSGUSD")
    private Boolean usado;

    @ManyToOne
    @JoinColumn(name = "CSGUSRID")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "CSGFCNID")
    private Funcionalidade funcionalidade;

    public void geraCodigo(int quantidadeNumeros) {
        StringBuilder stringBuilder = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < quantidadeNumeros; i++) {
            stringBuilder.append(random.nextInt(10));
        }

        codigo = stringBuilder.toString();
    }

    public void usa()
    {
        usado = true;
    }
}
