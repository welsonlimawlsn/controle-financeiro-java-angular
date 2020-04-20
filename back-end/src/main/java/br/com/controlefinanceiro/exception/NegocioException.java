package br.com.controlefinanceiro.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class NegocioException extends AbstractException
{
    public NegocioException(List<String> errorCodes, Erro erro)
    {
        super(errorCodes, erro);
    }

    public NegocioException(Erro erro, String... params)
    {
        super(erro, params);
    }

    public NegocioException(Erro erro, Throwable cause, String... params)
    {
        super(erro, cause, params);
    }
}
