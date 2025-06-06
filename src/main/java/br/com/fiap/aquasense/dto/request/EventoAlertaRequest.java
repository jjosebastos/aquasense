package br.com.fiap.aquasense.dto.request;

import br.com.fiap.aquasense.model.AreaRisco;
import br.com.fiap.aquasense.model.enums.NivelRisco;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventoAlertaRequest {

    @NotNull @Size(min = 1, max = 200)
    private String mensagem;
    @NotNull
    private String fonteDados;
    @NotNull @Enumerated(EnumType.STRING)
    private NivelRisco nivelRisco;
    @NotNull
    private Long idAreaRisco;
}
