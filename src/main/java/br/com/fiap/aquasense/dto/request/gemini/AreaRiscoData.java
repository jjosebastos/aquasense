package br.com.fiap.aquasense.dto.request.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaRiscoData { // DTO para a resposta do Gemini

    private String nm_area;
    private String tp_risco;
    private String nv_risco;
    private String ds_observacao_risco; // Corresponde ao seu novo campo
    private Double latitude;
    private Double longitude;
}