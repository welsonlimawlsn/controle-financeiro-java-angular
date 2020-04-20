package br.com.controlefinanceiro.token.servico;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import java.security.Key;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.controlefinanceiro.exception.Erro;
import br.com.controlefinanceiro.exception.InfraestruturaException;
import br.com.controlefinanceiro.exception.NegocioException;
import br.com.controlefinanceiro.parametro.constante.Parametros;
import br.com.controlefinanceiro.parametro.servico.ParametroService;
import br.com.controlefinanceiro.token.constante.TokenConstantes;
import br.com.controlefinanceiro.token.dto.TokenDTO;
import br.com.controlefinanceiro.usuario.dao.UsuarioDAO;
import br.com.controlefinanceiro.usuario.entidade.Usuario;

public class TokenService
{
    @Inject
    private ParametroService parametroService;

    @Inject
    private HttpServletRequest httpServletRequest;

    @Inject
    private UsuarioDAO usuarioDAO;

    public Usuario getUsuario() throws InfraestruturaException, NegocioException
    {
        String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null && !authorization.isEmpty() && authorization.startsWith(TokenConstantes.BEARER))
        {
            try
            {
                Claims body = Jwts.parser()
                        .setSigningKey(getChave())
                        .parseClaimsJws(authorization.replace(TokenConstantes.BEARER, ""))
                        .getBody();

                String ipToken = body.get(TokenConstantes.CLIENT_IP, String.class);

                if (!ipToken.equals(httpServletRequest.getRemoteAddr()))
                {
                    throw new NegocioException(Erro.TOKEN_INVALIDO);
                }

                long idUsuario = Long.parseLong(body.getSubject());

                return usuarioDAO.buscaPorId(idUsuario).orElseThrow(() -> new InfraestruturaException(Erro.USUARIO_NAO_ENCONTRADO));
            }
            catch (ExpiredJwtException e)
            {
                throw new NegocioException(Erro.TOKEN_EXPIRADO, e);
            }
        }
        return null;
    }

    public TokenDTO geraToken(Usuario usuario) throws InfraestruturaException
    {
        Long tempoParaExpirar = parametroService.getParametro(Parametros.EXPIRACAO_TOKEN_JWT, Long.class);
        ZonedDateTime tempoExpiracao = ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(tempoParaExpirar);
        String token = geraToken(usuario, tempoExpiracao);
        return TokenDTO.builder()
                .autorizacao(token)
                .expiracao(tempoExpiracao)
                .build();
    }

    private String geraToken(Usuario usuario, ZonedDateTime tempoExpiracao) throws InfraestruturaException
    {
        return TokenConstantes.BEARER + Jwts.builder()
                .setClaims(getExtraClaims())
                .setSubject(usuario.getId().toString())
                .signWith(getChave())
                .setExpiration(Date.from(tempoExpiracao.toInstant()))
                .compact();
    }

    private Map<String, Object> getExtraClaims()
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TokenConstantes.CLIENT_IP, httpServletRequest.getRemoteAddr());
        return claims;
    }

    private Key getChave() throws InfraestruturaException
    {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(parametroService.getParametro(Parametros.CHAVE_TOKEN_JWT, String.class)));
    }
}
