package br.com.fiap.aquasense.repository;

import br.com.fiap.aquasense.model.EventoAlerta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoAlertaRepository extends JpaRepository<EventoAlerta, Long> {
}
