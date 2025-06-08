package br.com.fiap.aquasense.controller;

import br.com.fiap.aquasense.dto.request.LocalizacaoRequest;
import br.com.fiap.aquasense.dto.response.LocalizacaoResponse;
import br.com.fiap.aquasense.model.AreaRiscoFilter;
import br.com.fiap.aquasense.model.Localizacao;
import br.com.fiap.aquasense.model.LocalizacaoFilter;
import br.com.fiap.aquasense.repository.LocalizacaoRepository;
import br.com.fiap.aquasense.service.LocalizacaoService;
import br.com.fiap.aquasense.specification.LocalizacaoSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
@RequestMapping("/localizacao")
public class LocalizacaoController {

    @Autowired
    private LocalizacaoService localizacaoService;

    @Autowired
    private LocalizacaoRepository localizacaoRepository;


    @GetMapping
    @Operation(summary = "Busca Localizacoes Specification", description = "Busca Localizacoes com base em filters",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "ID fornecido inválido."),
                    @ApiResponse(responseCode = "404", description = "Endereco não encontrado.")
            })
    public Page<Localizacao> index(
            LocalizacaoFilter filter,
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.DESC) Pageable pageable
    ){
        var specification = LocalizacaoSpecification.withFilters(filter);
        return localizacaoRepository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict("localizacao")
    @Operation(summary = "Criação de localizaçao.", description = "Criar localizações de um usuário.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cadastro concluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<LocalizacaoResponse> create(@Valid @RequestBody LocalizacaoRequest request) {
        var savedLocalizacao = this.localizacaoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLocalizacao);
    }

    @PutMapping("/{id}")
    @CacheEvict("localizacao")
    @Operation(summary = "Atualização de localizaçao.", description = "Atualizar localização de um usuário.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cadastro concluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<LocalizacaoResponse> update(@PathVariable Long id,
                                                      @RequestBody @Valid LocalizacaoRequest request) {
        var updatedLocalizacao = this.localizacaoService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedLocalizacao);
    }

    @GetMapping("/{id}")
    @Cacheable("localizacaoById")
    @Operation(summary = "Busca de localizaçao.", description = "Buscar localização de um usuário.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca concluída com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<LocalizacaoResponse> findById(@PathVariable Long id) {
        var foundLocalizacao = this.localizacaoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundLocalizacao);
    }

    @GetMapping("/all")
    @Operation(summary = "Busca localizações.", description = "Buscar todas as localizações de usuários.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca concluída com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public ResponseEntity<List<LocalizacaoResponse>> findAll() {
        var foundLocalizacao = this.localizacaoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(foundLocalizacao);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover localizaçao.", description = "Remoção de localização de um usuário.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Concluído com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado")
            })
    public void delete(@PathVariable Long id) {
        this.localizacaoService.delete(id);
    }
}
