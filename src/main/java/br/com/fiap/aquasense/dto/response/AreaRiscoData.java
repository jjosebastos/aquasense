package br.com.fiap.aquasense.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaRiscoData {


    @JsonProperty("nm_area")
    private String nmArea;

    @JsonProperty("tp_risco")
    private String tpRisco;

    @JsonProperty("nv_risco")
    private String nvRisco;

    @JsonProperty("ds_observacao_risco") // Corresponde EXATAMENTE à chave JSON "ds_observacao_risco" do Gemini
    private String dsObservacaoRisco; // Observações detalhadas (campo interno Java)

    @JsonProperty("latitude") // Corresponde EXATAMENTE à chave JSON "latitude" do Gemini
    private Double latitude; // Coordenada Latitude (campo interno Java)

    @JsonProperty("longitude") // Corresponde EXATAMENTE à chave JSON "longitude" do Gemini
    private Double longitude; // Coordenada Longitude (campo interno Java)


}