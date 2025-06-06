package br.com.fiap.aquasense.repository;

import br.com.fiap.aquasense.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long>, JpaSpecificationExecutor<Localizacao> {

    Optional<Localizacao> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
