package br.com.fiap.aquasense.repository;

import br.com.fiap.aquasense.model.AreaRisco;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AreaRiscoRepository extends JpaRepository<AreaRisco, Long>, JpaSpecificationExecutor<AreaRisco> {


    @Query("SELECT ar FROM AreaRisco ar WHERE ar.flagAtivo = 'S'")
    List<AreaRisco> findAllByFlagAtivoStatus();

    @Query("SELECT ar FROM AreaRisco ar WHERE ar.flagAtivo = 'S' AND ar.idAreaRisco = :idAreaRisco ")
    Optional<AreaRisco> findByIdAreaRisco(@PathParam("id") Long idAreaRisco);
}
