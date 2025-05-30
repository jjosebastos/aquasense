package br.com.fiap.aquasense.model;

import br.com.fiap.aquasense.model.auth.Usuario;
import br.com.fiap.aquasense.model.enums.StatusLocalizacao;
import ch.qos.logback.core.status.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Localizacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLocalizacao;
    private String nome;
    private Double latitude;
    private Double longitude;
    private LocalDateTime dataUltimaAtualizacao;
    private StatusLocalizacao status;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}
