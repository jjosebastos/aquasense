package br.com.fiap.aquasense.repository;

import br.com.fiap.aquasense.model.AlertaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlertaUsuarioRepository extends JpaRepository<AlertaUsuario, Long>, JpaSpecificationExecutor<AlertaUsuario> {
}
