package br.com.controlefinanceiro.dispositivo.processador.novodispositivo;

import javax.inject.Inject;

import br.com.controlefinanceiro.dispositivo.dao.DispositivoDAO;
import br.com.controlefinanceiro.dispositivo.dto.NovoDispositivoRequisicaoDTO;
import br.com.controlefinanceiro.dispositivo.dto.NovoDispositivoRespostaDTO;
import br.com.controlefinanceiro.dispositivo.entidade.Dispositivo;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.funcionalidade.constante.Funcionalidades;
import br.com.controlefinanceiro.requisicao.anotacao.Processador;
import br.com.controlefinanceiro.requisicao.processador.AbstractProcessadorRequisicao;

@Processador(Funcionalidades.NOVO_DISPOSITIVO)
public class NovoDispositivoProcessador extends AbstractProcessadorRequisicao<NovoDispositivoRequisicaoDTO, NovoDispositivoRespostaDTO>
{
    @Inject
    private DispositivoDAO dispositivoDAO;

    @Override
    protected void processaRequisicao(NovoDispositivoRequisicaoDTO requisicao, NovoDispositivoRespostaDTO resposta) throws NegocioException, InfraestruturaException
    {
        Dispositivo dispositivo = new Dispositivo();

        dispositivo.setIp(requisicao.getIpOrigem());
        dispositivo.setMac(requisicao.getMac());
        dispositivo.setSistemaOperacional(requisicao.getSistemaOperacional());

        dispositivoDAO.salva(dispositivo);

        resposta.setIdDispositivo(dispositivo.getId().toString());
    }

    @Override
    public void realizaPreValidacao(NovoDispositivoRequisicaoDTO requisicao) throws NegocioException
    {

    }
}
