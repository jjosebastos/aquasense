package br.com.fiap.aquasense.dto.request;

import br.com.fiap.aquasense.model.enums.NivelRisco;
import br.com.fiap.aquasense.model.enums.TipoRisco;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
public class AreaRiscoRequest {
    @NotBlank @Length(min = 1, max = 100)
    private String nm_area;
    @NotBlank @Enumerated(EnumType.STRING)
    private TipoRisco tp_risco;
    @NotBlank @Enumerated(EnumType.STRING)
    private NivelRisco nv_risco;
    @NotBlank
    private String ds_observacao_risco;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
}
