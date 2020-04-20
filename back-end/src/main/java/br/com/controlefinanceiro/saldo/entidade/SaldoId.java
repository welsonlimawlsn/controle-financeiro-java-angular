package br.com.controlefinanceiro.saldo.entidade;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

import br.com.controlefinanceiro.conta.entidade.Conta;
import br.com.controlefinanceiro.saldo.enums.TipoSaldo;

@Getter
@Setter
public class SaldoId implements Serializable
{
    private Conta conta;

    private Long competencia;

    private TipoSaldo tipoSaldo;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaldoId saldoId = (SaldoId) o;
        return conta.equals(saldoId.conta) &&
                competencia.equals(saldoId.competencia) &&
                tipoSaldo == saldoId.tipoSaldo;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(conta, competencia, tipoSaldo);
    }
}
