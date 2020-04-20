package br.com.controlefinanceiro.relatorio.processador;

import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;

public interface ProcessadorRelatorio
{
    void processaRelatorio(RespostaDTO resposta);
}
