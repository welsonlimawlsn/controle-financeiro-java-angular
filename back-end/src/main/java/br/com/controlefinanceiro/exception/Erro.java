package br.com.controlefinanceiro.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.ws.rs.core.Response;

@Getter
@AllArgsConstructor
public enum Erro
{
    AUTENTICACAO_OBRIGATORIA("001", Response.Status.UNAUTHORIZED),
    SEM_PERMISSAO("002", Response.Status.FORBIDDEN),
    ERRO_INTERNO("003", Response.Status.FORBIDDEN),
    NUMERO_INVALIDO_PARAMENTROS("004", Response.Status.INTERNAL_SERVER_ERROR),
    SEM_ANOTACAO_FUNCIONALIDADE("005", Response.Status.INTERNAL_SERVER_ERROR),
    SEM_HERANCA_REQUISICAO("006", Response.Status.INTERNAL_SERVER_ERROR),
    FUNCIONALIDADE_NULA("007", Response.Status.INTERNAL_SERVER_ERROR),
    FUNCIONALIDADE_NAO_ENCONTRADA("008", Response.Status.INTERNAL_SERVER_ERROR),
    INFORMACOES_INVALIDAS("009", Response.Status.BAD_REQUEST),
    EMAIL_OBRIGATORIO("010", Response.Status.BAD_REQUEST),
    SENHA_OBRIGATORIA("011", Response.Status.BAD_REQUEST),
    EMAIL_INVALIDO("012", Response.Status.BAD_REQUEST),
    CHAVE_NAO_ENCONTRADA("013", Response.Status.INTERNAL_SERVER_ERROR),
    NOME_OBRIGATORIO("014", Response.Status.BAD_REQUEST),
    SOBRENOME_OBRIGATORIO("015", Response.Status.BAD_REQUEST),
    EMAIL_JA_EXISTENTE("016", Response.Status.BAD_REQUEST),
    EMAIL_OU_SENHA_INVALIDOS("017", Response.Status.UNAUTHORIZED),
    PARAMETRO_NAO_ENCONTRADO("018", Response.Status.INTERNAL_SERVER_ERROR),
    TOKEN_INVALIDO("019", Response.Status.UNAUTHORIZED),
    TOKEN_EXPIRADO("020", Response.Status.UNAUTHORIZED),
    USUARIO_NAO_ENCONTRADO("021", Response.Status.NOT_FOUND),
    PROCESSADOR_NAO_ENCONTRADO("022", Response.Status.INTERNAL_SERVER_ERROR),
    CODIGO_SEGURANCA_OBRIGATORIO("023", Response.Status.FORBIDDEN),
    ERRO_AO_CRIAR_NOVO_CODIGO_SEGURANCA("024", Response.Status.INTERNAL_SERVER_ERROR),
    ERRO_PARSE_JSON("025", Response.Status.INTERNAL_SERVER_ERROR),
    CODIGO_SEGURANCA_INVALIDO("026", Response.Status.UNAUTHORIZED),
    TAMANHO_MINIMO_SENHA("027", Response.Status.BAD_REQUEST),
    DISPOSITIVO_OBRIGATORIO("028", Response.Status.FORBIDDEN),
    DISPOSITIVO_INVALIDO("029", Response.Status.FORBIDDEN),
    CONTA_NAO_ENCONTRADA("030", Response.Status.NOT_FOUND);

    private final String codigo;

    private final Response.Status status;

    public interface Constante
    {
        String EMAIL_OBRIGATORIO = "010";

        String SENHA_OBRIGATORIA = "011";

        String EMAIL_INVALIDO = "012";

        String NOME_OBRIGATORIO = "014";

        String SOBRENOME_OBRIGATORIO = "015";

        String TAMANHO_MINIMO_SENHA = "027";
    }
}
