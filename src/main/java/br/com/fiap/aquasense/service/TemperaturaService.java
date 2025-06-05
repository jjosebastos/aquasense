package br.com.fiap.aquasense.service;

import br.com.fiap.aquasense.dto.response.TemperaturaApiResponse;
import br.com.fiap.aquasense.dto.response.TemperaturaResponse;
import br.com.fiap.aquasense.model.Localizacao;
import br.com.fiap.aquasense.model.Temperatura;
import br.com.fiap.aquasense.model.enums.StatusLocalizacao;
import br.com.fiap.aquasense.repository.LocalizacaoRepository;
import br.com.fiap.aquasense.repository.TemperaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.NoSuchElementException;

import java.util.concurrent.CompletableFuture;

@Service
public class TemperaturaService {

    @Autowired
    private LocalizacaoRepository localizacaoRepository;
    @Autowired
    private TemperaturaRepository temperaturaRepository;
    @Value("${openweather.api.key}")
    private String apiKey;

    private final String BASE_URL = "https://api.openweathermap.org/data/3.0/onecall";

    public TemperaturaApiResponse getTemperatura(double latitude, double longitude) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("%s?lat=%f&lon=%f&units=metric&exclude=minutely,alerts&appid=%s",
                BASE_URL, latitude, longitude, apiKey);
        return restTemplate.getForObject(url, TemperaturaApiResponse.class);
    }

    @Async
    public CompletableFuture<TemperaturaResponse> save(double latitude, double longitude, Long idLocalizacao) {
        System.out.println("Iniciando 'save' em thread: " + Thread.currentThread().getName());

        try {
            Localizacao localizacaoFound = this.localizacaoRepository.findById(idLocalizacao)
                    .orElseThrow();

            TemperaturaApiResponse apiTemperatura = this.getTemperatura(latitude, longitude);

            if(localizacaoFound.getLatitude() != latitude && localizacaoFound.getLongitude() != longitude) {
                throw new IllegalStateException();
            }
            if (apiTemperatura == null || apiTemperatura.getCurrent() == null) {
                throw new IllegalStateException();
            }


            Temperatura temperaturaEntity = Temperatura.builder()
                    .temperatura(apiTemperatura.getCurrent().getTemp())
                    .umidade(apiTemperatura.getCurrent().getHumidity())
                    .pressao(apiTemperatura.getCurrent().getPressure())
                    .dataRegistro(LocalDateTime.ofInstant(Instant.ofEpochSecond(apiTemperatura.getCurrent().getDt()), ZoneOffset.UTC))
                    .localizacao(localizacaoFound)
                    .build();
            Temperatura savedTemperatura = this.temperaturaRepository.save(temperaturaEntity);
            return CompletableFuture.completedFuture(this.toTemperaturaResponse(savedTemperatura));

        } catch (NoSuchElementException e) {
            return CompletableFuture.failedFuture(e);
        } catch (IllegalStateException e) {
            return CompletableFuture.failedFuture(e);
        } catch (Exception e) {
            System.err.println("Erro inesperado ao salvar dados de temperatura assincronamente: " + e.getMessage());
            return CompletableFuture.failedFuture(e);
        } finally {
            System.out.println("Finalizando 'save' em thread: " + Thread.currentThread().getName());
        }
    }
    private TemperaturaResponse toTemperaturaResponse(Temperatura temperatura) {
        return TemperaturaResponse.builder()
                .idTemperatura(temperatura.getIdTemperatura())
                .pressao(temperatura.getPressao())
                .umidade(temperatura.getUmidade())
                .temperatura(temperatura.getTemperatura())
                .dataRegistro(temperatura.getDataRegistro())
                .idLocalizacao(temperatura.getLocalizacao().getIdLocalizacao())
                .build();
    }

}
