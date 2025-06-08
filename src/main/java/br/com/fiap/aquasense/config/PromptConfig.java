package br.com.fiap.aquasense.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class PromptConfig {

    @Bean
    Resource systemMessages() {
        return new ClassPathResource("/prompts/gemini/system.st");
    }
}
