package br.com.controlefinanceiro.configuracao;

public class PropriedadesService
{

    public static String getProperty(String key)
    {
        return System.getProperties().getProperty(key);
    }

    public interface Constantes
    {
        String PATH_CHAVE_CRIPTOGRAFIA = "br.com.escovandobit.fintips.requisicao.chavecriptografia";
    }
}
