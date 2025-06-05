package br.com.fiap.aquasense.repository;

import br.com.fiap.aquasense.model.Temperatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperaturaRepository extends JpaRepository<Temperatura, Long> {
}
