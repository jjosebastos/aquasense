package br.com.fiap.aquasense.controller;

import br.com.fiap.aquasense.dto.request.AreaRiscoRequest;
import br.com.fiap.aquasense.dto.response.AreaRiscoResponse;
import br.com.fiap.aquasense.model.AreaRisco;
import br.com.fiap.aquasense.model.AreaRiscoFilter;
import br.com.fiap.aquasense.model.Localizacao;
import br.com.fiap.aquasense.repository.AreaRiscoRepository;
import br.com.fiap.aquasense.repository.LocalizacaoRepository;
import br.com.fiap.aquasense.service.AreaRiscoService;
import br.com.fiap.aquasense.specification.AreaRiscoSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/area-risco")
public class AreaRiscoController {

    @Autowired

    public AreaRiscoService areaRiscoService;
    @Autowired
    private AreaRiscoRepository areaRiscoRepository;


    @GetMapping
    @Operation(summary = "Busca de Areas Risco", description = "Busca de Areas Risco com base em filters",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Requisicao invalida."),
                    @ApiResponse(responseCode = "404", description = "Enderecos não encontrados.")
            })
    public Page<AreaRisco> index(
            AreaRiscoFilter filter,
            @PageableDefault(size = 10, sort = "tipoRisco", direction = Sort.Direction.DESC) Pageable pageable
    ){
        var specification = AreaRiscoSpecification.withFilters(filter);
        return areaRiscoRepository.findAll(specification, pageable);
    }


    @PostMapping
    @CacheEvict("area-risco")
    @Operation(summary = "Criação de área de risco.", description = "Criar áreas de risco de um possível desastres naturais.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Concluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            })
    public ResponseEntity<List<AreaRiscoResponse>> create(@RequestBody List<AreaRiscoRequest> areaRiscoRequest) {
        var savedAreaRisco = areaRiscoService.save(areaRiscoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAreaRisco);
    }

    @PutMapping("/{id}")
    @CacheEvict("area-risco")
    @Operation(summary = "Atualização de AreaRisco.", description = "Atualizar dados de AreaRisco.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Concluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<AreaRiscoResponse> update(@PathVariable Long id, @RequestBody AreaRiscoRequest areaRiscoRequest) {
        var updatedAreaRisco = this.areaRiscoService.update(id, areaRiscoRequest);
        return ResponseEntity.ok(updatedAreaRisco);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover area risco.", description = "Atualização de flag de AreaRisco.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Concluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public void deleteById(@PathVariable Long id) {
        this.areaRiscoService.deleteById(id);
    }

    @GetMapping("/{id}")
    @Cacheable("area-risco")
    @Operation(summary = "Busca todas Areas de Risco.", description = "Buscar todas as localizações de possíveis incidentes.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca concluída com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<AreaRiscoResponse> getById(@PathVariable Long id) {
        var foundAreaRisco = this.areaRiscoService.getById(id);
        return ResponseEntity.ok(foundAreaRisco);
    }

    @GetMapping("/all")
    @Operation(summary = "Busca todas Areas de Risco.", description = "Buscar todas as localizações de possíveis incidentes.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca concluída com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<List<AreaRiscoResponse>> getAllByFlag() {
        var foundAreaRisco = this.areaRiscoService.getAll();
        return ResponseEntity.ok(foundAreaRisco);
    }

}
