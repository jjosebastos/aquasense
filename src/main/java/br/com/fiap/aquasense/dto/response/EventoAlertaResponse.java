package br.com.fiap.aquasense.dto.response;

import br.com.fiap.aquasense.model.enums.NivelRisco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventoAlertaResponse {

    private Long idEventoAlerta;
    private LocalDateTime dataHoraGeracao;
    private String mensagem;
    private String fonteDados;
    private NivelRisco nivelRisco;
    private Long idAreaRisco;
}
