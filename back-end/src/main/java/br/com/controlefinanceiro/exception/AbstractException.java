package br.com.controlefinanceiro.exception;

import lombok.Getter;
import lombok.Setter;

import javax.ejb.ApplicationException;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Getter
@ApplicationException(rollback = true)
public abstract class AbstractException extends Exception
{
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("errors");

    private final Erro erro;

    private List<String> mensagens;

    public AbstractException(List<String> codigosErros, Erro erro)
    {
        this(erro);
        this.mensagens = codigosErros.stream().map(AbstractException::getMensagemErro).collect(Collectors.toList());
    }

    public AbstractException(Erro erro, String... parametros)
    {
        super(getMensagemErro(erro, parametros));
        this.erro = erro;
    }

    public AbstractException(Erro erro, Throwable cause, String... parametros)
    {
        super(getMensagemErro(erro, parametros), cause);
        this.erro = erro;
    }

    private static String getMensagemErro(Erro erro, String... parametros)
    {
        String mensagem = getMensagemErro(erro.getCodigo());
        for (int i = 0; i < parametros.length; i++)
        {
            mensagem = mensagem.replace("{" + i + "}", parametros[i]);
        }
        return mensagem;
    }

    private static String getMensagemErro(String error)
    {
        String key = "error-" + error;
        try
        {
            return RESOURCE_BUNDLE.getString(key);
        }
        catch (MissingResourceException e)
        {
            throw new RuntimeException(new InfraestruturaException(Erro.CHAVE_NAO_ENCONTRADA, e, key));
        }
    }

    @Getter
    @Setter
    public static class ErrorResponse
    {
        private List<Error> erros;
    }

    @Getter
    @Setter
    public static class Error
    {
        private String mensagem;

        private String codigo;

        public Error(String mensagem, String codigo)
        {
            this.mensagem = mensagem;
            this.codigo = codigo;
        }
    }
}
