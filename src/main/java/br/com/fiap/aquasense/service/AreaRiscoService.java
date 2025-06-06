package br.com.fiap.aquasense.service;

import br.com.fiap.aquasense.dto.request.AreaRiscoRequest;
import br.com.fiap.aquasense.dto.response.AreaRiscoResponse;
import br.com.fiap.aquasense.model.AreaRisco;
import br.com.fiap.aquasense.repository.AreaRiscoRepository;
import br.com.fiap.aquasense.repository.LocalizacaoRepository;
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
    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Transactional
    public AreaRiscoResponse save(AreaRiscoRequest areaRiscoRequest) {
        var localizacaoId = areaRiscoRequest.getIdLocalizacao();
        var localizacaoFound = localizacaoRepository.findById(localizacaoId)
                .orElseThrow();
        var areaRisco = new AreaRisco();
        areaRisco.setNome(areaRiscoRequest.getNome());
        areaRisco.setNivelRisco(areaRiscoRequest.getNivelRisco());
        areaRisco.setTipoRisco(areaRiscoRequest.getTipoRisco());
        areaRisco.setFlagAtivo("S");
        areaRisco.setLocalizacao(localizacaoFound);
        var savedAreaRisco = areaRiscoRepository.save(areaRisco);
        return toAreaRiscoResponse(savedAreaRisco);
    }

    @Transactional
    public AreaRiscoResponse update(Long idAreaRisco, AreaRiscoRequest areaRiscoRequest) {
        var idLocalizacao = areaRiscoRequest.getIdLocalizacao();
        var localizacaoFound = localizacaoRepository.findById(idLocalizacao)
                .orElseThrow();
        var areaRiscoFound = getAreaRiscoById(idAreaRisco);
        areaRiscoFound.setNome(areaRiscoRequest.getNome());
        areaRiscoFound.setTipoRisco(areaRiscoRequest.getTipoRisco());
        areaRiscoFound.setNivelRisco(areaRiscoRequest.getNivelRisco());
        areaRiscoFound.setDataAtualizacao(LocalDateTime.now());
        areaRiscoFound.setLocalizacao(localizacaoFound);
        var updatedAreaRisco = areaRiscoRepository.save(areaRiscoFound);
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
    private AreaRiscoResponse toAreaRiscoResponse(AreaRisco areaRisco) {
        return AreaRiscoResponse.builder()
                .idAreaRisco(areaRisco.getIdAreaRisco())
                .nome(areaRisco.getNome())
                .tipoRisco(areaRisco.getTipoRisco())
                .nivelRisco(areaRisco.getNivelRisco())
                .dataAtualizacao(areaRisco.getDataAtualizacao())
                .idLocalizacao(areaRisco.getLocalizacao().getIdLocalizacao())
                .build();
    }

    private AreaRisco getAreaRiscoById(Long idAreaRisco) {
        return this.areaRiscoRepository.findByIdAreaRisco(idAreaRisco)
                .orElseThrow();
    }
}
