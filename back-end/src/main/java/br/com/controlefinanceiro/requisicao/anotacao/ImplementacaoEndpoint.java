package br.com.controlefinanceiro.requisicao.anotacao;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface ImplementacaoEndpoint
{
}
