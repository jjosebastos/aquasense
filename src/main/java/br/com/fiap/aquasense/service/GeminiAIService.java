package br.com.fiap.aquasense.service;

import br.com.fiap.aquasense.model.AreaRisco;
import br.com.fiap.aquasense.model.Localizacao;
import br.com.fiap.aquasense.model.enums.AreaRiscoData;
import br.com.fiap.aquasense.model.enums.NivelRisco;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import jakarta.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiAIService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final LocalizacaoService localizacaoService;
    private final AreaRiscoService AreaRiscoService;
    @Value("${gemini.api.key}")
    private String geminiApiKey;
    @Value("${gemini.api.url}")
    private String geminiApiUrl;
    @Value("classpath:prompts/gemini/system.st")
    private Resource systemMessagesResource;
    private String geminiSystemPrompt;

    @PostConstruct
    public void init() {
        this.geminiSystemPrompt = readResourceContent(systemMessagesResource);
    }

    private String readResourceContent(Resource resource) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            log.error("Falha ao ler o arquivo de prompt do sistema: {}", resource.getFilename(), e);
            throw new RuntimeException("Falha ao ler o arquivo de prompt do sistema: " + resource.getFilename(), e);
        }
    }


    public Mono<List<AreaRiscoData>> callGeminiApiAndParseRiskResponse(String fullPrompt) {
        ObjectNode rootNode = objectMapper.createObjectNode();
        String combinedPrompt = geminiSystemPrompt + "\n\n" + fullPrompt;
        ArrayNode contentsArray = rootNode.putArray("contents");
        ObjectNode contentNode = objectMapper.createObjectNode();
        ArrayNode partsArray = contentNode.putArray("parts");
        partsArray.add(objectMapper.createObjectNode().put("text", combinedPrompt));

        contentsArray.add(contentNode);

        log.info("JSON final enviado para Gemini: {}", rootNode.toString());


        return webClient.post()
                .uri(geminiApiUrl + "?key=" + geminiApiKey) // URL da API com a chave
                .header("Content-Type", "application/json") // Define o cabeçalho Content-Type
                .bodyValue(rootNode.toString()) // Define o corpo da requisição como o JSON montado
                .retrieve() // Inicia o processamento da resposta
                .bodyToMono(String.class) // Converte o corpo da resposta para um Mono<String>
                .map(this::extractAndParseGeminiRiskListResponse) // Mapeia a string JSON da resposta para List<AreaRiscoData>
                .onErrorResume(e -> { // Trata erros que ocorram durante a comunicação ou parsing
                    log.error("Erro ao chamar a API Gemini para dados de risco: {}", e.getMessage(), e);
                    // Retorna um erro reativo para propagar a falha no pipeline
                    return Mono.error(new RuntimeException("Falha na comunicação com Gemini para dados de risco: " + e.getMessage(), e));
                });
    }


    private List<AreaRiscoData> extractAndParseGeminiRiskListResponse(String responseJson) {
        try {
            JsonNode root = objectMapper.readTree(responseJson);
            String rawGeminiOutputText = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
            log.info("Resposta JSON completa do Gemini: {}", responseJson);
            log.info("Texto extraído do Gemini antes da limpeza: {}", rawGeminiOutputText);
            if (rawGeminiOutputText.startsWith("```json")) {
                rawGeminiOutputText = rawGeminiOutputText.substring(7); // Remove "```json"
                if (rawGeminiOutputText.endsWith("```")) {
                    rawGeminiOutputText = rawGeminiOutputText.substring(0, rawGeminiOutputText.length() - 3); // Remove "```"
                }
            }
            else if (rawGeminiOutputText.startsWith("```")) {
                rawGeminiOutputText = rawGeminiOutputText.substring(3); // Remove "```"
                if (rawGeminiOutputText.endsWith("```")) {
                    rawGeminiOutputText = rawGeminiOutputText.substring(0, rawGeminiOutputText.length() - 3); // Remove "```"
                }
            }
            rawGeminiOutputText = rawGeminiOutputText.trim(); // Remove espaços em branco extras no início/fim
            log.info("Texto extraído do Gemini APÓS a limpeza (para parsing): {}", rawGeminiOutputText);
            if (rawGeminiOutputText.isEmpty()) {
                log.warn("Texto do Gemini ficou vazio após a limpeza de marcadores. Retornando lista vazia.");
                return new ArrayList<>(); // Retorna uma lista vazia se não houver JSON para parsear
            }
            return objectMapper.readValue(rawGeminiOutputText, new TypeReference<List<AreaRiscoData>>() {});

        } catch (Exception e) {
            log.error("Erro ao extrair e parsear lista da resposta do Gemini: {}\nResposta Bruta COMPLETA: {}", e.getMessage(), responseJson, e);
            throw new RuntimeException("Erro ao processar resposta do Gemini para lista: " + e.getMessage(), e);
        }
    }


    public Mono<List<AreaRisco>> processAndStoreRiskAreasFromGemini(String promptParaGemini) {
        return callGeminiApiAndParseRiskResponse(promptParaGemini)
                .flatMapMany(riscoDataList -> { // flatMapMany para processar cada item da lista reativamente
                    if (riscoDataList == null || riscoDataList.isEmpty()) {
                        log.info("Nenhuma área de risco retornada pelo Gemini para o prompt: {}", promptParaGemini);
                        return Mono.empty(); // Retorna um Mono vazio se a lista for nula ou vazia
                    }
                    // Converte a lista de AreaRiscoData para um Flux de AreaRiscoData
                    return Flux.fromIterable(riscoDataList);
                })
                .flatMap(riscoData -> { // Para cada AreaRiscoData...
                    // 1. Tenta encontrar ou criar a Localizacao
                    return localizacaoService.findByLatitudeAndLongitude(riscoData.getLatitude(), riscoData.getLongitude())
                            .switchIfEmpty(Mono.defer(() -> { // Se não encontrar, cria uma nova Localizacao
                                log.info("Localizacao não encontrada para {}, criando nova.", riscoData.getNm_area());
                                return localizacaoService.save(Localizacao.builder()
                                        .latitude(riscoData.getLatitude())
                                        .longitude(riscoData.getLongitude())
                                        .nome(riscoData.getNm_area()) // Usando nome da área como nome da localização inicialmente
                                        .build());
                            }))
                            .flatMap(localizacao -> {
                                // 2. Mapeia AreaRiscoData para AreaRisco
                                AreaRisco areaRisco = mapToAreaRiscoEntity(riscoData, localizacao);

                                // 3. Salva a AreaRisco
                                log.info("Salvando AreaRisco: {}", areaRisco.getNome());
                                return areaRiscoService.save(areaRisco);
                            })
                            .onErrorResume(e -> {
                                log.error("Erro ao processar ou salvar AreaRisco para {}: {}", riscoData.getNm_area(), e.getMessage(), e);
                                return Mono.empty(); // Continua processando outros itens mesmo se um falhar
                            });
                })
                .collectList(); // Coleta todos os Monos resultantes em uma única lista
    }

    private AreaRisco mapToAreaRiscoEntity(AreaRiscoData riscoData, Localizacao localizacao) {
        return AreaRisco.builder()
                .nome(riscoData.getNmLocalGeral())
                .tipoRisco(TipoRisco.fromString(riscoData.getTp_risco())) // Converte String para Enum
                .nivelRisco(NivelRisco.fromString(riscoData.getNv_risco())) // Converte String para Enum
                .dataAtualizacao(LocalDateTime.now()) // Define a data de atualização como agora
                .flagAtivo("S") // Exemplo de flag. Ajuste conforme sua regra.
                .localizacao(localizacao) // Associa a localização encontrada/criada
                .build();
    }

    // --- Método para consulta simples (chat) ---
    public Mono<String> generateText(String userQuery) {
        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode contentsArray = rootNode.putArray("contents");
        ObjectNode contentNode = objectMapper.createObjectNode();
        ArrayNode partsArray = contentNode.putArray("parts");
        partsArray.add(objectMapper.createObjectNode().put("text", userQuery)); // O prompt é a própria query do usuário
        contentsArray.add(contentNode);

        return webClient.post()
                .uri(geminiApiUrl + "?key=" + geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(rootNode.toString())
                .retrieve()
                .bodyToMono(String.class)
                .map(this::extractSimpleGeminiTextResponse)
                .onErrorResume(e -> {
                    log.error("Erro ao chamar a API Gemini para consulta simples: {}", e.getMessage(), e);
                    return Mono.just("Desculpe, não consegui processar sua solicitação no momento.");
                });
    }

    private String extractSimpleGeminiTextResponse(String responseJson) {
        try {
            JsonNode root = objectMapper.readTree(responseJson);
            return root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
        } catch (Exception e) {
            log.error("Erro ao extrair texto da resposta simples do Gemini: {}\nResposta Bruta: {}", e.getMessage(), responseJson, e);
            return "Erro ao parsear a resposta do Gemini.";
        }
    }
}