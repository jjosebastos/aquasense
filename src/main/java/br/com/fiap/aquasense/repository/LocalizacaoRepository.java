package br.com.fiap.aquasense.repository;

import br.com.fiap.aquasense.model.Localizacao;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Pageable;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long>, JpaSpecificationExecutor<Localizacao> {

    Page<Localizacao> findByNomeContaining(String nome, Pageable pageable);
}
