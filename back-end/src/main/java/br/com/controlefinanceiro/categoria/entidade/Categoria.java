package br.com.controlefinanceiro.categoria.entidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "CGR")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria
{
    @Id
    @Column(name = "CGRID")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "CGRNME")
    private String nome;

    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID();
    }
}
