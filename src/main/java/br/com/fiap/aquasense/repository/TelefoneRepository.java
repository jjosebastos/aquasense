package br.com.fiap.aquasense.repository;

import br.com.fiap.aquasense.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
