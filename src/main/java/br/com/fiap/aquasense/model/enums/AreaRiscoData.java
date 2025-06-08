package br.com.fiap.aquasense.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRiscoData {

    @JsonProperty("nm_local")
    private String nmLocalGeral; // Nome do local mais abrangente

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    // Campos que vão para T_AS_AREA_RISCO (e o id_localizacao será setado depois)
    @JsonProperty("nm_area")
    private String nmAreaEspecifica; // Nome da área de risco específica

    @JsonProperty("tp_risco")
    private String tpRisco;

    @JsonProperty("nv_risco")
    private String nvRisco;

    @JsonProperty("ds_observacao_risco")
    private String dsObservacaoRisco;

    // Campo que será preenchido após a persistência em T_AS_LOCALIZACAO
    private Long idLocalizacao; // Chave estrangeira para T_AS_LOCALIZACAO

}
