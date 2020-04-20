package br.com.controlefinanceiro.configuracao;

import org.apache.log4j.Logger;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.parametro.constante.Parametros;
import br.com.controlefinanceiro.parametro.entidade.Parametro;
import br.com.controlefinanceiro.parametro.servico.ParametroService;
import br.com.controlefinanceiro.seguranca.servicos.CriptografiaSimetricaService;

@WebListener()
public class InitializerListener implements ServletContextListener
{

    public static final String ALGORITHM = "HmacSHA256";

    public static final String DESCRIPTION_KEY_TOKEN = "Chave de criptografia token";

    private static final String PERSISTENCE_UNIT_NAME = "default";

    private static final Logger LOGGER = Logger.getLogger(InitializerListener.class);

    @Inject
    private ParametroService parametroService;

    @Inject
    private CriptografiaSimetricaService criptografiaSimetricaService;

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        configuraVelocity();
        configuraChaveAssinaturaToken();
        criptografiaSimetricaService.criaChaves();
    }

    private void configuraChaveAssinaturaToken()
    {
        try
        {
            parametroService.getParametro(Parametros.CHAVE_TOKEN_JWT, String.class);
        }
        catch (InfraestruturaException e)
        {
            if (e.getErro() == Erro.PARAMETRO_NAO_ENCONTRADO)
            {
                LOGGER.warn("Chave para a assinatura do token JWT não foi encontrada. Será criada uma automaticamente!");
                generateKey();
            }
            else
            {
                throw new RuntimeException(e);
            }
        }
    }

    private void configuraVelocity()
    {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        Velocity.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        Velocity.init();
    }

    public void generateKey()
    {
        try
        {
            LOGGER.info("Gerando a chave de assinatura de token JWT");
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);

            String secretKey = Base64.getEncoder().encodeToString(generator.generateKey().getEncoded());

            Parametro parametro = new Parametro();
            parametro.setId(Parametros.CHAVE_TOKEN_JWT);
            parametro.setDescricao(DESCRIPTION_KEY_TOKEN);
            parametro.setValor(secretKey);

            EntityManager entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(parametro);
            entityManager.getTransaction().commit();

            LOGGER.info("Chave de assinatura de token JWT foi gerada com sucesso!");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(new InfraestruturaException(Erro.ERRO_INTERNO, e));
        }
    }
}
