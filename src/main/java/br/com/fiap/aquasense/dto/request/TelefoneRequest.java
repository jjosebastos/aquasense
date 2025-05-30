package br.com.fiap.aquasense.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelefoneRequest {

    @NotBlank
    @Size(min = 2, max = 3, message = "DDD deve ter entre 2 e 3 dígitos")
    @Pattern(regexp = "^[0-9]*$", message = "DDD deve conter apenas números")
    private String ddd;
    @NotBlank
    @Size(min = 8, max = 9)
    @Pattern(regexp = "^[0-9]*$")
    private String numero;
    @NotBlank
    private String tipo;
    @NotNull
    private Long idUsuario;

}
