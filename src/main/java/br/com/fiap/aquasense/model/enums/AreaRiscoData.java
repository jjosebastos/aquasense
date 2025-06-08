package br.com.fiap.aquasense.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRiscoData {

    @JsonProperty("nm_area") // Mapeia para a chave "nm_area" no JSON do Gemini
    private String nmArea; // Nome da área de risco específica, conforme a T_AS_AREA_RISCO

    @JsonProperty("tp_risco") // Mapeia para a chave "tp_risco" no JSON do Gemini
    private String tpRisco;

    @JsonProperty("nv_risco") // Mapeia para a chave "nv_risco" no JSON do Gemini
    private String nvRisco;

    @JsonProperty("ds_observacao_risco") // Mapeia para a chave "ds_observacao_risco" no JSON do Gemini
    private String dsObservacaoRisco;

    @JsonProperty("latitude") // Mapeia para a chave "latitude" no JSON do Gemini
    private Double latitude;

    @JsonProperty("longitude") // Mapeia para a chave "longitude" no JSON do Gemini
    private Double longitude;

}
