package br.com.fiap.aquasense.dto.response;

import br.com.fiap.aquasense.model.EventoAlerta;
import br.com.fiap.aquasense.model.auth.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlertaUsuarioResponse {
    private Long idAlertaUsuario;
    private LocalDateTime dataEnvio;
    private String canalEnvio;
    private String statusEnvio;
    private Long idUsuario;
    private Long idEventoAlerta;
}
