package br.com.fiap.aquasense.controller;

import br.com.fiap.aquasense.dto.request.TelefoneRequest;
import br.com.fiap.aquasense.dto.response.TelefoneResponse;
import br.com.fiap.aquasense.service.TelefoneService;
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
@RequestMapping("/telefone")
public class TelefoneController {
    @Autowired
    private TelefoneService telefoneService;

    @PostMapping
    @CacheEvict("telefones")
    @Operation(summary = "Cadastro de telefone.", description = "Cadastro de telefones de um usuário.",
    responses = {
            @ApiResponse(responseCode = "201", description = "Cadastro concluído com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public ResponseEntity<TelefoneResponse> create(@RequestBody TelefoneRequest telefoneRequest){
        var telefoneSaved = telefoneService.create(telefoneRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(telefoneSaved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualização telefones.", description = "Atualizar telefone com base no ID.",
    responses = {
            @ApiResponse(responseCode = "200", description = "Atualização concluida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public ResponseEntity<TelefoneResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody TelefoneRequest telefoneRequest){
        var updated = telefoneService.update(id, telefoneRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atualização telefones.", description = "Atualizar telefone com base no ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Atualização concluida com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public void deleteById(@PathVariable Long id){
        this.telefoneService.delete(id);
    }

    @GetMapping("/{id}")
    @Cacheable("telefoneById")
    @Operation(summary = "Buscar telefones.", description = "Busca de telefone com base no ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca concluida com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<TelefoneResponse> get(@PathVariable Long id){
        var telefoneFound = telefoneService.findById(id);
        return ResponseEntity.ok(telefoneFound);
    }

    @GetMapping("/all")
    @Operation(summary = "Buscar telefones.", description = "Busca de todos os telefones.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca concluida com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<List<TelefoneResponse>> getAll(){
        var telefoneFound = telefoneService.findAll();
        return ResponseEntity.ok(telefoneFound);
    }

}
