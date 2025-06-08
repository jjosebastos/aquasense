package br.com.fiap.aquasense.controller;

import br.com.fiap.aquasense.model.auth.Usuario;
import br.com.fiap.aquasense.model.auth.UserRole; // <<<< ADICIONE ESTA IMPORTAÇÃO
import br.com.fiap.aquasense.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public Usuario create(@Valid @RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRole(UserRole.USER);
        return usuarioRepository.save(usuario);
    }
}