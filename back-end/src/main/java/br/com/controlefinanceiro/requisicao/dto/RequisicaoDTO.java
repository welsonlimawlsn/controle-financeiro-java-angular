package br.com.controlefinanceiro.requisicao.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.QueryParam;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import br.com.controlefinanceiro.dispositivo.entidade.Dispositivo;
import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.funcionalidade.entidade.Funcionalidade;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

@Getter
@Setter
public abstract class RequisicaoDTO<T extends RespostaDTO>
{
    @QueryParam("codigoSeguranca")
    private String codigoSeguranca;

    private String email;

    @JsonIgnore
    private String mac;

    @JsonIgnore
    private Usuario usuario;

    @JsonIgnore
    private Funcionalidade funcionalidade;

    @JsonIgnore
    private Dispositivo dispositivo;

    @JsonIgnore
    private String ipOrigem;

    @JsonIgnore
    private T resposta;

    @JsonIgnore
    private boolean obrigadoCodigoSeguranca;

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
