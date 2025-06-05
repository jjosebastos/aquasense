package br.com.fiap.aquasense.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AlertaUsuarioRequest {

    @NotNull @PastOrPresent
    private LocalDateTime dataEnvio;
    @NotBlank
    private String canalEnvio;
    @NotBlank
    private String statusEnvio;
    @NotNull
    private Long idUsuario;
    @NotNull
    private Long idEventoAlerta;
}
