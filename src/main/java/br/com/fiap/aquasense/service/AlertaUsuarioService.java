package br.com.fiap.aquasense.service;

import br.com.fiap.aquasense.dto.request.AlertaUsuarioRequest;
import br.com.fiap.aquasense.dto.response.AlertaUsuarioResponse;
import br.com.fiap.aquasense.model.AlertaUsuario;
import br.com.fiap.aquasense.model.EventoAlerta;
import br.com.fiap.aquasense.model.auth.Usuario;
import br.com.fiap.aquasense.repository.AlertaUsuarioRepository;
import br.com.fiap.aquasense.repository.EventoAlertaRepository;
import br.com.fiap.aquasense.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AlertaUsuarioService {

    @Autowired
    private AlertaUsuarioRepository alertaUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoAlertaRepository eventoAlertaRepository;

    @Transactional
    public AlertaUsuarioResponse save(AlertaUsuarioRequest request) {
        var eventoAlertaFound = this.getEventoAlerta(request.getIdEventoAlerta());
        var usuarioFound = this.getUsuario(request.getIdUsuario());
        var alertaUsuario = new AlertaUsuario();
        alertaUsuario.setDataEnvio(request.getDataEnvio());
        alertaUsuario.setStatusEnvio(request.getStatusEnvio());
        alertaUsuario.setCanalEnvio(request.getCanalEnvio());
        alertaUsuario.setEventoAlerta(eventoAlertaFound);
        alertaUsuario.setUsuario(usuarioFound);
        var savedAlertaUsuario = this.alertaUsuarioRepository.save(alertaUsuario);
        return this.toAlertaUsuarioResponse(savedAlertaUsuario);
    }

    @Transactional
    public AlertaUsuarioResponse update(Long idAlerta, AlertaUsuarioRequest request) {
        var alertaUsuarioFound = getAlerta(idAlerta);
        var usuarioFound = getUsuario(request.getIdUsuario());
        var eventoAlertaFound = this.getEventoAlerta(request.getIdEventoAlerta());
        var alertaUsuario = new AlertaUsuario();
        alertaUsuario.setDataEnvio(request.getDataEnvio());
        alertaUsuario.setStatusEnvio(request.getStatusEnvio());
        alertaUsuario.setCanalEnvio(request.getCanalEnvio());
        alertaUsuario.setEventoAlerta(eventoAlertaFound);
        alertaUsuario.setUsuario(usuarioFound);
        var savedAlertaUsuario = this.alertaUsuarioRepository.save(alertaUsuario);

        return this.toAlertaUsuarioResponse(savedAlertaUsuario);
    }

    @Transactional
    public void deleteById(Long idAlerta) {
        var alertaUsuarioFound = this.getAlerta(idAlerta);
        if(alertaUsuarioFound == null) {
            throw new NoSuchElementException();
        }
        this.alertaUsuarioRepository.deleteById(idAlerta);
    }

    public List<AlertaUsuarioResponse> getAll() {
        var alertaUsuarioFound = this.alertaUsuarioRepository.findAll();
        if(alertaUsuarioFound.isEmpty()) {
            throw new NoSuchElementException();
        }
        return alertaUsuarioFound
                .stream()
                .map(this::toAlertaUsuarioResponse)
                .toList();
    }

    public AlertaUsuarioResponse getById(Long idAlerta) {
        var alertaUsuarioFound = this.getAlerta(idAlerta);
        if(alertaUsuarioFound == null) {
            throw new NoSuchElementException();
        }
        return this.toAlertaUsuarioResponse(alertaUsuarioFound);
    }

    private AlertaUsuarioResponse toAlertaUsuarioResponse(AlertaUsuario alertaUsuario) {
        return AlertaUsuarioResponse.builder()
                .idAlertaUsuario(alertaUsuario.getIdAlertaUsuario())
                .canalEnvio(alertaUsuario.getCanalEnvio())
                .dataEnvio(alertaUsuario.getDataEnvio())
                .idUsuario(alertaUsuario.getUsuario().getIdUsuario())
                .idEventoAlerta(alertaUsuario.getEventoAlerta().getIdEventoAlerta())
                .build();
    }

    private Usuario getUsuario(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElseThrow();
    }

    private EventoAlerta getEventoAlerta(Long idAlerta) {
        return eventoAlertaRepository.findById(idAlerta).orElseThrow();
    }

    private AlertaUsuario getAlerta(Long idAlerta) {
        return alertaUsuarioRepository.findById(idAlerta)
                .orElseThrow();
    }

}
