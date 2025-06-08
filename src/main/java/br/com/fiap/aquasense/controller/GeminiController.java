package br.com.fiap.aquasense.controller;

import br.com.fiap.aquasense.model.enums.AreaRiscoData;
import br.com.fiap.aquasense.service.GeminiAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class GeminiController {

    @Autowired
    private GeminiAIService geminiAIService;

    @PostMapping("/gemini/chat")
    public Mono<String> getGemini(@RequestBody String request) {
    return geminiAIService.generateText(request);
    }

    @PostMapping("/gemini/test")
    public Mono<List<AreaRiscoData>> getGeminiTest(@RequestBody String request) {
        return geminiAIService.callGeminiApiAndParseRiskResponse(request);
    }
}
