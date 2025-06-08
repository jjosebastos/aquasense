package br.com.fiap.aquasense.dto.request;

// Removidos imports desnecessários para DTOs (Localizacao, EnumType, Enumerated)
// Removido import com.fasterxml.jackson.annotation.JsonProperty, pois o idUsuario não estará mais aqui

import br.com.fiap.aquasense.model.enums.StatusLocalizacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalizacaoRequest {

    @NotBlank(message = "O nome do local não pode estar em branco")
    private String nome;

    @NotNull(message = "A latitude não pode ser nula")
    private Double latitude;

    @NotNull(message = "A longitude não pode ser nula")
    private Double longitude;

    @NotNull(message = "O status da localização não pode ser nulo")
    private StatusLocalizacao status;
}