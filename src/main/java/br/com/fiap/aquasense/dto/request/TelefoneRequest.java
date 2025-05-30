package br.com.fiap.aquasense.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelefoneRequest {

    @NotBlank
    private String ddd;
    @NotBlank
    private String numero;
    @NotBlank
    private String tipo;
    @NotNull
    private Long idUsuario;

}
