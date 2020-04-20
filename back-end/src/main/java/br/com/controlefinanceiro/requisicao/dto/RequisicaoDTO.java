package br.com.controlefinanceiro.requisicao.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.funcionalidade.entidade.Funcionalidade;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

@Getter
@Setter
public abstract class RequisicaoDTO<T extends RespostaDTO>
{
    private String codigoSeguranca;

    private String email;

    @JsonIgnore
    private Usuario usuario;

    @JsonIgnore
    private Funcionalidade funcionalidade;

    @JsonIgnore
    private T resposta;

    @JsonIgnore
    private String ipOrigem;

    public void validaRequisicao() throws NegocioException, InfraestruturaException
    {
    }

    public void criaResposta() throws InfraestruturaException
    {
        if (resposta == null)
        {
            try
            {
                ParameterizedType aClass = (ParameterizedType) this.getClass().getGenericSuperclass();
                Class<T> respostaClass = (Class<T>) aClass.getActualTypeArguments()[0];
                resposta = respostaClass.getConstructor().newInstance();
            }
            catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
            {
                throw new InfraestruturaException(Erro.ERRO_INTERNO, e);
            }
        }
    }
}
