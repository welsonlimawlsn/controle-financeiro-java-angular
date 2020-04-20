package br.com.controlefinanceiro.email.processador;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;

@Stateless
public class ProcessadorEmailImpl implements ProcessadorEmail
{
    private static final Logger LOGGER = Logger.getLogger(ProcessadorEmail.class);

    @Resource(name = "java:/EmailFintips")
    private Session session;

    @Override
    @Asynchronous
    public void processaEmail(RespostaDTO resposta) throws InfraestruturaException
    {
        try
        {
            valida(resposta);

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setRecipients(Message.RecipientType.TO, getInternetAddresses(resposta.getDestinatarios()));
            if (!resposta.getCopias().isEmpty())
            {
                mimeMessage.setRecipients(Message.RecipientType.CC, getInternetAddresses(resposta.getCopias()));
            }

            mimeMessage.setSubject(resposta.getAssunto());
            mimeMessage.setText(processaTemplate(resposta), StandardCharsets.UTF_8.toString(), "html");

            Transport.send(mimeMessage);
        }
        catch (MessagingException e)
        {
            LOGGER.error("Erro ao enviar e-mail.", e);
        }
    }

    private String processaTemplate(RespostaDTO resposta) throws InfraestruturaException
    {
        try
        {
            VelocityContext context = new VelocityContext();

            context.put("resposta", resposta);

            Template template = Velocity.getTemplate(resposta.getTemplateEmail());

            StringWriter sw = new StringWriter();

            template.merge(context, sw);

            return sw.toString();
        }
        catch (ResourceNotFoundException | ParseErrorException | MethodInvocationException e)
        {
            throw new InfraestruturaException(Erro.ERRO_INTERNO, e);
        }
    }

    private InternetAddress[] getInternetAddresses(Set<String> emails)
    {
        return emails.stream().map(this::criaInternetAddress).toArray(InternetAddress[]::new);
    }

    private InternetAddress criaInternetAddress(String email)
    {
        InternetAddress internetAddress = new InternetAddress();
        internetAddress.setAddress(email);
        return internetAddress;
    }

    private void valida(RespostaDTO resposta) throws InfraestruturaException
    {
        if (resposta.getDestinatarios().isEmpty())
        {
            throw new InfraestruturaException(Erro.ERRO_INTERNO);
        }

        if (resposta.getAssunto() == null || resposta.getAssunto().isEmpty())
        {
            throw new InfraestruturaException(Erro.ERRO_INTERNO);
        }
    }
}
