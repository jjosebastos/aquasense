package br.com.fiap.aquasense.repository;

import br.com.fiap.aquasense.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long>, JpaSpecificationExecutor<Localizacao> {

}
