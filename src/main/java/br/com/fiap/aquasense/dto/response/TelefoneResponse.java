package br.com.fiap.aquasense.dto.response;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelefoneResponse {

    private Long idTelefone;
    private String ddd;
    private String numero;
    private String tipo;
    private Long idUsuario;

}
