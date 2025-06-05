package br.com.fiap.aquasense.model;

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
public class Temperatura {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTemperatura;
    private LocalDateTime dataRegistro;
    private Double temperatura;
    private int umidade;
    private int pressao;

    @ManyToOne
    @JoinColumn(name = "id_localizacao")
    private Localizacao localizacao;
}
