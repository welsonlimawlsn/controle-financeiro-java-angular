package br.com.controlefinanceiro.email.processador;

import java.util.List;

import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;

public interface ProcessadorEmail
{
    void processaEmail(List<String> to, List<String> cc, String subject, String template, RespostaDTO respostaDTO) throws InfraestruturaException;

    void processaEmail(RespostaDTO resposta) throws InfraestruturaException;
}
