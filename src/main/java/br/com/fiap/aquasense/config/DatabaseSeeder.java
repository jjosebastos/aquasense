package br.com.fiap.aquasense.config;

import br.com.fiap.aquasense.model.auth.UserRole;
import br.com.fiap.aquasense.model.auth.Usuario;
import br.com.fiap.aquasense.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DatabaseSeeder {

//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//    @PostConstruct
//    public void init(){
//        usuarioRepository.saveAll(List.of(
//                Usuario.builder()
//                        .username("jjosebastos")
//                        .created(LocalDate.now())
//                        .password(passwordEncoder.encode("12345678"))
//                        .email("jjbastos@gmail.com")
//                        .role(UserRole.ADMIN)
//                        .build(),
//
//                Usuario.builder()
//                        .username("mmaria")
//                        .password(passwordEncoder.encode("12345678"))
//                        .email("mmaria@gmail.com")
//                        .created(LocalDate.now())
//                        .role(UserRole.USER).build()
//         ));
//
//            }
}
