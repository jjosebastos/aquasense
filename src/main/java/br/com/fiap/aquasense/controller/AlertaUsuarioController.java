package br.com.fiap.aquasense.controller;

import br.com.fiap.aquasense.dto.request.AlertaUsuarioRequest;
import br.com.fiap.aquasense.dto.response.AlertaUsuarioResponse;
import br.com.fiap.aquasense.service.AlertaUsuarioService;
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
@RequestMapping("/alerta-usuario")
public class AlertaUsuarioController {

    @Autowired
    private AlertaUsuarioService alertaUsuarioService;

    @PostMapping
    @CacheEvict("alerta-usuario")
    @Operation(summary = "Criação de Eventos de Alerta.", description = "Criar Alertas de desastres naturais.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Concluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<AlertaUsuarioResponse> create(@RequestBody @Valid AlertaUsuarioRequest request) {
        var saved = this.alertaUsuarioService.save(request);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    @CacheEvict("alerta-usuario")
    @Operation(summary = "Atualização de Evento alerta.", description = "Atualizar Alertas de desastres naturais.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cadastro concluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<AlertaUsuarioResponse> update(@PathVariable Long id,
                                                        @RequestBody @Valid AlertaUsuarioRequest request) {
        var updated = this.alertaUsuarioService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/all")
    @Operation(summary = "Busca Evento Alertas.", description = "Buscar todos os alertas de desastres cadastrados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca concluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<List<AlertaUsuarioResponse>> getAll() {
        var alertaUsuarioList = this.alertaUsuarioService.getAll();
        return ResponseEntity.ok(alertaUsuarioList);
    }

    @GetMapping("/{id}")
    @Cacheable("alerta-usuario")
    @Operation(summary = "Busca de Evento Alerta.", description = "Buscar  os alertas de desastres naturais por Id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca concluída com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<AlertaUsuarioResponse> getById(@PathVariable Long id) {
        var alertaUsuario = this.alertaUsuarioService.getById(id);
        return ResponseEntity.ok(alertaUsuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remoção de Eventos Alertas.", description = "Remove os Eventos Alertas com base no Id.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Busca concluída com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public void delete(@PathVariable Long id) {
        this.alertaUsuarioService.deleteById(id);
    }
}
