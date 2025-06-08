package br.com.fiap.aquasense.repository;

import br.com.fiap.aquasense.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {

    Mono<Localizacao> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
