package br.com.fiap.aquasense.service;

import br.com.fiap.aquasense.dto.request.EventoAlertaRequest;
import br.com.fiap.aquasense.dto.response.EventoAlertaResponse;
import br.com.fiap.aquasense.model.AreaRisco;
import br.com.fiap.aquasense.model.EventoAlerta;
import br.com.fiap.aquasense.repository.AreaRiscoRepository;
import br.com.fiap.aquasense.repository.EventoAlertaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoAlertaService {

    @Autowired
    private EventoAlertaRepository eventoAlertaRepository;

    @Autowired
    private AreaRiscoRepository areaRiscoRepository;
    @Transactional
    public EventoAlertaResponse save(EventoAlertaRequest request) {
        var areaRiscoFound = this.getAreaRiscoById(request.getIdAreaRisco());
        var eventoAlerta = new EventoAlerta();
        eventoAlerta.setMensagem(request.getMensagem());
        eventoAlerta.setDataHoraGeracao(request.getDataHoraGeracao());
        eventoAlerta.setFonteDados(request.getFonteDados());
        eventoAlerta.setNivelRisco(request.getNivelRisco());
        eventoAlerta.setAreaRisco(areaRiscoFound);
        var savedEventoAlerta = this.eventoAlertaRepository.save(eventoAlerta);
        return toEventoAlertaResponse(savedEventoAlerta);
    }

    @Transactional
    public EventoAlertaResponse update(Long idEventoAlerta, EventoAlertaRequest request) {
        var eventoAlertaFound = this.getEventoAlertaById(idEventoAlerta);
        eventoAlertaFound.setMensagem(request.getMensagem());
        eventoAlertaFound.setFonteDados(request.getFonteDados());
        eventoAlertaFound.setNivelRisco(request.getNivelRisco());
        eventoAlertaFound.setAreaRisco(this.getAreaRiscoById(request.getIdAreaRisco()));
        var updatedEventoAlerta = this.eventoAlertaRepository.save(eventoAlertaFound);
        return toEventoAlertaResponse(updatedEventoAlerta);
    }


    public EventoAlertaResponse getById(Long idEventoAlerta) {
        var eventoAlertaFound = this.getEventoAlertaById(idEventoAlerta);
        return toEventoAlertaResponse(eventoAlertaFound);
    }

    public List<EventoAlertaResponse> getAll() {
        var eventoAlertaList = this.eventoAlertaRepository.findAll();
        return eventoAlertaList.stream()
                .map(this::toEventoAlertaResponse)
                .toList();
    }

    private EventoAlertaResponse toEventoAlertaResponse(EventoAlerta eventoAlerta) {
        return EventoAlertaResponse.builder()
                .idEventoAlerta(eventoAlerta.getIdEventoAlerta())
                .dataHoraGeracao(eventoAlerta.getDataHoraGeracao())
                .mensagem(eventoAlerta.getMensagem())
                .fonteDados(eventoAlerta.getFonteDados())
                .nivelRisco(eventoAlerta.getNivelRisco())
                .idAreaRisco(eventoAlerta.getAreaRisco().getIdAreaRisco())
                .build();

    }

    private EventoAlerta getEventoAlertaById(Long idEventoAlerta) {
        return this.eventoAlertaRepository.findById(idEventoAlerta)
                .orElseThrow();
    }
    private AreaRisco getAreaRiscoById(Long idAreaRisco) {
        return this.areaRiscoRepository.findById(idAreaRisco).orElseThrow();
    }
}
