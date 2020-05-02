package br.com.controlefinanceiro.configuracao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropriedadesService
{
    private static Properties properties;

    static
    {
        InputStream properties = PropriedadesService.class.getClassLoader().getResourceAsStream("application.properties");

        PropriedadesService.properties = new Properties();
        try
        {
            PropriedadesService.properties.load(properties);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key)
    {
        return properties.getProperty(key);
    }

    public interface Constantes
    {
        String PATH_CHAVE_CRIPTOGRAFIA = "pathChaveCriptografia";
    }
}
