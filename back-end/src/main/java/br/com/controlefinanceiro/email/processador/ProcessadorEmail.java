package br.com.controlefinanceiro.email.processador;

import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;

public interface ProcessadorEmail
{
    void processaEmail(RespostaDTO resposta) throws InfraestruturaException;
}
