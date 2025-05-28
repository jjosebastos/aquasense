package br.com.fiap.aquasense.service;

import br.com.fiap.aquasense.model.auth.Token;
import br.com.fiap.aquasense.model.auth.UserRole;
import br.com.fiap.aquasense.model.auth.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TokenService {
    private Instant expiresAt = LocalDateTime.now().plusMinutes(120).atZone(ZoneId.systemDefault()).toInstant();
    private Algorithm algorithm = Algorithm.HMAC256("secret");

    public Token createToken(Usuario usuario) {
        var jwt = JWT.create()
                .withSubject(usuario.getUsername())
                .withClaim("username", usuario.getUsername())
                .withClaim("password", usuario.getPassword())
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        return new Token(jwt, usuario.getUsername());
    }

    public Usuario getUserFromToken(String jwt){
        var jwtVerified = JWT.require(algorithm).build().verify(jwt);
        return Usuario.builder()
                .idUsuario(Long.valueOf(jwtVerified.getSubject()))
                .username(jwtVerified.getClaim("username").asString())
                .role(UserRole.ADMIN)
                .build();
    }
}
