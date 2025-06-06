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
    private String nome;
    @NotBlank @Enumerated(EnumType.STRING)
    private TipoRisco tipoRisco;
    @NotBlank @Enumerated(EnumType.STRING)
    private NivelRisco nivelRisco;
    @NotNull
    private Long idLocalizacao;
}
