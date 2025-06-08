package br.com.fiap.aquasense.model;

import br.com.fiap.aquasense.model.auth.Usuario;
import br.com.fiap.aquasense.model.enums.StatusLocalizacao;
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
@Table(name = "T_AS_LOCALIZACAO")
public class Localizacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localizacao")
    private Long idLocalizacao;

    @Column(name = "nm_local")
    private String nome;

    @Column(name = "vl_atitude") 
    private Double latitude; 

    @Column(name = "vl_longitude")
    private Double longitude;

    @Column(name = "dt_ultima_atualizacao")
    private LocalDateTime dataUltimaAtualizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_localizacao")
    private StatusLocalizacao status;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}