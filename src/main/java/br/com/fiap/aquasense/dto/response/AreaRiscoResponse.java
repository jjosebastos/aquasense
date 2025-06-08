package br.com.fiap.aquasense.dto.response;

import br.com.fiap.aquasense.model.enums.NivelRisco;
import br.com.fiap.aquasense.model.enums.TipoRisco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaRiscoResponse {
    private Long idAreaRisco;
    private String nome;
    private TipoRisco tipoRisco;
    private NivelRisco nivelRisco;
    private String observacaoRisco;
    private double latitude;
    private double longitude;

}
