package br.com.fiap.aquasense.model;

import br.com.fiap.aquasense.model.enums.NivelRisco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Table(name = "T_AS_EVENTO_ALERTA")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EventoAlerta {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento_alerta")
    private Long idEventoAlerta;
    @Column(name = "dt_hora_geracao")
    private LocalDateTime dataHoraGeracao;
    @Column(name = "ds_mensagem")
    private String mensagem;
    @Column(name = "ft_dados")
    private String fonteDados;
    @Column(name = "nv_risco")
    @Enumerated(EnumType.STRING)
    private NivelRisco nivelRisco;
    @ManyToOne
    @JoinColumn (name = "id_area_risco")
    private AreaRisco areaRisco;
}
