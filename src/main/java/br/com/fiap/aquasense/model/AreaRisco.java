package br.com.fiap.aquasense.model;

import br.com.fiap.aquasense.model.enums.NivelRisco;
import br.com.fiap.aquasense.model.enums.TipoRisco;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class AreaRisco {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAreaRisco;
    private String nome;
    private TipoRisco tipoRisco;
    private NivelRisco nivelRisco;
    private LocalDateTime dataAtualizacao;
    private String flagAtivo;
}
