package br.com.fiap.aquasense.model;

import br.com.fiap.aquasense.model.auth.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_as_telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_telefone")
    private Long idTelefone;

    @Column(name = "nr_ddd")
    private String ddd;
    @Column(name = "nr_telefone")
    private String numero;
    @Column(name = "tp_telefone")
    private String tipo;

    @Column(name = "dt_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}
