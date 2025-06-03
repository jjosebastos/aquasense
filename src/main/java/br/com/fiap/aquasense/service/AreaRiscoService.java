package br.com.fiap.aquasense.service;

import br.com.fiap.aquasense.dto.request.AreaRiscoRequest;
import br.com.fiap.aquasense.dto.response.AreaRiscoResponse;
import br.com.fiap.aquasense.model.AreaRisco;
import br.com.fiap.aquasense.repository.AreaRiscoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AreaRiscoService {
    @Autowired
    private AreaRiscoRepository areaRiscoRepository;

    public AreaRiscoResponse save(AreaRiscoRequest areaRiscoRequest) {
        var areaRisco = new AreaRisco();
        areaRisco.setNome(areaRiscoRequest.getNome());
        areaRisco.setNivelRisco(areaRiscoRequest.getNivelRisco());
        areaRisco.setTipoRisco(areaRiscoRequest.getTipoRisco());
        areaRisco.setFlagAtivo("S");
        var savedAreaRisco = areaRiscoRepository.save(areaRisco);
        return toAreaRiscoResponse(savedAreaRisco);
    }

    public AreaRiscoResponse update(Long idAreaRisco, AreaRiscoRequest areaRiscoRequest) {
        var areaRiscoFound = getAreaRiscoById(idAreaRisco);
        areaRiscoFound.setNome(areaRiscoRequest.getNome());
        areaRiscoFound.setTipoRisco(areaRiscoRequest.getTipoRisco());
        areaRiscoFound.setNivelRisco(areaRiscoRequest.getNivelRisco());
        areaRiscoFound.setDataAtualizacao(LocalDateTime.now());
        var updatedAreaRisco = areaRiscoRepository.save(areaRiscoFound);
        return toAreaRiscoResponse(updatedAreaRisco);
    }

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
    private AreaRiscoResponse toAreaRiscoResponse(AreaRisco areaRisco) {
        return AreaRiscoResponse.builder()
                .idAreaRisco(areaRisco.getIdAreaRisco())
                .nome(areaRisco.getNome())
                .tipoRisco(areaRisco.getTipoRisco())
                .nivelRisco(areaRisco.getNivelRisco())
                .build();
    }

    private AreaRisco getAreaRiscoById(Long idAreaRisco) {
        return this.areaRiscoRepository.findByIdAreaRisco(idAreaRisco)
                .orElseThrow();
    }
}
