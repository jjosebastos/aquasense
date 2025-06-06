package br.com.fiap.aquasense.model;

import br.com.fiap.aquasense.model.enums.NivelRisco;
import br.com.fiap.aquasense.model.enums.TipoRisco;
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
@Table(name = "t_as_area_risco")
public class AreaRisco {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_area_risco")
    private Long idAreaRisco;
    @Column(name = "nm_area")
    private String nome;
    @Column(name = "tp_risco")
    @Enumerated(EnumType.STRING)
    private TipoRisco tipoRisco;

    @Column(name = "nv_risco")
    @Enumerated(EnumType.STRING)
    private NivelRisco nivelRisco;

    @Column(name = "dt_ultima_atualizacao")
    private LocalDateTime dataAtualizacao;
    private String flagAtivo;
    @ManyToOne
    @JoinColumn(name = "id_localizcao")
    private Localizacao localizacao;
}
