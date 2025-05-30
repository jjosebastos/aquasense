package br.com.fiap.aquasense.dto.request;

import br.com.fiap.aquasense.model.Localizacao;
import br.com.fiap.aquasense.model.enums.StatusLocalizacao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LocalizacaoRequest {

    @NotNull
    private Long idUsuario;
    @NotBlank
    private String nome;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    private LocalDateTime dataUltimaAtualizacao;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusLocalizacao status;
}
