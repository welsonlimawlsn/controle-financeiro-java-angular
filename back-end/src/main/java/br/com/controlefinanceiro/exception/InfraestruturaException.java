package br.com.controlefinanceiro.exception;

import lombok.Getter;

@Getter
public class InfraestruturaException extends AbstractException
{

    public InfraestruturaException(Erro erro, String... params)
    {
        super(erro, params);
    }

    public InfraestruturaException(Erro erro, Throwable cause, String... params)
    {
        super(erro, cause, params);
    }
}
