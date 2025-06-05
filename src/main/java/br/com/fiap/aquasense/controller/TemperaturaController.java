package br.com.fiap.aquasense.controller;

import br.com.fiap.aquasense.dto.response.TemperaturaApiResponse;
import br.com.fiap.aquasense.dto.response.TemperaturaResponse;
import br.com.fiap.aquasense.service.TemperaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/temperatura")
public class TemperaturaController {

    @Autowired
    private TemperaturaService temperaturaService;

    @GetMapping("/{latitude}/{longitude}")
    public ResponseEntity<TemperaturaApiResponse> getTemperatura(
            @PathVariable("latitude") double lat,
            @PathVariable("longitude") double lon) {

    var temperatura = temperaturaService.getTemperatura(lat, lon);
    return ResponseEntity.ok(temperatura);

    }

    @PostMapping("/{latitude}/{longitude}/{idLocalizacao}")
    public ResponseEntity<TemperaturaResponse> postTemperatura(
            @PathVariable("latitude") double latitude,
            @PathVariable("longitude") double longitude,
            @PathVariable("idLocalizacao") Long idLocalizacao) {
        try {
            CompletableFuture<TemperaturaResponse> futureTemperatura =
                    temperaturaService.save(latitude, longitude, idLocalizacao);
            TemperaturaResponse savedTemperatura = futureTemperatura.get();
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTemperatura);

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Erro ao processar requisição assíncrona: " + e.getMessage());
            throw new RuntimeException("Erro ao processar a solicitação de salvamento de temperatura.", e.getCause());
        }
    }

}
