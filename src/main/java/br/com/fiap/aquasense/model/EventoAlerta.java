package br.com.fiap.aquasense.model;

import br.com.fiap.aquasense.model.enums.NivelRisco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventoAlerta {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idEventoAlerta;
    private LocalDateTime dataHoraGeracao;
    private String mensagem;
    private String fonteDados;
    private NivelRisco nivelRisco;
    @ManyToOne
    @JoinColumn (name = "id_area_risco")
    private AreaRisco areaRisco;
}
