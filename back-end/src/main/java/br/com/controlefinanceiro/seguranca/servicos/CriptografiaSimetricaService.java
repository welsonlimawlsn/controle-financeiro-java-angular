package br.com.controlefinanceiro.seguranca.servicos;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Base64;

import br.com.controlefinanceiro.configuracao.PropriedadesService;
import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;

public class CriptografiaSimetricaService
{
    private static final Path LOCAL_IV;

    private static final Path LOCAL_KEY;

    private static byte[] IV;

    private static SecretKey KEY;

    static
    {
        String path = PropriedadesService.getProperty(PropriedadesService.Constantes.PATH_CHAVE_CRIPTOGRAFIA);
        LOCAL_IV = Paths.get(path + File.separator + "iv.key");
        LOCAL_KEY = Paths.get(path + File.separator + "key.key");
        KEY = carregaSecretKey();
        IV = carregaIV();
    }

    private static byte[] carregaIV()
    {
        return carrega(LOCAL_IV, byte[].class);
    }

    private static SecretKey carregaSecretKey()
    {
        return carrega(LOCAL_KEY, SecretKey.class);
    }

    private static <T> T carrega(Path path, Class<T> tClass)
    {
        if (Files.exists(path))
        {
            try
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path));
                T secretKey = (T) objectInputStream.readObject();
                objectInputStream.close();
                return secretKey;
            }
            catch (IOException | ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public String criptografa(String texto) throws InfraestruturaException
    {
        Cipher encripta = null;
        try
        {
            encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            encripta.init(Cipher.ENCRYPT_MODE, KEY, new IvParameterSpec(IV));
            return Base64.getEncoder().encodeToString(encripta.doFinal(texto.getBytes(StandardCharsets.UTF_8)));
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e)
        {
            throw new InfraestruturaException(Erro.ERRO_INTERNO, e);
        }

    }

    public String descriptografa(String textoCriptografa) throws InfraestruturaException
    {
        try
        {
            byte[] decode = Base64.getDecoder().decode(textoCriptografa);
            Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            decripta.init(Cipher.DECRYPT_MODE, KEY, new IvParameterSpec(IV));
            return new String(decripta.doFinal(decode), StandardCharsets.UTF_8);
        }
        catch (NoSuchAlgorithmException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | NoSuchProviderException | IllegalBlockSizeException e)
        {
            throw new InfraestruturaException(Erro.ERRO_INTERNO, e);
        }
    }

    public void criaChaves()
    {
        try
        {
            SecureRandom secureRandom = new SecureRandom();

            byte[] key = new byte[16];
            byte[] iv = new byte[16];

            secureRandom.nextBytes(key);
            secureRandom.nextBytes(iv);

            SecretKey secretKey = new SecretKeySpec(key, "AES");

            salvaKey(iv, LOCAL_IV);
            salvaKey(secretKey, LOCAL_KEY);

            KEY = carregaSecretKey();
            IV = carregaIV();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void salvaKey(Object o, Path local) throws IOException
    {
        if (Files.notExists(local))
        {
            Files.createFile(local);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(local));
            objectOutputStream.writeObject(o);
            objectOutputStream.close();
        }
    }
}
