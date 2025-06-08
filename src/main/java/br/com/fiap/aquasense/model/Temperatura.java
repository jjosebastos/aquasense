package br.com.fiap.aquasense.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Table(name = "t_as_temperatura")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Temperatura {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_temperatura")
    private Long idTemperatura;
    @Column(name = "vl_temperatura")
    private Double temperatura;
    @Column(name = "vl_umidade")
    private int umidade;
    @Column(name = "vl_pressao")
    private int pressao;

    @ManyToOne
    @JoinColumn(name = "id_localizacao")
    private Localizacao localizacao;
}
