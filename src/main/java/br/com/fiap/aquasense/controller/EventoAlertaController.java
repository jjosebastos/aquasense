package br.com.fiap.aquasense.controller;

import br.com.fiap.aquasense.dto.request.EventoAlertaRequest;
import br.com.fiap.aquasense.dto.response.EventoAlertaResponse;
import br.com.fiap.aquasense.service.EventoAlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evento-alerta")
public class EventoAlertaController {

    @Autowired
    private EventoAlertaService eventoAlertaService;

    @PostMapping
    @CacheEvict("evento-alerta")
    @Operation(summary = "Criação de Eventos de Alerta.", description = "Criar Alertas de desastres naturais.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Concluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<EventoAlertaResponse> create(@RequestBody @Valid EventoAlertaRequest eventoAlertaRequest) {
        var savedEventoAlerta = this.eventoAlertaService.save(eventoAlertaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEventoAlerta);
    }

    @PutMapping("/{id}")
    @CacheEvict("evento-alerta")
    @Operation(summary = "Atualização de Evento alerta.", description = "Atualizar Alertas de desastres naturais.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cadastro concluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<EventoAlertaResponse> update(@PathVariable Long id, @RequestBody @Valid EventoAlertaRequest request) {
        var updatedEventoAlerta = this.eventoAlertaService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEventoAlerta);
    }

    @GetMapping("/{id}")
    @Cacheable("evento-alerta")
    @Operation(summary = "Busca Evento Alertas.", description = "Buscar todas os alertas de desastres naturais por Id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca concluída com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<EventoAlertaResponse> get(@PathVariable Long id) {
        var foundEventoAlerta = this.eventoAlertaService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundEventoAlerta);
    }

    @GetMapping("/all")
    @Operation(summary = "Busca Eventos Alertas.", description = "Buscar todas os alertas de desastre natural.",
            responses = {
                    @ApiResponse(responseCode = "200", description = " concluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<List<EventoAlertaResponse>> getAll() {
        var foundEventoAlertaList = this.eventoAlertaService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(foundEventoAlertaList);
    }
}