package br.com.fiap.aquasense.service;

import br.com.fiap.aquasense.dto.request.AreaRiscoRequest;
import br.com.fiap.aquasense.dto.response.AreaRiscoResponse;
import br.com.fiap.aquasense.model.AreaRisco;
import br.com.fiap.aquasense.repository.AreaRiscoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AreaRiscoService {
    @Autowired
    private AreaRiscoRepository areaRiscoRepository;


    @Transactional
    public List<AreaRiscoResponse> save(List<AreaRiscoRequest> areaRiscoRequest) {
        var saveListAreaRisco = areaRiscoRequest.stream()
                .map(this::mapperAreaRisco)
                .map(areaRiscoRepository::save)
                .toList();
        return saveListAreaRisco.stream()
                .map(this::toAreaRiscoResponse)
                .toList();
    }

    @Transactional
    public AreaRiscoResponse update(Long id, AreaRiscoRequest areaRiscoRequest) {
        AreaRisco existingAreaRisco = areaRiscoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Área de risco não encontrada para o ID: " + id));

        existingAreaRisco.setNomeArea(areaRiscoRequest.getNm_area());
        existingAreaRisco.setTipoRisco(areaRiscoRequest.getTp_risco());
        existingAreaRisco.setNivelRisco(areaRiscoRequest.getNv_risco());
        existingAreaRisco.setObservacaoRisco(areaRiscoRequest.getDs_observacao_risco());
        existingAreaRisco.setLatitude(areaRiscoRequest.getLatitude());
        existingAreaRisco.setLongitude(areaRiscoRequest.getLongitude());
        existingAreaRisco.setDataAtualizacao(LocalDateTime.now());
        AreaRisco updatedAreaRisco = areaRiscoRepository.save(existingAreaRisco);
        return toAreaRiscoResponse(updatedAreaRisco);
    }

    @Transactional
    public void deleteById(Long idAreaRisco) {
        var areaRiscoFound = getAreaRiscoById(idAreaRisco);
        areaRiscoFound.setFlagAtivo("N");
        areaRiscoRepository.save(areaRiscoFound);
    }

    public List<AreaRiscoResponse> getAll(){
        var foundAllAreaRisco = this.areaRiscoRepository.findAllByFlagAtivoStatus();
        if(foundAllAreaRisco.isEmpty()){
            throw new NoSuchElementException();
        }
        return foundAllAreaRisco
                .stream()
                .map(this::toAreaRiscoResponse)
                .toList();

    }

    public AreaRiscoResponse getById(Long idAreaRisco) {
        return toAreaRiscoResponse(this.getAreaRiscoById(idAreaRisco));
    }

    private AreaRisco mapperAreaRisco(AreaRiscoRequest areaRiscoRequest) {
        return AreaRisco.builder()
                .nomeArea(areaRiscoRequest.getNm_area())
                .observacaoRisco(areaRiscoRequest.getDs_observacao_risco())
                .nivelRisco(areaRiscoRequest.getNv_risco())
                .tipoRisco(areaRiscoRequest.getTp_risco())
                .latitude(areaRiscoRequest.getLatitude())
                .longitude(areaRiscoRequest.getLongitude())
                .flagAtivo("S")
                .dataAtualizacao(LocalDateTime.now())
                .build();
    }

    private AreaRiscoResponse toAreaRiscoResponse(AreaRisco areaRisco) {
        return AreaRiscoResponse.builder()
                .idAreaRisco(areaRisco.getIdAreaRisco())
                .nome(areaRisco.getNomeArea())
                .tipoRisco(areaRisco.getTipoRisco())
                .nivelRisco(areaRisco.getNivelRisco())
                .observacaoRisco(areaRisco.getObservacaoRisco())
                .latitude(areaRisco.getLatitude())
                .longitude(areaRisco.getLongitude())
                .build();
    }

    private AreaRisco getAreaRiscoById(Long idAreaRisco) {
        return this.areaRiscoRepository.findByIdAreaRisco(idAreaRisco)
                .orElseThrow();
    }
}
