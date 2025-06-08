package br.com.fiap.aquasense.model;

import br.com.fiap.aquasense.model.enums.NivelRisco;
import br.com.fiap.aquasense.model.enums.TipoRisco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_AS_AREA_RISCO")
@Data // Gera getters, setters, toString, equals e hashCode
@NoArgsConstructor // Construtor sem argumentos
@AllArgsConstructor // Construtor com todos os argumentos
@Builder // Padrão de projeto Builder para criação de objetos
public class AreaRisco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento para H2/MySQL
    @Column(name = "id_area_risco")
    private Long idAreaRisco;

    @Column(name = "nm_area", nullable = false, length = 100)
    private String nomeArea; // Nome da área de risco específica, agora como nm_area

    @Column(name = "tp_risco", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private TipoRisco tipoRisco;

    @Column(name = "nv_risco", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private NivelRisco nivelRisco;

    @Column(name = "ds_observacao_risco", length = 1000)
    private String observacaoRisco;

    @Column(name = "vl_latitude")
    private Double latitude;

    @Column(name = "vl_longitude")
    private Double longitude;

    @Column(name = "dt_ultima_atualizacao", columnDefinition = "TIMESTAMP")
    private LocalDateTime dataAtualizacao;

    @Column(name = "fl_ativo", length = 1)
    private String flagAtivo;
}
