package br.com.fiap.aquasense.controller;

import br.com.fiap.aquasense.model.auth.Credentials;
import br.com.fiap.aquasense.model.auth.Token;
import br.com.fiap.aquasense.model.auth.Usuario;
import br.com.fiap.aquasense.service.AuthService;
import br.com.fiap.aquasense.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public Token login(@RequestBody Credentials credentials) {
        Usuario usuario = (Usuario) authService.loadUserByUsername(credentials.username());
        if(!passwordEncoder.matches(credentials.password(), usuario.getPassword())) {
            throw new BadCredentialsException("Senha incorreta");
        }
        return tokenService.createToken(usuario);
    }
}
