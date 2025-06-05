package br.com.fiap.aquasense.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemperaturaResponse {
    private Long idTemperatura;
    private LocalDateTime dataRegistro;
    private Double temperatura;
    private int umidade;
    private int pressao;
    private Long idLocalizacao;
}
