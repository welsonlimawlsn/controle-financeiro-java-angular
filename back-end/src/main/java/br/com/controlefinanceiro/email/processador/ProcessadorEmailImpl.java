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
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import br.com.controlefinanceiro.email.anotacao.Cc;
import br.com.controlefinanceiro.email.anotacao.EnviaEmail;
import br.com.controlefinanceiro.email.anotacao.Subject;
import br.com.controlefinanceiro.email.anotacao.To;
import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.requisicao.dto.RespostaDTO;

@Stateless
public class ProcessadorEmailImpl implements ProcessadorEmail
{
    private static final Logger LOGGER = Logger.getLogger(ProcessadorEmail.class);

    @Resource(name = "java:/EmailFintips")
    private Session session;

    @Asynchronous
    @Override
    public void processaEmail(List<String> to, List<String> cc, String subject, String template, RespostaDTO respostaDTO) throws InfraestruturaException
    {
        try
        {
            valida(to, subject, template);

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setRecipients(Message.RecipientType.TO, getInternetAddresses(to));
            if (cc != null && !cc.isEmpty())
            {
                mimeMessage.setRecipients(Message.RecipientType.CC, getInternetAddresses(cc));
            }

            mimeMessage.setSubject(subject);
            mimeMessage.setText(processaTemplate(template, respostaDTO), StandardCharsets.UTF_8.toString(), "html");

            Transport.send(mimeMessage);
        }
        catch (Exception e)
        {
            LOGGER.error("Erro ao enviar e-mail.", e);
            throw new InfraestruturaException(Erro.ERRO_INTERNO, e);
        }
    }

    @Override
    @Asynchronous
    public void processaEmail(RespostaDTO resposta) throws InfraestruturaException
    {
        EnviaEmail annotation = resposta.getClass().getAnnotation(EnviaEmail.class);

        processaEmail(getEmailList(resposta, To.class), getEmailList(resposta, Cc.class), getSubject(annotation, resposta), annotation.vm(), resposta);
    }

    private List<String> getEmailList(RespostaDTO resposta, Class<? extends Annotation> annotationClass) throws InfraestruturaException
    {
        Object to = getAtributoAnotacao(resposta, annotationClass);
        if (to != null)
        {
            if (to instanceof String)
            {
                return Collections.singletonList((String) to);
            }
            else if (to instanceof List)
            {
                return (List<String>) to;
            }
            else
            {
                throw new InfraestruturaException(Erro.ERRO_INTERNO);
            }
        }
        return null;
    }

    private String getSubject(EnviaEmail annotation, RespostaDTO resposta) throws InfraestruturaException
    {
        String subject = (String) getAtributoAnotacao(resposta, Subject.class);
        return subject == null || subject.isEmpty() ? annotation.subject() : subject;
    }

    private Object getAtributoAnotacao(RespostaDTO resposta, Class<? extends Annotation> annotationClass) throws InfraestruturaException
    {
        try
        {
            Class<?> classe = resposta.getClass();
            while (classe != Object.class)
            {
                Field[] fields = classe.getDeclaredFields();
                for (Field field : fields)
                {
                    if (field.isAnnotationPresent(annotationClass))
                    {
                        return classe.getMethod(getNameMethodGet(field)).invoke(resposta);
                    }
                }
                classe = classe.getSuperclass();
            }
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
        {
            throw new InfraestruturaException(Erro.ERRO_INTERNO, e);
        }
        return null;
    }

    private String getNameMethodGet(Field field)
    {
        return "get" + field.getName().substring(0, 1).toUpperCase().concat(field.getName().substring(1));
    }

    private void valida(List<String> to, String subject, String template)
    {
        if (to == null || to.isEmpty())
        {
            throw new IllegalArgumentException("Precisa de ao menos um destinatario para enviar um email");
        }
        if (subject == null || subject.isEmpty())
        {
            throw new IllegalArgumentException("Precisa um assundo para enviar um email");
        }
        if (template == null || template.isEmpty())
        {
            throw new IllegalArgumentException("Informe o template");
        }
    }

    private String processaTemplate(String vm, RespostaDTO resposta) throws InfraestruturaException
    {
        try
        {
            VelocityContext context = new VelocityContext();

            context.put("resposta", resposta);

            Template template = Velocity.getTemplate(vm);

            StringWriter sw = new StringWriter();

            template.merge(context, sw);

            return sw.toString();
        }
        catch (ResourceNotFoundException | ParseErrorException | MethodInvocationException e)
        {
            throw new InfraestruturaException(Erro.ERRO_INTERNO, e);
        }
    }

    private InternetAddress[] getInternetAddresses(List<String> emails)
    {
        return emails.stream().map(this::criaInternetAddress).toArray(InternetAddress[]::new);
    }

    private InternetAddress criaInternetAddress(String email)
    {
        InternetAddress internetAddress = new InternetAddress();
        internetAddress.setAddress(email);
        return internetAddress;
    }
}
