package br.com.controlefinanceiro.criptografia.servico;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class CriptografiaService
{
    public boolean validaSenha(String senha, String hashSenha)
    {
        return BCrypt.checkpw(senha, hashSenha);
    }

    public String hashSenha(String senha)
    {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }
}
