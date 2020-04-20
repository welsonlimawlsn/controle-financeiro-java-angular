package br.com.controlefinanceiro.requisicao.anotacao;

import java.lang.annotation.Annotation;

public class ProcessadorSelector implements Processador
{
    private int value;

    public ProcessadorSelector(int value)
    {
        this.value = value;
    }

    @Override
    public int value()
    {
        return value;
    }

    @Override
    public Class<? extends Annotation> annotationType()
    {
        return Processador.class;
    }
}
