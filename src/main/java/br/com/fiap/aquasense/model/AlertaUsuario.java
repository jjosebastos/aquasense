package br.com.fiap.aquasense.model;

import br.com.fiap.aquasense.model.auth.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlertaUsuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlertaUsuario;
    private LocalDateTime dataEnvio;
    private String canalEnvio;
    private String statusEnvio;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_alerta")
    private EventoAlerta eventoAlerta;
}
